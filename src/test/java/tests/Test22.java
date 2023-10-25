package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Test22 {

	public static void main(String[] args) {
		//creating digist authentectaion
		RequestSpecification req=RestAssured.given();
		req.baseUri("https://postman-echo.com");// protocal with domain anme
		req.basePath("digest-auth");
		req.auth().digest("postman", "password");
		System.out.println("Request");
		System.out.println("--------");
		req.given().log().all();
		Response res=req.get();
		System.out.println("Response");
		System.out.println("--------");
		res.then().log().all();

	}

}
