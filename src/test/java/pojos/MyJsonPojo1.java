package pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MyJsonPojo1 {
	//Private properties to map with fileds
	@JsonProperty("uesrId") private int uid;
	@JsonProperty("title") private String t;
	@JsonProperty("body") private String b;
	@JsonProperty("id") private int i;
	
	//class is Test9
	
	//setter method
	public void setUserId(int uid)
	{
		this.uid=uid;
	}
	public void setTitle(String t)
	{
		this.t=t;
	}
	public void setBody(String b)
	{
		this.b=b;		
	}
	//getter methods
	public int getUserId()
	{
		return(uid);
	}
	public String getTitle()
	{
		return(t);		
	}
	public String getBody()
	{
		return(b);
	}
	public int getId()
	{
		return(i);
	}

}
