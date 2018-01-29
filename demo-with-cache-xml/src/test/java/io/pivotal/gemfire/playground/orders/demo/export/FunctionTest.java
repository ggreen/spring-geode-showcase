package io.pivotal.gemfire.playground.orders.demo.export;


import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.execute.Execution;
import org.apache.geode.cache.execute.FunctionService;
import org.apache.geode.cache.execute.ResultCollector;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import io.pivotal.gemfire.playground.orders.domain.Order;



/*
 * create region --name=Order --type=PARTITION
 * create region --name=InMsg --type=PARTITION --colocated-with=/Order
 * deploy --jar=/Projects/solutions/gedi/dev/playground/spring-geode-showcase/demo-functions/target/demo-functions-0.0.1-SNAPSHOT.jar
 */
public class FunctionTest
{
	
	
	/**
	 * The Spring Application Context
	 */
	static ClassPathXmlApplicationContext ctx;
	static ClientCache cache;
	static Region<String,Order> orderRegion;
	
	
	@BeforeClass
	public static void setUp()
	{
		ctx = new ClassPathXmlApplicationContext("client-cache.xml");
		cache = ctx.getBean("gemfireCache",ClientCache.class);
		orderRegion = cache.getRegion("Order");
	}

	
	
	@SuppressWarnings({ "rawtypes" })
	@Test
	public void testFunctionDownloadExampleFunction()
	{
		
		Object results = null;
		
		Execution exe = FunctionService.onRegion(orderRegion);
		
		ResultCollector resultCollector = exe.execute("DownloadExampleFunction");
		
		results= resultCollector.getResult();
		System.out.println(" results:"+results);
		

	}

}
