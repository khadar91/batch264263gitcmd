package tests;

import java.util.HashMap;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Test15 {

	public static void main(String[] args) {
		// creating as hashmap
		HashMap<String,Object> h=new HashMap<String,Object>();
		h.put("key1", "hi");
		h.put("key2", "how r u");
		//creates HTTP request with form parameter
		RequestSpecification req=RestAssured.given();
		req.baseUri("http://postman-echo.com"); // protocal with domain name
		req.basePath("post");
		req.header("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
		req.formParams(h);
		req.given().log().all();
	    //Submit Request via POST method
	    Response res=req.post();
	    //Display whole Response
	    res.then().log().all();

	}

}
