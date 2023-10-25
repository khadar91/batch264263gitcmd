package tests;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Test27 
{
	String userpayload="{\r\n" + 
			"  \"userName\": \"batch2024api\",\r\n" + 
			"  \"password\": \"working@7IST\"\r\n" + 
			"}";
	String uid;
	String token;
	String bookPayload;
	String bookisbn;
	String updatePayload;
	String deletePayload;
	@Test(priority=1)
	public void createUser()
	{
		
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
			Reporter.log("Sucessfully created new User");
			Assert.assertTrue(true);
		}
		else
		{
			Reporter.log("UnSucessfully created new User");
			Assert.assertTrue(false);
		}
	}
	@Test(priority=2,dependsOnMethods= {"createUser"})
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
			Reporter.log("Sucessfully generateToken");
			Assert.assertTrue(true);
		}
		else
		{
			Reporter.log("UnSucessfully cgenerateTokenr");
			Assert.assertTrue(false);
		}
	}
@Test(priority=3,dependsOnMethods= {"generateToken"})
public void verifyUser()
{
	RequestSpecification req=RestAssured.given();
	req.baseUri("https://bookstore.toolsqa.com");
	req.basePath("Account/V1/Authorized");
	req.header("Content-Type","application/json");
	req.body(userpayload);
	Response res=req.post();
	res.then().log().all();
	String userOutput=res.body().asString();
	Reporter.log(userOutput);
	if(userOutput.equalsIgnoreCase("true"))
	{
		Reporter.log("Sucessfully Authorized");
		Assert.assertTrue(true);
	}
	else
	{
		Reporter.log("UnSucessfully Authorized");
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
	req.basePath("BookStore/V1/Books");
	req.header("content-Type","application/json");
	req.header("Authorization","Bearer "+token);
	req.body(bookPayload);
	Response res=req.post();
	res.then().log().all();
	bookisbn=res.body().jsonPath().getString("books[0].isbn");
	if(res.getStatusCode()==201 && bookisbn!=null)
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

@Test(priority=5,dependsOnMethods= {"addBook"})
public void updateBook()

{
	updatePayload="{\r\n" + 
			"  \"userId\": \""+uid+"\",\r\n" + 
			"  \"isbn\": \"9781449325862\"\r\n" + 
			"}";
	RequestSpecification req=RestAssured.given();
	req.baseUri("https://bookstore.toolsqa.com");
	req.basePath("BookStore/V1/Books"+"/"+bookisbn);
	req.header("Content-Type","application/json");
	req.header("Authorization","Bearer "+token);
	req.body(updatePayload);
	Response res=req.put();
	res.then().log().all();

	 if(res.getStatusCode()==200)
	 {
	    Reporter.log("Successful updation of a book to user");
	    Assert.assertTrue(true);
	 }
	 else
	 {
		Reporter.log("UnSuccessful updation of a book to user");
	    Assert.assertTrue(false);
	 }
}
@Test(priority=5,dependsOnMethods= {"updateBook"})
public void deleteBook()
{
	deletePayload="{\r\n" + 
			"  \"userId\": \""+uid+"\",\r\n" + 
			"  \"isbn\": \"9781449325862\"\r\n" + 
			"}";
	RequestSpecification req=RestAssured.given();
	req.baseUri("https://bookstore.toolsqa.com");
	req.basePath("BookStore/V1/Book");
	req.header("Content-Type","application/json");
	req.header("Authorization","Bearer "+token);
	req.body(deletePayload);
	Response res=req.delete();
	res.then().log().all();

	 if(res.getStatusCode()==204)
	 {
	    Reporter.log("Successful deleted a book ");
	    Assert.assertTrue(true);
	 }
	 else
	 {
		Reporter.log("UnSuccessful deleted a book");
	    Assert.assertTrue(false);
	 }
}
@Test(priority=7, dependsOnMethods={"verifyUser"})
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