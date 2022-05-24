package com.vmware.spring.geode.showcase.account.event.durable

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AccountLocationDurableEventApp

fun main(args: Array<String>) {
	runApplication<AccountLocationDurableEventApp>(*args)
}
