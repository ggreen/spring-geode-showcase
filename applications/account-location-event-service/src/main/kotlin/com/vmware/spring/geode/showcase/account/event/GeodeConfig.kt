package com.vmware.spring.geode.showcase.account.event

import com.vmware.spring.geode.showcase.account.domain.account.Location
import org.apache.geode.cache.GemFireCache
import org.apache.geode.cache.client.ClientCacheFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.gemfire.GemfireTemplate
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication
import org.springframework.data.gemfire.config.annotation.EnableClusterDefinedRegions
import org.springframework.data.gemfire.config.annotation.EnableContinuousQueries

/**
 * @author Gregory Green
 */
@ClientCacheApplication(subscriptionEnabled = true)
@EnableContinuousQueries
@EnableClusterDefinedRegions
@Configuration
class GeodeConfig {
    @Bean
    fun gemFireTemplate(gemFireCache: GemFireCache) : GemfireTemplate
    {
        return GemfireTemplate<String,Location>(ClientCacheFactory.getAnyInstance().getRegion("Location"))
    }
}