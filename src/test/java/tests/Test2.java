package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Test2 {

	public static void main(String[] args) {
		// 1.Packing Request (Serialization)
		RequestSpecification req=RestAssured.given();
		req.baseUri("https://restcountries.com"); // Protocol with domain name
		req.basePath("v3.1/name/india"); //Resource hierarchy with parameter
		req.queryParam("fullText","true"); //query parameter
		//Submit the request
		Response res=req.get();
		//Analysis Response(deserialization)
		System.out.println(res.statusCode());
		System.out.println(res.body().asPrettyString());
		

	}

}
