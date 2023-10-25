package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Test1 {

	public static void main(String[] args) {
		// 1. packing HTTP Request(Serialization)
		RequestSpecification req=RestAssured.given();
		req.baseUri("https://restcountries.com"); // Protocol with domain name
		req.basePath("v3.1/all"); //Resource hierarchy with no parameter
		// 2. submit Request
		Response res=req.get();
		//3. Analysis HTTP response(deserialization)
		System.out.println(res.statusCode());
		System.out.println(res.body().asPrettyString());
	}

}
