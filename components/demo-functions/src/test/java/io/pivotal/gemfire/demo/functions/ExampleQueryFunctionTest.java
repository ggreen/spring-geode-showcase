package io.pivotal.gemfire.demo.functions;

import java.util.Properties;

import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientCacheFactory;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class ExampleQueryFunctionTest
{

	@Test
	public void testQuery()
	{
		Properties prop = new Properties();
		prop.setProperty("locators", "Gregorys-MBP");
		
		ClientCacheFactory factory = new ClientCacheFactory(prop);
		ClientCache cache = factory.create();
		
		
				
		
	}

}
