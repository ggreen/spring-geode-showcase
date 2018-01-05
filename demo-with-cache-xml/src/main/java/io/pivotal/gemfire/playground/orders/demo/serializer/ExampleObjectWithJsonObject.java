package io.pivotal.gemfire.playground.orders.demo.serializer;

import org.json.JSONObject;

/**
 * This is an example domain data object.
 * 
 * It contains the field myfieldWithJson that is not serializable.
 * This object will be converted to and from a String 
 * @author Gregory Green
 *
 */
public class ExampleObjectWithJsonObject
{	
	/**
	 * @return the myfieldWithJson
	 */
	public JSONObject getMyfieldWithJson()
	{
		return myfieldWithJson;
	}
	/**
	 * @return the myId
	 */
	public String getMyId()
	{
		return myId;
	}
	/**
	 * @param myfieldWithJson the myfieldWithJson to set
	 */
	public void setMyfieldWithJson(JSONObject myfieldWithJson)
	{
		this.myfieldWithJson = myfieldWithJson;
	}
	/**
	 * @param myId the myId to set
	 */
	public void setMyId(String myId)
	{
		this.myId = myId;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((myId == null) ? 0 : myId.hashCode());
		result = prime * result + ((myfieldWithJson == null) ? 0 : myfieldWithJson.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExampleObjectWithJsonObject other = (ExampleObjectWithJsonObject) obj;
		if (myId == null)
		{
			if (other.myId != null)
				return false;
		}
		else if (!myId.equals(other.myId))
			return false;
		if (myfieldWithJson == null)
		{
			if (other.myfieldWithJson != null)
				return false;
		}
		else if (!myfieldWithJson.toString().equals(other.myfieldWithJson.toString()))
			return false;
		return true;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("ExampleObjectWithJsonObject [myfieldWithJson=").append(myfieldWithJson).append(", myId=")
		.append(myId).append("]");
		return builder.toString();
	}


	private JSONObject myfieldWithJson;
	private String myId;
}
