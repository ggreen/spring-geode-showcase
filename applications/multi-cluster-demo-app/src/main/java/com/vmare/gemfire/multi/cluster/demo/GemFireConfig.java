package com.vmare.gemfire.multi.cluster.demo;


import com.vmare.gemfire.multi.cluster.demo.domain.Claim;
import com.vmare.gemfire.multi.cluster.demo.domain.Member;
import org.apache.geode.cache.DataPolicy;
import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.client.Pool;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.client.ClientRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.EnablePool;
import org.springframework.data.gemfire.config.annotation.EnablePools;

@Configuration
@EnablePools(pools = {
        @EnablePool(name = "GemFireOne"),
        @EnablePool(name = "GemFireTwo")
        })

public class GemFireConfig {

    @Value("${spring.data.gemfire.pool.default.locators}")
    private String locators;

    @Bean
    ClientRegionFactoryBean<String, Claim> claimRegion(GemFireCache gemFireCache,
                                                       @Qualifier("GemFireOne") Pool poolForPccTwo)
    {
        var region = new ClientRegionFactoryBean();
        region.setCache(gemFireCache);
        region.setDataPolicy(DataPolicy.EMPTY);
        region.setName("Claim");
        return region;
    }

    @Bean
    ClientRegionFactoryBean<String, Member> memberRegion(GemFireCache gemFireCache,
                                                         @Qualifier("GemFireTwo") Pool poolForPccTwo)
    {
        var region = new ClientRegionFactoryBean();
        region.setCache(gemFireCache);
        region.setDataPolicy(DataPolicy.EMPTY);
        region.setName("Member");
        return region;
    }
}
