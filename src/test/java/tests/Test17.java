package tests;

import java.util.HashMap;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Test17 {

	public static void main(String[] args) {
		// creating hasmap object
		HashMap<String,Object> h=new HashMap<String,Object>();
		h.put("zone", "student");
		//creating http Request as form parameter
		RequestSpecification req=RestAssured.given();
		req.baseUri("https://jsonplaceholder.typicode.com"); //protocal with domain aname
		req.basePath("users");
		req.header("Content-Type","application/x-www-form-urlencoded; charset=utf-8");
		req.formParams(h);// form parameter as key and value
		//get whole request
		req.given().log().all();
		//submit the request via post method
		Response res=req.post();
		//get the whole Response
		res.then().log().all();


	}

}
