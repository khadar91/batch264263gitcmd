package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Test20 {

	public static void main(String[] args) {
		//Basic(pemitive) Authentication
		RequestSpecification req=RestAssured.given();
		req.baseUri("http://the-internet.herokuapp.com"); //protocal with domain name
		req.basePath("basic_auth");
		req.auth().basic("admin", "admin");
		System.out.println("Request");
		System.out.println("---------------------------");
		req.given().log().all();
		//submit the response
		Response res=req.get();
		System.out.println("Response");
		System.out.println("==============================");
		res.then().log().all();

	}

}
