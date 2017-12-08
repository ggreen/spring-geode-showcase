package io.pivotal.gemfire.playground.orders.demo;

import static org.junit.runners.MethodSorters.NAME_ASCENDING;

import java.io.IOException;

import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientCacheFactory;
import org.apache.geode.cache.client.ClientRegionFactory;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.apache.geode.test.junit.categories.ClientServerTest;
import org.apache.geode.test.junit.categories.IntegrationTest;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import static org.junit.Assert.*;


//@RunWith(SpringRunner.class)
//@SpringBootTest
//@FixMethodOrder(NAME_ASCENDING)
//@Category({IntegrationTest.class, ClientServerTest.class})
public class GemfireDemoApplicationTests {

	@BeforeClass
	public static void setUp()
	throws Exception
	{

		qa.startCluster();
		
	}//------------------------------------------------
	@Test
	public void contextLoads() {
		
		ClientCache cache = new ClientCacheFactory().create();
		ClientRegionFactory<String, String> regionFactory = cache.createClientRegionFactory(ClientRegionShortcut.PROXY);
		
		Region<String,String> region = regionFactory.create("Test");
		
	    region.put("1", "1");
		
		//assertEquals(region.get("1"),"1");
	}
	
	private static Gfsh qa = new Gfsh();

}
