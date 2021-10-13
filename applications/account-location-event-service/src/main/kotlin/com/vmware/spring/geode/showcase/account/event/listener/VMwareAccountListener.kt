package com.vmware.spring.geode.showcase.account.event.listener

import com.vmware.spring.geode.showcase.account.domain.account.Location
import org.apache.geode.cache.query.CqEvent
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.data.gemfire.GemfireTemplate
import org.springframework.data.gemfire.listener.annotation.ContinuousQuery
import org.springframework.stereotype.Component

/**
 * @author Gregory Green
 */
@Component
class VMwareAccountListener(private val locationGemFireTemplate: GemfireTemplate) {
    private val log : Logger = LogManager.getLogger(VMwareAccountListener::class.java)

    @ContinuousQuery(name = "addVMwareLocation", query = "select * from /Account where name = 'VMware' ")
    fun addVMwareLocation(cqEvent: CqEvent) {
        log.info("==============Adding location!!!!!")

        var key = cqEvent.key.toString()
        val location : Location = Location()
        location.id = key
        location.address = "3401 Hillview Ave"
        location.city = "Palo Alto"
        location.stateCode = "CA"
        location.zipCode = "94304"

        locationGemFireTemplate.put(key,location)
    }
}