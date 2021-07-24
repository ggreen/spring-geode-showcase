package io.pivotal.gemfire.playground.orders.demo.serializer;

import static org.junit.Assert.*;

import org.apache.geode.pdx.PdxReader;
import org.apache.geode.pdx.PdxWriter;
import org.json.JSONObject;
import org.junit.Test;
import org.mockito.Mockito;

import io.pivotal.gemfire.playground.orders.demo.domain.ExampleObjectWithJsonObject;

public class ExampleAutoSerializerTest
{

	@Test
	public void testSerialization()
	throws Exception
	{
		ExampleAutoSerializer exampleAutoSerializer = new ExampleAutoSerializer();
		
		JSONObject jsonObject = new JSONObject("{ \"name\": \"test\" }" );
		
		ExampleObjectWithJsonObject in = new  ExampleObjectWithJsonObject();
		in.setMyId("JUNITID");
		in.setMyfieldWithJson(jsonObject);
		
		PdxWriter pdxWriter = Mockito.mock(PdxWriter.class);
		
		exampleAutoSerializer.toData(in, pdxWriter);
		
		
		
		PdxReader pdxReader  = Mockito.mock(PdxReader.class);
		Mockito.when(pdxReader.readString("myId")).thenReturn("JUNITID");
		Mockito.when(pdxReader.readString("myfieldWithJson")).thenReturn(jsonObject.toString());
		ExampleObjectWithJsonObject out = (ExampleObjectWithJsonObject)exampleAutoSerializer.fromData(ExampleObjectWithJsonObject.class, pdxReader);
		
		assertEquals(in,out);
		
		
		
	}

}
