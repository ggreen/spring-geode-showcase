package com.vmware.spring.geode.showcase.account.event.durable.listener

import com.vmware.spring.geode.showcase.account.domain.account.Account
import org.apache.geode.cache.EntryEvent
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

internal class VMwareAccountCacheListenerTest{

    @Test
    internal fun afterCreate() {

        var subject = VMwareAccountCacheListener()
        val event : EntryEvent<String, Account> = mock<EntryEvent<String,Account>>();
        subject.afterCreate(event)

        verify(event).key
    }

    @Test
    internal fun afterUpdate() {

        var subject = VMwareAccountCacheListener()
        val event : EntryEvent<String, Account> = mock<EntryEvent<String,Account>>();
        subject.afterUpdate(event)

        verify(event).key
    }
}