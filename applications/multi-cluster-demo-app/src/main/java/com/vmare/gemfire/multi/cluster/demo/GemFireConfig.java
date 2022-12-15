package com.vmare.gemfire.multi.cluster.demo;


import com.vmare.gemfire.multi.cluster.demo.domain.Claim;
import com.vmare.gemfire.multi.cluster.demo.domain.Member;
import org.apache.geode.cache.DataPolicy;
import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.client.Pool;
import org.apache.geode.cache.client.PoolManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.client.ClientRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.EnablePool;
import org.springframework.data.gemfire.config.annotation.EnablePools;

@Configuration
@ClientCacheApplication
@EnablePools(pools = {
        @EnablePool(name = "GemFireOne")
        })
public class GemFireConfig {

    @Value("${spring.data.gemfire.pool.default.locators}")
    private String locators;

    @Value("${spring.data.gemfire.pool.gemfiretwo.locators.host}")
    private String locator2PoolHost;

    @Value("${spring.data.gemfire.pool.gemfiretwo.locators.name}")
    private String locator2PoolName;

    @Value("${spring.data.gemfire.pool.gemfiretwo.locators.port}")
    private int locator2PoolPort;

    @Bean
    ClientRegionFactoryBean<String, Claim> claimRegion(GemFireCache gemFireCache,
                                                       @Qualifier("GemFireOne") Pool gemFireOnePool)
    {
        var region = new ClientRegionFactoryBean();
        region.setCache(gemFireCache);
        region.setDataPolicy(DataPolicy.EMPTY);
        region.setName("Claim");
        region.setPool(gemFireOnePool);
        return region;
    }

    @Bean
    ClientRegionFactoryBean<String, Member> memberRegion(GemFireCache gemFireCache)
    {
        var region = new ClientRegionFactoryBean();
        region.setCache(gemFireCache);
        region.setDataPolicy(DataPolicy.EMPTY);
        region.setName("Member");

        var p2 = PoolManager.createFactory().addLocator(locator2PoolHost,locator2PoolPort).create(locator2PoolName);

        region.setPool(p2);

        return region;
    }
}
