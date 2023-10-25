package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Test18 {


	public static void main(String[] args)
	{
		//Create a HTTP request with form parameters for API1
		RequestSpecification req=RestAssured.given();
		req.baseUri("https://www.smatbot.com");
		req.basePath("kya_backend/pagehub/chatbot_utils");
		req.header("Content-Type","application/x-www-form-urlencoded"); 
		req.formParam("action","init_chat");
		req	.formParam("device_print","ed79cf898a7951b5f8858d2d7f4f106f");
		req	.formParam("chatbot_id",4174);
		req	.formParam("name","SmatBot");
		req	.formParam("language_code","default");
	    //Submit Request via POST method
	    Response res=req.post();
	    //Display whole Response
	    System.out.println("Response");
	    System.out.println("--------");
	    res.then().log().all();
	}
}
