package io.pivotal.gemfire.playground.orders.demo.serializer;

import org.apache.geode.cache.Declarable;
import org.apache.geode.pdx.PdxReader;
import org.apache.geode.pdx.PdxSerializer;
import org.apache.geode.pdx.PdxWriter;
import org.apache.geode.pdx.ReflectionBasedAutoSerializer;

import io.pivotal.gemfire.playground.orders.demo.domain.ExampleObjectWithJsonObject;

/**
 * <pre>
 * An example of a custom PdxSerializer. 
 * This example sub classes the ReflectionBasedAutoSerializer.
 * This serializer demonstrates special logic to handle ExampleObjectWithJsonObject
 * object. This object has as a field that uses the JSONObject that is not serializable by default.
 * </pre>
 * </p>
 * 
 * @author Gregory Green
 */
public class ExampleAutoSerializer extends ReflectionBasedAutoSerializer 
implements PdxSerializer, Declarable
{
	//Pdx serializer just for  ExampleObjectWithJSONObject
    private ExampleObjectWithJSONObjectPdxSerializer exampleObjectWithJSONObjectPdxSerializer = new ExampleObjectWithJSONObjectPdxSerializer();
		
	/**
	 * Constructor from ReflectionBasedAutoSerializer 
	 */
	public ExampleAutoSerializer()
	{
		super();
	}

	/**
	 * Constructor from ReflectionBasedAutoSerializer 
	 * @param checkPortability the checkPortability
	 * @param patterns class patterns
	 */
	public ExampleAutoSerializer(boolean checkPortability, String... patterns)
	{
		super(checkPortability, patterns);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor from ReflectionBasedAutoSerializer
	 * 	@param patterns class patterns 
	 */
	public ExampleAutoSerializer(String... patterns)
	{
		super(patterns);
	}//------------------------------------------------
	
	/**
	 * Handles custom ExampleObjectWithJsonObject
	 * @param obj the object to convert
	 * @param writer the pdx writer
	 */
	@Override
	public boolean toData(Object obj, PdxWriter writer) 
	{
		if(obj != null && ExampleObjectWithJsonObject.class.isAssignableFrom(obj.getClass()))
		{
			return  exampleObjectWithJSONObjectPdxSerializer.toData(obj,writer);
		}
		return super.toData(obj, writer);
	}//------------------------------------------------
	
	@Override
	public Object fromData(Class<?> clazz, PdxReader reader)
	{
		if(clazz.equals(ExampleObjectWithJsonObject.class))
		{
			return this.exampleObjectWithJSONObjectPdxSerializer.fromData(clazz, reader);
		}
		
		return super.fromData(clazz, reader);
	}
}
