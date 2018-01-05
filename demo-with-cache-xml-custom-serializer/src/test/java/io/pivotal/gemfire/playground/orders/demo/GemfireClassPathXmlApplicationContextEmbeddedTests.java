package io.pivotal.gemfire.playground.orders.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.geode.cache.Region;
import org.apache.geode.cache.RegionShortcut;
import org.apache.geode.cache.client.ClientCache;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.gemfire.GemfireTemplate;

import gedi.solutions.geode.qa.GUnit;
import io.pivotal.gemfire.playground.orders.demo.dao.AccountCrudRepository;
import io.pivotal.gemfire.playground.orders.demo.dao.OrderCrudRepository;
import io.pivotal.gemfire.playground.orders.demo.domain.Account;
import io.pivotal.gemfire.playground.orders.demo.domain.ExampleObjectWithJsonObject;
import nyla.solutions.core.patterns.jmx.JMX;

/**
 * <pre>
 * This class provides an example of starting and stopping a GemFire
 * cluster to used within JUNIT suite.
 * 
 * </pre>
 * @author Gregory Green
 *
 */
public class GemfireClassPathXmlApplicationContextEmbeddedTests {

	/**
	 * The Spring boot based app.
	 * Note will be not be ran as a Web Service.
	 * 
	 */
	static GemfireDemoApplication app;
	
	/**
	 * The Spring Application Context
	 */
	static ClassPathXmlApplicationContext ctx;
	
	
	/**
	 * 
	 * @throws Exception an unknown error occurs
	 */
	@SuppressWarnings("unchecked")
	@BeforeClass
	public static void setUp()
	throws Exception
	{

		gunit.startCluster();
		
		try(JMX jmx = JMX.connect("localhost",1099))
		{
			gunit.waitForMemberStart("server",jmx);			
		}
		
		//Create regions on server
		gunit.createRegion("Order", RegionShortcut.PARTITION);
		gunit.createRegion("Test", RegionShortcut.PARTITION);
		gunit.createRegion("Accounts", RegionShortcut.PARTITION);
		
		//Manually wire the application object
		ctx = new ClassPathXmlApplicationContext("client-cache.xml");
		
		app = new GemfireDemoApplication();
			
		app.accountCrudRepos = ctx.getBean(AccountCrudRepository.class);
		app.accountsTemplate = ctx.getBean("accountsTemplate",GemfireTemplate.class);
			
		app.orderPdxRegion = ctx.getBean("Order",Region.class);
		app.orderRegion = ctx.getBean("Order",Region.class);
		app.orderTemplate = ctx.getBean("orderTemplate",GemfireTemplate.class);
		app.orderCrudRepos = ctx.getBean(OrderCrudRepository.class);
		
	}//------------------------------------------------
	
	@AfterClass
	public static void shutdown()
	throws Exception
	{
		try{gunit.shutdown(); } catch(Exception e) {}
		
		try{ctx.close(); } catch(Exception e) {}
	}//------------------------------------------------
	@Test
	public void contextLoads() {
		
		ClientCache cache = ctx.getBean("gemfireCache",ClientCache.class);
		assertNotNull(cache);
	}//------------------------------------------------
	
	@Test
	public void testExampleAutoSerializer() throws Exception
	{
		ExampleObjectWithJsonObject obj = new ExampleObjectWithJsonObject();
		obj.setMyId("JUNITID");
		obj.setMyfieldWithJson(new JSONObject("{\"name\":\"hello world\"}"));
		app.orderTemplate.put(obj.getMyId(), obj);
		
		ExampleObjectWithJsonObject out = app.orderTemplate.get(obj.getMyId());
		
		assertEquals(obj,out);
	}//------------------------------------------------
	
	private static GUnit gunit = new GUnit();
}
