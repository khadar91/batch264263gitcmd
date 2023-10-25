package tests;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Test24 {
	String userPayload;
	String uid;
	String token;
	@Test(priority=1)
	public void createUser()
	{
		userPayload="{\r\n" + 
				"  \"userName\": \"batch26452\",\r\n" + 
				"  \"password\": \"working@7IST\"\r\n" + 
				"}";
		RequestSpecification req=RestAssured.given();
		req.baseUri("https://bookstore.toolsqa.com");
		req.basePath("Account/v1/User");
		req.header("Content-Type","application/json");
		req.body(userPayload);
		Response res=req.post();
		res.then().log().all();
		uid=res.body().jsonPath().getString("userID");
		if(res.getStatusCode()==201 && uid!=null)
		{
			Reporter.log("Sucessful creation of new user");
			Assert.assertTrue(true);
		}
		else
		{
			Reporter.log("UnSucessful creation of new user");
			Assert.assertTrue(false);
		}
	}
	@Test(priority=2,dependsOnMethods={"createUser"})
	public void generateToken()
	{
		RequestSpecification req=RestAssured.given();
		req.baseUri("https://bookstore.toolsqa.com");
		req.basePath("Account/v1/GenerateToken");
		req.header("Content-Type","application/json");
		req.body(userPayload);
		Response res=req.post();
		res.then().log().all();
		token=res.body().jsonPath().getString("token");
		if(res.getStatusCode()==200 && token!=null)
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
	@Test(priority=3, dependsOnMethods="generateToken")
	public void verifyUser()
	{
		RequestSpecification req=RestAssured.given();
		req.baseUri("https://bookstore.toolsqa.com");
		req.basePath("Account/v1/Authorized");
		 req.header("Content-Type","application/json");
		 req.body(userPayload);
		 Response res=req.post();
		 res.then().log().all();
		 String userOutput=res.body().asString(); //Response body as plain text
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
	@Test(priority=4, dependsOnMethods={"verifyUser"})
		 public void getUser()
		 {
			 RequestSpecification req=RestAssured.given();
			 req.baseUri("https://bookstore.toolsqa.com");
			 req.basePath("Account/v1/User"+"/"+uid);
			 req.header("Authorization","Bearer "+token);
			 Response res=req.get();
			 System.out.println("getUser Response");
			 res.then().log().all();
			 if(res.getStatusCode()==200)
			 {
			    Reporter.log("Successful get user details");
			    Assert.assertTrue(true);
			 }
			 else
			 {
			    Reporter.log("Unsuccessful get user details");
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



