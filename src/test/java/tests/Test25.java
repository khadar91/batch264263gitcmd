package tests;

import java.util.List;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Test25 {
	String userpayload;
	String uid;
	String token;
	 List<String> booksisbn;
	@Test(priority=1)
	public void createUser()
	{
		userpayload="{\r\n" + 
				"  \"userName\": \"batch2655Api123\",\r\n" + 
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
		if(res.statusCode()==201 && uid!=null)
		{
			Reporter.log("Sucessful of new User creation");
			Assert.assertTrue(true);
		}
		else
		{
			Reporter.log("UnSucessful of user Creation");
			Assert.assertTrue(false);
		}
		
	}
	@Test(priority=2,dependsOnMethods={"createUser"})
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
		if(res.statusCode()==200 && token!=null)
		{
			Reporter.log("Sucessfully Generate Token");
			Assert.assertTrue(true);
		}
		else
		{
			Reporter.log("UnSucessfully Generate Token");
			Assert.assertTrue(false);
		}
		
	}
	@Test(priority=3, dependsOnMethods={"generateToken"})
	public void verifyUser() 
	{
		RequestSpecification req=RestAssured.given();
		req.baseUri("https://bookstore.toolsqa.com");
		req.basePath("Account/V1/Authorized");
		req.header("Content-Type","application/json");
		req.body(userpayload);
		Response res=req.post();
		res.then().log().all();
		String userOutput=res.body().asString(); //response body as plainText
		Reporter.log(userOutput);
		if(res.statusCode()==200 && userOutput.equalsIgnoreCase("true"))
		{
			Reporter.log("Sucessfully Autherized");
			Assert.assertTrue(true);
		}
		else
		{
			Reporter.log("Sucessfully Autherized");
			Assert.assertTrue(true);	
		}
	}
	@Test(priority=4)
	public void getAllBooksinStore()
	{
	
		RequestSpecification req=RestAssured.given();
		req.baseUri("https://bookstore.toolsqa.com");
		req.basePath("BookStore/V1/Books");
		Response res=req.get();
		res.then().log().all();
		if(res.body().jsonPath().getList("books").size()>0)
		{
			Reporter.log("Books are available in sore");
			booksisbn=res.jsonPath().getList("books.isbn");
			Assert.assertTrue(true);
		
		}
		else
		{
			Reporter.log("No books find in strore");
			Assert.assertTrue(false);
			
		}
	}
	@Test(priority=5, dependsOnMethods={"verifyUser"})
	public void deleteUser()
	{
		RequestSpecification req=RestAssured.given();
		req.baseUri("https://bookstore.toolsqa.com");
		req.basePath("Account/v1/User"+"/"+uid);
		req.header("Authorization","Bearer "+token);
		Response res=req.delete();
		res.then().log().all();
		if(res.statusCode()==204)
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
	
