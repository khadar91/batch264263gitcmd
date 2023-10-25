package tests;

import java.util.HashMap;
import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Test12 {

	public static void main(String[] args) {
		// create a map
		HashMap<String,String> data=new HashMap<String,String>();
		data.put("Login", "login");
		data.put("Password", "password");
		StringBuilder b=new StringBuilder();
		b.append("<Request>");
		for(Map.Entry<String, String>entry:data.entrySet())
		{
			String key=entry.getKey();
			String value=entry.getValue();
			b.append("<").append(key).append(">");
			b.append(value);
			b.append("</").append(value).append(">");
			b.append(value);
		}
		b.append("</Request>");
		System.out.println(b.toString());
		//create http request as body parameter
		RequestSpecification req=RestAssured.given();
		req.baseUri("https://reqbin.com"); // Protocol with domain name
		req.basePath("echo/post/xml"); //resource hierarchy 
		req.headers("Content-Type","application/xml"); //header
		req.header("User-Agent","PostmanRuntime/7.33.0");
		req.body(b.toString()); //body as string variable
		System.out.println("Request");
	    System.out.println("--------");
	    req.given().log().all();
	    //Submit Request via POST method
	    Response res=req.post();
	    //Display whole Response
	    System.out.println("Response");
	    System.out.println("--------");
	    res.then().log().all();
		
		

	}

}
