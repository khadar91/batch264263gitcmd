package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Test3 {

	public static void main(String[] args) {
		// 1.packing request(Serialization)
		RequestSpecification req=RestAssured.given();
		req.baseUri("http://api.weatherapi.com"); //protocol with domain name
		req.basePath("v1/current.xml"); //resource hierarchy with out path parameter
		req.queryParam("q","london");// quiry parameter
		req.queryParam("key","e9c3b0195be341c795281747202311"); //quiry parameter
		//submit the request
		Response res=req.get();
		//Analysis response(Deserialization)
		System.out.println(res.statusCode());
		System.out.println(res.body().asPrettyString());
		

	}

}
