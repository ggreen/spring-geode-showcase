package com.vmware.spring.geode.showcase.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GatewayLoadBalancerRestApp

fun main(args: Array<String>) {
	runApplication<GatewayLoadBalancerRestApp>(*args)
}
