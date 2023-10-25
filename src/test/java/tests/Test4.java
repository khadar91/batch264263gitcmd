package tests;

import java.util.Scanner;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Test4 {

	public static void main(String[] args) {
		Scanner scn=new Scanner(System.in);
		System.out.println("Enter Name:: ");
		String x=scn.nextLine();
		scn.close();
		// 1.Packing Request
		RequestSpecification req=RestAssured.given();
		req.baseUri("http://api.weatherapi.com"); //protocal with domain name
		req.basePath("v1/current.xml"); //resource hierarchy with no parameter
		req.queryParam("q", x);// quiry parameter parameterization with variable
		req.queryParam("key", "e9c3b0195be341c795281747202311"); //quiry param
		//2. Submit the request
		Response res=req.get();
		//3. Analysis response(deserialization
		System.out.println(res.statusCode());
		System.out.println(res.body().asPrettyString());

	}

}
