package io.pivotal.gemfire.playground.orders.demo.queries;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.execute.Execution;
import org.apache.geode.cache.execute.FunctionService;
import org.apache.geode.cache.execute.ResultCollector;
import org.apache.geode.cache.query.FunctionDomainException;
import org.apache.geode.cache.query.NameResolutionException;
import org.apache.geode.cache.query.Query;
import org.apache.geode.cache.query.QueryInvocationTargetException;
import org.apache.geode.cache.query.QueryService;
import org.apache.geode.cache.query.SelectResults;
import org.apache.geode.cache.query.TypeMismatchException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import io.pivotal.gemfire.playground.orders.domain.InMsg;
import io.pivotal.gemfire.playground.orders.domain.Order;



/*
 * create region --name=Order --type=PARTITION
 * create region --name=InMsg --type=PARTITION --colocated-with=/Order
 * deploy --jar=/Projects/solutions/gedi/dev/playground/spring-geode-showcase/demo-functions/target/demo-functions-0.0.1-SNAPSHOT.jar
 */
public class QueryTest
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

	
	@Test
	public void testQueryJoins() throws Exception
	{

		assertNotNull(cache);

		String oql = "select * from /Order o";
		
		
		
		Order order = new Order();
		order.setOrderId("junit");
		order.setClOrdId("junit");
		
		orderRegion.put("junit", order);
		
		this.executeQueryWithFunction(oql, orderRegion);
		
		
		oql = "select * from /InMsg im";
		
		Region<String,InMsg> inMsgRegion = cache.getRegion("InMsg");
		InMsg inMsg = new InMsg();
		inMsg.setMsgId("junit");
		
		inMsgRegion.put("junit", inMsg);
		
		executeQueryWithFunction(oql, inMsgRegion);
		
		
		
	
		
	}//------------------------------------------------
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testFunctionWithJoins()
	{
		
		String join = "select od.orderId  from /Order od, /InMsg im where im.msgId = od.orderId";
		
		Object results = this.executeQueryWithFunction(join, orderRegion);
		assertNotNull(results);
		List<Object> list = (List)results;
		
		assertTrue(!list.isEmpty());
		
		for (Object object : list)
		{
			List<Object> fromDataNodeList = (List)object;
			for (Object fromDataNode : fromDataNodeList)
			{
				System.out.println("fromDataNode:"+fromDataNode);
			}
			
		}
	}//------------------------------------------------
	@SuppressWarnings("unchecked")
	@Test
	public void testPreparedQuery() 
	throws FunctionDomainException, TypeMismatchException, NameResolutionException, QueryInvocationTargetException
	{
		
			
			Order order = new Order();
			order.setCumQty(1.0);
			order.setOrderId("preparedQuery");
			
			orderRegion.put(order.getOrderId(), order);
			
			
			// specify the  query string
			 String queryString = "SELECT DISTINCT * FROM /Order o WHERE o.orderId = $1 and o.cumQty = $2 ";
	
			QueryService queryService = cache.getQueryService();
			Query query = queryService.newQuery(queryString);
	
			// set a query bind parameter
			Object[] params =  {order.getOrderId(),order.getCumQty()};
	
			// Execute the query locally. It returns the results set.
			SelectResults<Order> results = (SelectResults<Order>) query.execute(params);
	
			assertTrue(results.size() == 1);		 
			 
			Order out = results.iterator().next();
			assertEquals(order, out);
		
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Object executeQueryWithFunction(String oql, Region<?, ?> region)
	{
		Execution exe;
		ResultCollector resultCollector;
		Object results = null;
		String[] queryArgs = {oql};
		
	
		exe = FunctionService.onRegion(region)
		.setArguments(queryArgs);
		
		resultCollector = exe.execute("ExampleQueryFunction");
		
		results= resultCollector.getResult();
		System.out.println("oql:"+oql+" results:"+results);
		
		return results;
	}

}
