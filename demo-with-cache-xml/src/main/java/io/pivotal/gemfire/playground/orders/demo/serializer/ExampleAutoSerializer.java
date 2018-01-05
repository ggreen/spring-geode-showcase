package io.pivotal.gemfire.playground.orders.demo.serializer;

import org.apache.geode.cache.Declarable;
import org.apache.geode.pdx.PdxReader;
import org.apache.geode.pdx.PdxSerializer;
import org.apache.geode.pdx.PdxWriter;
import org.apache.geode.pdx.ReflectionBasedAutoSerializer;

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
	private ExampleObjectWithJSONObjectPdxSerializer exampleObjectWithJSONObjectPdxSerializer = new ExampleObjectWithJSONObjectPdxSerializer();
	
	@Override
	public boolean toData(Object obj, PdxWriter writer) 
	{
		if(obj != null && ExampleObjectWithJsonObject.class.isAssignableFrom(obj.getClass()))
		{
			return  exampleObjectWithJSONObjectPdxSerializer.toData(obj,writer);
		}
		return super.toData(obj, writer);
	}
	
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
