package pojos;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@XmlRootElement(name="Response")
public class MyXmlPojo2 {
	@JacksonXmlProperty(localName="ResponseCode")
	private int rc;
	@JacksonXmlProperty(localName="ResponseMessage")
	private String rm;
	//setters method
	public int getResponseCode()
	{
		return(rc);
	}
	public String getResponseMessage()
	{
		return(rm);
	}

}
