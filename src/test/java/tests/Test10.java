package tests;

import java.io.File;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Test10 {

	public static void main(String[] args) {
		// creating a file instance/object
		File f=new File("src/test/resources/mydata.xml");
		//Create Http Rwuest with body parameter
		RequestSpecification req=RestAssured.given();
		req.baseUri("https://reqbin.com"); //Protocol with domain name
		req.basePath("echo/post/xml"); //resource hierarchy
		req.header("Content-Type","apllication/xml");
		req.header("User-Agent","PostmanRuntime/7.33.0");
		req.body(f);
		System.out.println("Request");
	    System.out.println("--------");
	    req.given().log().all();
	    //Submit Request via POST method
	    Response res=req.post();
	    //Display whole Response
	    System.out.println("Response");
	    System.out.println("-------------------");
	    res.then().log().all();
		
		

	}

}
