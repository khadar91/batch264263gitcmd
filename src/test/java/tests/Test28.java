package tests;

import java.util.List;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Test28
{
	List<String> allbooksisbn;
	String userPayload="{\r\n" + 
    		"  \"userName\": \"batch265apist123\",\r\n" + 
    		"  \"password\": \"working@7IST\"\r\n" + 
    		"}";
	String uid;
	String token;
	String booksPayload;
	String booktitle;
	@Test(priority=1)
	public void getAllBooksInStore()

	{
	RequestSpecification req=RestAssured.given();
	req.baseUri("https://bookstore.toolsqa.com");
	req.basePath("BookStore/V1/Books");
	req.header("Content-Type","application/json");
	Response res=req.get();
	res.then().log().all();
	if(res.jsonPath().getList("books").size()>0)
	{
		Reporter.log("books are available in store");
		allbooksisbn=res.body().jsonPath().getList("books.isbn");
		Assert.assertTrue(true);
	}
	else
	{
		Reporter.log("books are unavailable in store");
		Assert.assertTrue(false);
	}
	}
	@Test(priority=2, dependsOnMethods= {"getAllBooksInStore"})
 public void createUser()
	{
		
		RequestSpecification req=RestAssured.given();
		req.baseUri("https://bookstore.toolsqa.com");
		req.basePath("Account/V1/User");
		req.header("Content-Type","application/json");
		req.body(userPayload);
		Response res=req.post();
		res.then().log().all();
		uid=res.body().jsonPath().getString("userID");
		if(res.getStatusCode()==201&&uid!=null)
		{
			Reporter.log("Sucessfull Creating new User");
			Assert.assertTrue(true);
		}
		else
		{
			Reporter.log("UnSucessfull Creating new User");
			Assert.assertTrue(false);
		}
		
	}
@Test(priority=3, dependsOnMethods= {"createUser"})	
public void generateToken()
{
	
	RequestSpecification req=RestAssured.given();
	req.baseUri("https://bookstore.toolsqa.com");
	req.basePath("Account/V1/GenerateToken");
	req.headers("Content-Type","application/json");
	req.body(userPayload);
	Response res=req.post();
	res.then().log().all();
	token=res.body().jsonPath().get("token");
	if(res.getStatusCode()==200 && token!=null)
	{
		Reporter.log("Sucessfully generatetoken");
		Assert.assertTrue(true);
	}
	else
	{
		Reporter.log("UnSucessfully generateToken");
		Assert.assertTrue(false);
	}
	}
@Test(priority=4, dependsOnMethods="generateToken")
public void verifyUser()
{
	RequestSpecification req=RestAssured.given();
	req.baseUri("https://bookstore.toolsqa.com");
	req.basePath("Account/V1/Authorized");
	req.header("Content-Type","application/json");
	req.body(userPayload);
	Response res=req.post();
	res.then().log().all();
	String userOutput=res.body().asString();
	Reporter.log(userOutput);
	if(userOutput.equalsIgnoreCase("true"))
	{

		 Reporter.log("User is authorized to do further tasks");
		 Assert.assertTrue(true);
	}
	else
	{
		Reporter.log("User is not authorized");
		Assert.assertTrue(false);
	}
}
@Test(priority=5,dependsOnMethods={"verifyUser"})
public void addBooks()
{
	int flag=0;
	for(String isbn:allbooksisbn)
	{
	booksPayload="{\r\n" + 
			"  \"userId\": \""+uid+"\",\r\n" + 
			"  \"collectionOfIsbns\": [\r\n" + 
			"    {\r\n" + 
			"      \"isbn\": \""+isbn+"\"\r\n" + 
			"    }\r\n" + 
			"  ]\r\n" + 
			"}";
	RequestSpecification req=RestAssured.given();
	req.baseUri("https://bookstore.toolsqa.com");
	req.basePath("BookStore/V1/Books");
	req.header("Content-Type","application/json");
	req.header("Authorization","Bearer "+token);
	req.body(booksPayload);
	Response res=req.post();
	res.then().log().all();
	if(res.getStatusCode()!=201)
	{
		flag=1;
	break;
	}
	else
		if(flag==0)
		{
			Reporter.log("Successful addition of books to user");
			Assert.assertTrue(true);
		}
		else
		{
			Reporter.log("Unsuccessful addition of books to user");
			Assert.assertTrue(false);
		}
	}
}
@Test(priority=6, dependsOnMethods= {"addBooks"})
public void getBooks()
{
	for(String isbn:allbooksisbn)
	{
	RequestSpecification req=RestAssured.given();
	req.baseUri("https://bookstore.toolsqa.com");
	req.basePath("BookStore/V1/Book");
	req.queryParam("ISBN",isbn);
	req.header("Authorization","Bearer "+token);
	req.header("Content-Type","application/json");
	Response res=req.get();
	res.then().log().all();
	booktitle=res.body().jsonPath().getString("title");
	Reporter.log(booktitle +"<br/>");
	}
}
@Test(priority=7,dependsOnMethods="addBooks")
public void deleteBooks()
{
	 RequestSpecification req=RestAssured.given();
	 req.baseUri("https://bookstore.toolsqa.com");
	 req.basePath("BookStore/v1/Books");
	 req.header("Authorization","Bearer "+token);
	 req.queryParam("UserId",uid);
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
@Test(priority=8,dependsOnMethods="verifyUser")
public void deleteuser()
{
	RequestSpecification req=RestAssured.given();
	req.baseUri("https://bookstore.toolsqa.com");
	req.basePath("Account/V1/User"+"/"+uid);
	req.header("Authorization","Bearer "+token);
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