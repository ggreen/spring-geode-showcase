package com.vmware.spring.geode.showcase

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringGeodeKotlinTransactionApplication

fun main(args: Array<String>) {
	runApplication<SpringGeodeKotlinTransactionApplication>(*args)
}
