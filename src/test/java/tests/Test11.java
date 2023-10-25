package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Test11 {

	public static void main(String[] args) {
		//creating string as variable
		String x="<Request>\r\n" + 
				"<Login>login</Login>\r\n" + 
				"<Password>password</Password>\r\n" + 
				"</Request>";
		//crate and submit http request as body parameter
		RequestSpecification req=RestAssured.given();
		req.baseUri("https://reqbin.com");// protocal with domain name
		req.basePath("echo/post/xml"); // resource hirarichy
		req.header("Content-Type","application/xml");
		req.header("User-Agent","PostmanRuntime/7.33.0");
		req.body(x); // variable AS string
		//get whole request
		System.out.println("Request");
		System.out.println("-----------------");
		req.given().log().all();
		//submit the request
		Response res=req.post();
		//get whole response
		System.out.println("Response");
		System.out.println("-----------------------");
		res.then().log().all();
	}

}
