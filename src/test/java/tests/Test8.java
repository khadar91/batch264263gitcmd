package tests;

import java.util.HashMap;

import com.google.gson.Gson;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Test8 {

	public static void main(String[] args) {
		// Way-3 Hasmap as Json pay load
		HashMap<String,Object> m=new HashMap<String,Object>();
		m.put("uesrId", 1);
		m.put("title", "wishes");
		m.put("body", "All the best");
		//convert hamap in to json payload
		Gson obj=new Gson();
		String p=obj.toJson(m);
		//create http request with body parameters
		RequestSpecification req=RestAssured.given();
		req.baseUri("https://jsonplaceholder.typicode.com"); //protocal with domain name
		req.basePath("posts"); //resource hirarirchy
		req.header("ContentType","application.json"); // header
		req.body(p);// body as 	string
		System.out.println("Request");
		System.out.println("--------------------");
		//get whole request
		req.given().log().all();
		// Submit request via post method
		Response res=req.post();
		//get whole response
		System.out.println("Response");
		System.out.println("--------------------");
		res.then().log().all();
	}

}
