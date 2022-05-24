package com.vmware.spring.geode.showcase.account.event.durable

import com.vmware.spring.geode.showcase.account.domain.account.Account
import com.vmware.spring.geode.showcase.account.domain.account.Location
import com.vmware.spring.geode.showcase.account.event.durable.listener.VMwareAccountListener
import io.pivotal.services.dataTx.geode.client.GeodeClient
import org.apache.geode.cache.Region
import org.apache.geode.cache.client.ClientCache
import org.apache.geode.cache.client.ClientCacheFactory
import org.apache.geode.cache.query.CqAttributesFactory
import org.apache.geode.cache.query.CqListener
import org.apache.geode.cache.query.CqQuery
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


/**
 * @author Gregory Green
 */
@Configuration
class GeodeConfig {

    @Bean
    fun geode() : ClientCache
    {
        return ClientCacheFactory()
//            .set("durable-client-id","31")
//            .set("durable-client-timeout","9999")
            .set("name","account-location-durable-event-service")
            .addPoolLocator("127.0.0.1",10334)
            .setPoolSubscriptionEnabled(true)
            .create()

    }

    @Bean("Account")
    fun account(geodeClient: ClientCache): Region<String, Account>? {
        return GeodeClient.getRegion<String,Account>(geodeClient,"Account")
    }

    @Bean("Location")
    fun location(cache: ClientCache): Region<String, Location>? {
        return GeodeClient.getRegion<String,Location>(cache,"Location")
    }

    @Bean
    fun cqListener(cache: ClientCache,
                   @Qualifier("Account") accountRegion : Region<String,Account>,
                   @Qualifier("Location") locationRegion : Region<String,Location>) : CqQuery
    {
//        val region = GeodeClient.getRegion<String,Any>(cache,"Account");
//        region.registerInterestForAllKeys()
        val queryService = accountRegion.regionService.queryService

        // Create CqAttribute using CqAttributeFactory
        val cqf = CqAttributesFactory()

        val vmwareEventListener: CqListener = VMwareAccountListener(locationRegion)
        cqf.addCqListener(vmwareEventListener)
        val cqa = cqf.create()

        // Name of the CQ and its query
        val cqName = "vmware"
        val queryStr = "select * from /Account where name = 'VMware' "


        val cqQuery: CqQuery = queryService.newCq(cqName, queryStr, cqa,true)
        cqQuery.execute()

        println("RUNNING===================")

//        cache.readyForEvents()
        return cqQuery

    }
}