package io.pivotal.gemfire.playground.orders;

import java.util.Properties;
import org.apache.geode.cache.Cache;
import org.apache.geode.cache.CacheFactory;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.RegionFactory;
import org.apache.geode.cache.RegionShortcut;
import org.apache.geode.pdx.ReflectionBasedAutoSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.GemfireTemplate;
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions;
import org.springframework.data.gemfire.config.annotation.EnablePdx;
import org.springframework.data.gemfire.config.annotation.EnableSecurity;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;
import io.pivotal.gemfire.playground.orders.domain.Account;

@EnableAutoConfiguration
@EnableGemfireRepositories
@EnablePdx(serializerBeanName = "pdxSerializer")
@EnableEntityDefinedRegions(basePackages = "io.pivotal.gemfire.playground.orders.domain")
@EnableSecurity
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
	
	/**
	 * 
	 * @param gemfireProperties the GemFire properties (see https://gemfire.docs.pivotal.io/geode/reference/topics/gemfire_properties.html)
	 * @return  cache
	 * @throws Exception
	 */
	@Bean
	@Autowired
	public Cache gemfireCache(@Qualifier("gemfireProperties") Properties gemfireProperties)
			throws Exception {

		Cache gemfireCache = new CacheFactory(gemfireProperties)
		.setPdxSerializer(
		new ReflectionBasedAutoSerializer("io.pivotal.gemfire.playground.orders.domain.*"))
		.create();
		
		return gemfireCache;
	}//------------------------------------------------
	
	@Bean
	@Autowired
	public RegionFactory<?, ?> partitionRegionFactory(@Qualifier("gemfireCache") 
	Cache gemfireCache)
	{
		return gemfireCache.createRegionFactory(RegionShortcut.PARTITION);
		
	}
	
	@Bean
	@Autowired
	@SuppressWarnings("unchecked")
	public Region<String,Account> accountRegion(@Qualifier("partitionRegionFactory") RegionFactory<?, ?> partitionRegionFactory )
	{
		return (Region<String,Account>)partitionRegionFactory.create("Accounts");
	}

	@Bean
	@Autowired
	@SuppressWarnings("unchecked")
	public Region<String,Account> orderRegion(@Qualifier("partitionRegionFactory") RegionFactory<?, ?> partitionRegionFactory )
	{
		return (Region<String,Account>)partitionRegionFactory.create("Order");
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

}
