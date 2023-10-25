package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Test5 {

	public static void main(String[] args) {
		//create http request for all details
		RestAssured.urlEncodingEnabled=false;// to identify matrix parameter in Uri
		RequestSpecification req=RestAssured.given();
		req.baseUri("https://petstore.swagger.io");//Protocol with domain name
		////resources hierarchy with matrix parameter value
		req.basePath("v2/pet/findByStatus;matrixParm=test");
		req.queryParam("offset","0");
		req.queryParam("pageSize","20");
		System.out.println("Request");
		System.out.println("-----------------");
		req.given().log().all();// To display whole request
		//submit the request
		Response res=req.get();
		//get and display response
		System.out.println("Response");
		System.out.println("-----------------");
		res.then().log().all();// to display whole response

	}

}
