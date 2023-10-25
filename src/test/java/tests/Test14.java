package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Test14 {

	public static void main(String[] args) {
		// create http request with form param
		RequestSpecification req=RestAssured.given();
		req.baseUri("http://postman-echo.com"); // protocal with domain name
		req.basePath("post");
		req.header("Content-Type","application/x-www-form-urlencoded; charset=utf-8");
		req.formParam("key1", "hi");
		req.formParam("key2", "how r u");
		req.given().log().all();
		//submit the request via post method
		Response res=req.post();
		//Display the whole response
		res.then().log().all();

	}

}
