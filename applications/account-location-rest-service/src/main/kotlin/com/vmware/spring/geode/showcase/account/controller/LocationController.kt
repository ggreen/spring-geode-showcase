package com.vmware.spring.geode.showcase.account.controller

import com.vmware.spring.geode.showcase.account.domain.account.Location
import com.vmware.spring.geode.showcase.account.repository.LocationRepository
import org.springframework.web.bind.annotation.*

/**
 * @author Gregory Green
 */
@RestController
class LocationController(private var locationRepository: LocationRepository) {
    @PostMapping("locations/location")
    fun saveLocation(@RequestBody location: Location) {
        locationRepository.save(location)
    }

    @GetMapping("locations/location/{id}")
    fun findLocation(@PathVariable id: String): Location? {
        var results = locationRepository.findById(id)
        if(results.isEmpty)
            return null

        return results.get()
    }
}