package io.pivotal.gemfire.playground.orders.demo.serializer;

import org.apache.geode.pdx.PdxReader;
import org.apache.geode.pdx.PdxSerializer;
import org.apache.geode.pdx.PdxWriter;
import org.json.JSONException;
import org.json.JSONObject;

import io.pivotal.gemfire.playground.orders.demo.domain.ExampleObjectWithJsonObject;


/**
 * <pre
 * An example of a PdxSerializer that only
 * handles the ExampleObjectWithJsonObject object.
 * 
 * This object will be convert the JSONObject to and from a String.
 * 
 * It will also mark the myId as an identify field as an performance 
 * improvement.
 * 
 * </pre>
 * 
 * @author Gregory Green
 */
public class ExampleObjectWithJSONObjectPdxSerializer implements PdxSerializer
{

	@Override
	public Object fromData(Class<?> clazz, PdxReader pdxreader)
	{
	    if (!clazz.equals(ExampleObjectWithJsonObject.class)) 
	        return null;
	    
	    ExampleObjectWithJsonObject obj = new ExampleObjectWithJsonObject();
	    
	    try
		{
			String json = pdxreader.readString("myfieldWithJson");
			
			if(json != null && json.length() > 0)
			{
				obj.setMyfieldWithJson(new JSONObject(json));
			}
			obj.setMyId(pdxreader.readString("myId"));
			
			return obj;
		}
		catch (JSONException e)
		{
			throw new RuntimeException(e.getMessage(),e);
		}
	    
	}//------------------------------------------------
 
	@Override
	public boolean toData(Object obj, PdxWriter pdxwriter)
	{
		if(obj == null)
			return false;
		
		if(!ExampleObjectWithJsonObject.class.isAssignableFrom(obj.getClass()))
			return false;
		
		ExampleObjectWithJsonObject exampleObjectWithJsonObject = (ExampleObjectWithJsonObject)obj;
		
		String json = "";
		
		JSONObject jsonObject = exampleObjectWithJsonObject.getMyfieldWithJson();
		if(jsonObject != null)
			json = jsonObject.toString();
		
		pdxwriter.writeString("myfieldWithJson", json);
		pdxwriter.writeString("myId", exampleObjectWithJsonObject.getMyId());

		pdxwriter.markIdentityField("myId");
		
		return true;
	}

}
