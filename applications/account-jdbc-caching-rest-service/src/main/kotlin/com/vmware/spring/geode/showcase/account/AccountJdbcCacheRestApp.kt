package com.vmware.spring.geode.showcase.account

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AccountJdbcCacheRestApp

fun main(args: Array<String>) {
	runApplication<AccountJdbcCacheRestApp>(*args)
}
