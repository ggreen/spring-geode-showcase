package com.vmware.spring.geode.showcase.account;

import com.vmware.spring.geode.showcase.account.domain.account.Account;
import com.vmware.spring.geode.showcase.account.domain.account.Location;
import org.apache.geode.cache.DataPolicy;
import org.apache.geode.cache.GemFireCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.client.ClientRegionFactoryBean;
import org.springframework.data.gemfire.client.Interest;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.EnableSecurity;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;
import org.springframework.data.gemfire.transaction.config.EnableGemfireCacheTransactions;

/**
 * @author Gregory Green
 */
@ClientCacheApplication(subscriptionEnabled = true)
@Configuration
@EnableSecurity
@EnableGemfireCacheTransactions
@EnableGemfireRepositories
public class GeodeConf
{
    @Bean("Account")
    ClientRegionFactoryBean<String, Account> account(GemFireCache gemfireCache)
    {
        var bean= new ClientRegionFactoryBean<String,Account>();
        bean.setCache(gemfireCache);
        bean.setDataPolicy(DataPolicy.EMPTY);
        return bean;
    }

    @Bean("Location")
    ClientRegionFactoryBean<String, Location> location(GemFireCache gemfireCache)
    {
        Interest[] interests = {new Interest(".*")};
        var bean= new ClientRegionFactoryBean<String,Location>();
        bean.setCache(gemfireCache);
        bean.setDataPolicy(DataPolicy.NORMAL);
        bean.setInterests(interests);
        return bean;
    }


}
