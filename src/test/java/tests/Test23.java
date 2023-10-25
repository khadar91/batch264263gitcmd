package tests;

import io.restassured.RestAssured;
import io.restassured.authentication.FormAuthConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Test23 {

	public static void main(String[] args) {
		// Form Authentication
		//open browser and launch page with given uri
		//use inspect on page and take action name, name of userid and password fields
		FormAuthConfig fc=new FormAuthConfig("index.php","uid", "password");
		//creating http request parameter
		RequestSpecification req=RestAssured.given();
		req.baseUri("https://demo.guru99.com/"); //protocal with domain name
		req.basePath("V1/index.php");
		req.auth().form("mngr356676","deqUpav",fc);
		Response res=req.post();
		System.out.println(res);
	    res.then().log().all();
		

	}

}
