package tests;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojos.MyJsonPojo1;

public class Test9 {

	public static void main(String[] args) throws Exception {
		//Way-4: Serialisation of individual values into JSON payload using POJO class
		//crate object on MyJsonPojo1 class object
		MyJsonPojo1 obj1=new MyJsonPojo1();
		obj1.setUserId(1);
		obj1.setTitle("hello");
		obj1.setBody("how are you");
		ObjectMapper om=new ObjectMapper();
		System.out.println(om.writeValueAsString(obj1));
		//Create a http request with body param	
		RequestSpecification req=RestAssured.given();
		req.baseUri("https://jsonplaceholder.typicode.com");// protocal with domain name
		req.basePath("posts");// resources hirarichy	
		req.header("Content-Type","application/json");// header
		req.body(obj1); //body as object of corresponding pojo calss
		//Submit Request via POST method
	    Response res=req.post();
	    System.out.println("request");
	    System.out.println("--------------------------");
	    System.out.println(res.getBody().asPrettyString());
	    //Deserialization of JSON response body into individual values using POJO class
	    MyJsonPojo1 obj2=om.readValue(res.getBody().asString(), MyJsonPojo1.class);
	    System.out.println(obj2.getUserId());
	    System.out.println(obj2.getTitle());
	    System.out.println(obj2.getBody());
	    System.out.println(obj2.getId());

	}

}
