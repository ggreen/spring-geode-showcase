package com.vmware.spring.geode.showcase.account.event.durable.listener

import com.vmware.spring.geode.showcase.account.domain.account.Account
import org.apache.geode.cache.EntryEvent
import org.apache.geode.cache.util.CacheListenerAdapter

/**
 * @author Gregory Green
 */
class VMwareAccountCacheListener : CacheListenerAdapter<String, Account>() {

    override fun afterCreate(event: EntryEvent<String, Account>?) {
        processEvent(event!!)
    }

    override fun afterUpdate(event: EntryEvent<String, Account>?) {
        processEvent(event!!)
    }

    private fun processEvent(event : EntryEvent<String, Account>)
    {
        println("*********************** KEY ${event.key} EVENT ${event.newValue}")
    }
}