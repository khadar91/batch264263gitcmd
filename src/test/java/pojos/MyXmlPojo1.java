package pojos;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@XmlRootElement(name="Request")
public class MyXmlPojo1 {
@JacksonXmlProperty(localName="Login")
private String uid;
@JacksonXmlProperty(localName="Password")
private String pwd;
public void setLogin(String uid)
{
	this.uid=uid;
}
public void setPassword(String pwd)
{
	this.pwd=pwd;
}
}
