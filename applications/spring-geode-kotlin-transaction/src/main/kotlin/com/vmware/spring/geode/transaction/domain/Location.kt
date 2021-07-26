package com.vmware.spring.geode.transaction.domain

data class Location(
    var id: String,
    var address: String,
    var city: String,
    var stateCode: String,
    var zipCode: String
)
