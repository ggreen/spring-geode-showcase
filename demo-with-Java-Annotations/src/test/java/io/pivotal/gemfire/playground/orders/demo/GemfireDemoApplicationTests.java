package io.pivotal.gemfire.playground.orders.demo;

import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientCacheFactory;
import org.apache.geode.cache.client.ClientRegionFactory;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

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
	
	@AfterClass
	public static void shutdown()
	throws Exception
	{

		qa.shutdown();
		
	}//------------------------------------------------
	@Test
	public void contextLoads() {
		
		ClientCache cache = new ClientCacheFactory().create();
		ClientRegionFactory<String, String> regionFactory = cache.createClientRegionFactory(ClientRegionShortcut.PROXY);
		
		Region<String,String> region = regionFactory.create("Test");
		
	    region.put("1", "1");
		
		//assertEquals(region.get("1"),"1");
	    cache.close();
	}
	
	private static Gfsh qa = new Gfsh();

}
