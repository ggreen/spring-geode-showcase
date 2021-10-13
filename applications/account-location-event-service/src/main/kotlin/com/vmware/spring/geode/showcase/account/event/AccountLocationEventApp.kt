package com.vmware.spring.geode.showcase.account.event

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AccountLocationEventApp

fun main(args: Array<String>) {
	runApplication<AccountLocationEventApp>(*args)
}
