package tests;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class Test26 {
	String userpayload;
	String bookPayload;
	String uid;
	String token;
	String bookisbn;
	String booktitle;
	
@Test(priority=1)
	public void createUser()

	{

	userpayload="{\r\n" + 
    		"  \"userName\": \"batch261api2\",\r\n" + 
    		"  \"password\": \"working@7IST\"\r\n" + 
    		"}";
	
	RequestSpecification req=RestAssured.given();
	req.baseUri("https://bookstore.toolsqa.com");
	req.basePath("Account/V1/User");
	req.header("Content-Type","application/json");
	req.body(userpayload);
	Response res=req.post();
	res.then().log().all();
	uid=res.body().jsonPath().getString("userID");
	if(res.getStatusCode()==201 && uid!=null)
	{
		Reporter.log("Sucessfully new User Created");
		Assert.assertTrue(true);
	}
	else
	{
		Reporter.log("unSucessfully new User Created");
		Assert.assertTrue(false);
	}

}
@Test(priority=2, dependsOnMethods= {"createUser"})
public void generateToken()
{
RequestSpecification req=RestAssured.given();
req.baseUri("https://bookstore.toolsqa.com");
req.basePath("Account/V1/GenerateToken");
req.header("Content-Type","application/json");
req.body(userpayload);
Response res=req.post();
res.then().log().all();
token=res.body().jsonPath().getString("token");
if(res.getStatusCode()==200 && token!=null)
{
	Reporter.log("Sucessfully generate token");
	Assert.assertTrue(true);
}
else
{
	Reporter.log("unSucessfully genertae toke");
	Assert.assertTrue(false);
}

}
@Test(priority=3, dependsOnMethods= {"generateToken"})
public void verifyUser()
{

RequestSpecification req=RestAssured.given();
req.baseUri("https://bookstore.toolsqa.com");
req.basePath("Account/V1/Authorized");
req.header("Content-Type","application/json");
req.body(userpayload);
Response res=req.post();
res.then().log().all();
String userOutput=res.body().asString();//
Reporter.log(userOutput);
if(userOutput.equalsIgnoreCase("true"))
{
	Reporter.log("Sucessfully Autherized ");
	Assert.assertTrue(true);
}
else
{
	Reporter.log("UnAutherized");
	Assert.assertTrue(false);
}

}
@Test(priority=4, dependsOnMethods= {"verifyUser"})
public void addBook()
{
	bookPayload="{\r\n" + 
			"  \"userId\": \""+uid+"\",\r\n" + 
			"  \"collectionOfIsbns\": [\r\n" + 
			"    {\r\n" + 
			"      \"isbn\": \"9781449337711\"\r\n" + 
			"    }\r\n" + 
			"  ]\r\n" + 
			"}";
RequestSpecification req=RestAssured.given();
req.baseUri("https://bookstore.toolsqa.com");
req.basePath("BookStore/v1/Books");
req.header("Content-Type","application/json");
req.header("Authorization","Bearer "+token);
req.body(bookPayload);
Response res=req.post();
res.then().log().all();
bookisbn=res.body().jsonPath().getString("books[0].isbn");
if(res.statusCode()==201 && bookisbn!=null)
{
    Reporter.log("Successfully a book is added to user");
    Assert.assertTrue(true);
 }
 else
 {
	Reporter.log("Unsuccessfully Successfully a book is added to user");
    Assert.assertTrue(false);
 }
}
@Test(priority=5, dependsOnMethods= {"addBook"})
public void getbook()
{


	 RequestSpecification req=RestAssured.given();
	 req.baseUri("https://bookstore.toolsqa.com");
	 req.basePath("BookStore/v1/Book");
	 req.queryParam("ISBN",bookisbn);
	 req.header("Authorization","Bearer "+token);
	 Response res=req.get();
	 res.then().log().all();
	 booktitle=res.body().jsonPath().getString("title");
	 Reporter.log(booktitle);
}

@Test(priority=6, dependsOnMethods={"verifyUser"})
public void deleteUser()
{
	 RequestSpecification req=RestAssured.given();
	 req.baseUri("https://bookstore.toolsqa.com");
	 req.basePath("Account/v1/User"+"/"+uid);
	 req.header("Authorization","Bearer "+token);
	 req.given().log().all();
	 Response res=req.delete();
	 res.then().log().all();
	 if(res.getStatusCode()==204)
	 {
	    Reporter.log("Successful deletion of user");
	    Assert.assertTrue(true);
	 }
	 else
	 {
	    Reporter.log("Unsuccessful deletion of user");
	    Assert.assertTrue(false);
	 }
}
}