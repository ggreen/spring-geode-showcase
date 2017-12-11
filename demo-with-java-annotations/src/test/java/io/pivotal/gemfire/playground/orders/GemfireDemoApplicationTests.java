package io.pivotal.gemfire.playground.orders;
import static org.junit.Assert.*;

import org.apache.geode.cache.Region;
import org.apache.geode.cache.RegionShortcut;
import org.apache.geode.cache.client.ClientCache;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.gemfire.GemfireTemplate;
import org.springframework.stereotype.Component;
import gedi.solutions.geode.qa.GUnit;
import io.pivotal.gemfire.playground.orders.AppConfig;
import io.pivotal.gemfire.playground.orders.GemfireDemoApplication;
import io.pivotal.gemfire.playground.orders.dao.AccountCrudRepository;
import io.pivotal.gemfire.playground.orders.dao.OrderCrudRepository;
import io.pivotal.gemfire.playground.orders.domain.Account;
import nyla.solutions.core.patterns.jmx.JMX;


/**
 * JUNIT test that does not us the Spring Run as annotations
 * @author Gregory Green
 *
 */
@Component
public class GemfireDemoApplicationTests {

	static GemfireDemoApplication app;
	static AnnotationConfigApplicationContext ctx;
	
	
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
		ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		
		app = new GemfireDemoApplication();
			
		app.accountCrudRepos = ctx.getBean(AccountCrudRepository.class);
		app.accountsTemplate = ctx.getBean("accountsTemplate",GemfireTemplate.class);
			
		app.orderPdxRegion = ctx.getBean("orderRegion",Region.class);
		app.orderRegion = ctx.getBean("orderRegion",Region.class);
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
