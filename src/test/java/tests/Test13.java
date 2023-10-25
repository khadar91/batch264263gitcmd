package tests;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojos.MyXmlPojo1;
import pojos.MyXmlPojo2;

public class Test13 {

	public static void main(String[] args) throws Exception{
		// creating object to pojo class
		MyXmlPojo1 obj1=new MyXmlPojo1();
		obj1.setLogin("Login");
		obj1.setPassword("Password");
		XmlMapper xm=new XmlMapper();
		System.out.println(xm.writeValueAsString(obj1));
		//create HttpRequst as body parameter
		RequestSpecification req=RestAssured.given();
		req.baseUri("https://reqbin.com");// protocal with domain name
		req.basePath("echo/post/xml"); // resource hirarichy
		req.header("Content-Type","application/xml"); //header
		req.header("User-Agent","PostmanRuntime/7.32.3");
		req.body(obj1); //body as object of pojo class
		//Submit request via post method
		Response res=req.post();
		//get whole request
		//Dsplay the whole Response
		MyXmlPojo2 obj2=xm.readValue(res.getBody().asString(), MyXmlPojo2.class);
		System.out.println(obj2.getResponseCode());
		System.out.println(obj2.getResponseMessage());
		
		

	}

}
