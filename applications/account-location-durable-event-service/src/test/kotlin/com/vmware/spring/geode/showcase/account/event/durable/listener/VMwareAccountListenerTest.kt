//package com.vmware.spring.geode.showcase.account.event.durable.listener
//
//import com.vmware.spring.geode.showcase.account.domain.account.Location
//import org.apache.geode.cache.Region
//import org.apache.geode.cache.query.CqEvent
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.Test
//import org.mockito.kotlin.any
//import org.mockito.kotlin.mock
//import org.mockito.kotlin.verify
//import org.mockito.kotlin.whenever
//
//internal class VMwareAccountListenerTest{
//    private lateinit var locationGemFireTemplate: Region<String, Location>
//
//    @BeforeEach
//    internal fun setUp() {
//        locationGemFireTemplate = mock<Region<String,Location>>()
//    }
//
//    @Test
//    internal fun addVMwareLocation() {
//        var expectedKey = "1"
//        var cqEvent : CqEvent = mock<CqEvent>()
//        whenever(cqEvent.key).thenReturn(expectedKey)
//        var subject : VMwareAccountListener = VMwareAccountListener(locationGemFireTemplate)
//
////        subject.addVMwareLocation(cqEvent)
//        verify(locationGemFireTemplate).put(any<String>(),any<Location>())
//
//    }
//}