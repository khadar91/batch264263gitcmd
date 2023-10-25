package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Test7 {

	public static void main(String[] args) {
		// WAY-2 :JSON string value as Request body
		String x="{\r\n" + 
				"\"userId\":1,\r\n" + 
				"\"title\":\"wishes\",\r\n" + 
				"\"body\":\"All the best\"\r\n" + 
				"}";
		//Create Http request as a body parameter
		RequestSpecification req=RestAssured.given();
		req.baseUri("https://jsonplaceholder.typicode.com"); //protocol with domain name
		req.basePath("posts"); //resource hierarchy 
		req.header("Content-Type","application/json");  //header
		req.body(x); //body as string
		//submit the request via post method
		Response res=req.post();
		//Display the whole request
		System.out.println("Rquest");
		System.out.println("----------------");
		req.given().log().all();
		//display the whole response
		System.out.println("Response");
		System.out.println("----------------");
		res.then().log().all();

	}

}
