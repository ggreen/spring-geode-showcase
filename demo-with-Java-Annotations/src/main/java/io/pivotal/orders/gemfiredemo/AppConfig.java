package io.pivotal.orders.gemfiredemo;

import java.util.Collections;
import java.util.Properties;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientCacheFactory;
import org.apache.geode.cache.client.ClientRegionFactory;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.GemfireTemplate;
import org.springframework.data.gemfire.listener.ContinuousQueryDefinition;
import org.springframework.data.gemfire.listener.ContinuousQueryListenerContainer;

import io.pivotal.orders.gemfiredemo.domain.Account;
import io.pivotal.orders.gemfiredemo.listener.OrderListener;

@Configuration
public class AppConfig
{	
	@Bean
	public Properties gemfireProperties() {

		Properties gemfireProperties = new Properties();

		gemfireProperties.setProperty("name", "test");
		gemfireProperties.setProperty("mcast-port", "0");
		//gemfireProperties.setProperty("log-level", "config");

		return gemfireProperties;
	}
	
	@Bean
	@Autowired
	public ClientCache gemfireCache(@Qualifier("gemfireProperties") Properties gemfireProperties)
			throws Exception {

		ClientCache gemfireCache = new ClientCacheFactory(gemfireProperties).create();
		
		return gemfireCache;
		
	}
	
	@Bean
	@Autowired
	public ClientRegionFactory<?, ?> proxyClientRegionFactory(@Qualifier("gemfireCache") 
	ClientCache gemfireCache)
	{
		return gemfireCache.createClientRegionFactory(ClientRegionShortcut.PROXY);
		
	}
	
	@Bean
	@Autowired
	@SuppressWarnings("unchecked")
	public Region<String,Account> accountRegion(@Qualifier("proxyClientRegionFactory") ClientRegionFactory<?, ?> proxyClientRegionFactory )
	{
		return (Region<String,Account>)proxyClientRegionFactory.create("Accounts");
	}

	@Bean
	@Autowired
	@SuppressWarnings("unchecked")
	public Region<String,Account> orderRegion(@Qualifier("proxyClientRegionFactory") ClientRegionFactory<?, ?> proxyClientRegionFactory )
	{
		return (Region<String,Account>)proxyClientRegionFactory.create("Order");
	}
	
	
	@Bean
	@Autowired
	public GemfireTemplate orderTemplate(@Qualifier("orderRegion") Region<?,?> orderRegion)
	{
		return new GemfireTemplate(orderRegion);
	}
	
	@Bean
	@Autowired
	public GemfireTemplate accountsTemplate(@Qualifier("accountRegion") Region<?,?> accountRegion)
	{
		return new GemfireTemplate(accountRegion);
	}

	@Bean
	@Autowired
	public OrderListener orderListener()
	{
		return new OrderListener();
	}

	@Bean
	@Autowired
	public ContinuousQueryListenerContainer orderListenerContainer(OrderListener orderListener, ClientCache gemfireCache)
	{
		ContinuousQueryListenerContainer container = new ContinuousQueryListenerContainer();
		container.setCache(gemfireCache);

		String query  = "SELECT * FROM /Order where status = 'COMPLETE'";
		ContinuousQueryDefinition cqd = new ContinuousQueryDefinition(query,orderListener);
		
		container.setQueryListeners(Collections.singleton(cqd));
		
		return container;
	}
}
