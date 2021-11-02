package com.vmware.spring.geode.showcase.gateway

import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.PredicateSpec
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @author Gregory Green
 */
@Configuration
class GatewayConfig {
    private val uris : Array<String> = arrayOf("http://localhost:8000/","http://localhost:8001/")
    private var i : Int = 0

    @Bean
    fun myRoutes(builder: RouteLocatorBuilder): RouteLocator? {

       var results = builder.routes()
            .route { p: PredicateSpec ->
                p
                    .path("/geode/**")
                    .uri(uris[i])
            }
            .build()

        i = (i+1)% uris.size
        return results
    }
}