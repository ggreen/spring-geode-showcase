package com.vmware.spring.geode.showcase.account.event

import com.vmware.spring.geode.showcase.account.domain.account.Account
import com.vmware.spring.geode.showcase.account.domain.account.Location
import com.vmware.spring.geode.showcase.account.event.durable.listener.VMwareAccountCacheListener
import org.apache.geode.cache.DataPolicy
import org.apache.geode.cache.GemFireCache
import org.apache.geode.cache.InterestResultPolicy
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.gemfire.GemfireTemplate
import org.springframework.data.gemfire.client.ClientRegionFactoryBean
import org.springframework.data.gemfire.client.Interest
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication
import org.springframework.data.gemfire.config.annotation.EnableContinuousQueries

/**
 * @author Gregory Green
 */
@ClientCacheApplication(subscriptionEnabled = true, readyForEvents = true) //seconds
@EnableContinuousQueries
@Configuration
class GeodeConfig {

    @Value("\${spring.data.gemfire.cache.client.durable-client-id}")
    private var durableClientId : String = ""

    @Value("\${spring.data.gemfire.cache.client.durable-client-timeout}")
    private var durableClientTimeout : Int = 1000


    @Value("\${spring.data.gemfire.pool.subscription-redundancy}")
    private var subscriptionRedundancy : Int = 1

    @Value("\${keyInterestRegExp}")
    private var keyInterestRegExp: String = ".*";

    @Bean("Account")
    fun account(gemFireCache: GemFireCache) : ClientRegionFactoryBean<String,Account>
    {
        var region = ClientRegionFactoryBean<String,Account>()
        region.cache = gemFireCache
        region.setRegionName("Account")
        region.setDataPolicy(DataPolicy.EMPTY)
        val durable = true
        var interest :Interest<String> = Interest(keyInterestRegExp, InterestResultPolicy.KEYS,durable)
        region.setInterests(arrayOf(interest))
        region.statisticsEnabled =true

        region.setCacheListeners(arrayOf(VMwareAccountCacheListener()))

        return region
    }

    @Bean("Location")
    fun location(gemFireCache: GemFireCache,@Qualifier("accountTemplate")accountTemplate : GemfireTemplate) : ClientRegionFactoryBean<String,Location>
    {
        var region = ClientRegionFactoryBean<String,Location>()
        region.cache = gemFireCache
        region.setRegionName("Location")
        region.setDataPolicy(DataPolicy.EMPTY)
        val durable = true
        var interest :Interest<String> = Interest(".*", InterestResultPolicy.KEYS,durable)
        region.setInterests(arrayOf(interest))
        region.statisticsEnabled =true


        return region
    }
}