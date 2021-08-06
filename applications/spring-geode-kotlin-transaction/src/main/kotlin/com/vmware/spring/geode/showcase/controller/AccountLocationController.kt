package com.vmware.spring.geode.showcase.controller

import com.vmware.spring.geode.showcase.domain.AccountLocation
import com.vmware.spring.geode.showcase.repository.AccountRepository
import com.vmware.spring.geode.showcase.repository.LocationRepository
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.lang.IllegalArgumentException

/**
 * @author Gregory Green
 */
@RestController
class AccountLocationController(
    private val accountRepository: AccountRepository,
    private val locationRepository: LocationRepository) {
    private val validZipRegEx = "^\\d{5}(?:[-\\s]\\d{4})?\$".toRegex();

    @PostMapping("save")
    @Transactional
    fun save(@RequestBody accountLocation: AccountLocation) {
        accountRepository.save(accountLocation.account)

        var location = accountLocation.location;
        if(!location.zipCode.matches(validZipRegEx))
            throw IllegalArgumentException("Invalid zip code ${location.zipCode}");

        locationRepository.save(accountLocation.location)
    }
}