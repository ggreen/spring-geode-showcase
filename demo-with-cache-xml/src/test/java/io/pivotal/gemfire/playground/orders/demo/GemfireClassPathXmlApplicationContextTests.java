package io.pivotal.gemfire.playground.orders.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.geode.cache.Region;
import org.apache.geode.cache.RegionShortcut;
import org.apache.geode.cache.client.ClientCache;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.gemfire.GemfireTemplate;

import gedi.solutions.geode.qa.GUnit;
import io.pivotal.gemfire.playground.orders.demo.dao.AccountCrudRepository;
import io.pivotal.gemfire.playground.orders.demo.dao.OrderCrudRepository;
import io.pivotal.gemfire.playground.orders.demo.domain.Account;
import nyla.solutions.core.patterns.jmx.JMX;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class GemfireDemoApplicationTests {

	static GemfireDemoApplication app;
	static ClassPathXmlApplicationContext ctx;
	
	
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
	public void testSaveAcccount() throws Exception
	{
		Account acct = new Account();
		acct.setId("junitAcct");
		acct.setName("Testing name");
		assertTrue(app.saveAccount(acct));
		
		assertEquals(acct,app.accountsTemplate.get(acct.getId()));
		assertEquals(acct,app.accountCrudRepos.findById(acct.getId()).get());
	}//------------------------------------------------
	
	private static GUnit gunit = new GUnit();
}
