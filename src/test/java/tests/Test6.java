package tests;

import java.io.File;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Test6 {

	public static void main(String[] args) {
		//Way-1 Json file content as response
		//creating a json file instance for json file
		File f=new File("src/test/resources/postdata.json");
		//create http request as body parameters
		RequestSpecification req=RestAssured.given();
		req.baseUri("https://jsonplaceholder.typicode.com");// Protocol with domain name
		req.basePath("posts"); //resource hierarchy
		req.header("Content-Type","applcation/json"); //header
		req.body(f); //body as .json file
		System.out.println("Request");
		System.out.println("------------------");
		req.given().log().all();
		//Submit the request via post method
		Response res=req.post();
		//Display the response
		System.out.println("Response");
		System.out.println("-------------------");
		res.then().log().all();
		

	}

}
