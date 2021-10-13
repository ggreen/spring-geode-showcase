package com.vmware.spring.geode.showcase.account.controller

import com.vmware.spring.geode.showcase.account.domain.account.Location
import com.vmware.spring.geode.showcase.account.repository.LocationRepository
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.verify
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.*

/**
 * @author Gregory Green
 */
class LocationControllerTest {

    private val location: Location = JavaBeanGeneratorCreator.of(Location::class.java).create()
    private lateinit var locationRepository: LocationRepository
    private lateinit var subject : LocationController

    @BeforeEach
    internal fun setUp() {
        locationRepository = mock<LocationRepository>()
        subject = LocationController(locationRepository);
    }

    @Test
    internal fun saveLocation() {
        subject.saveLocation(location);
        verify(locationRepository).save(any<Location>())
    }


    @Test
    internal fun findLocation() {
        whenever(locationRepository.findById(any())).thenReturn(Optional.of(location))
        assertEquals(location,subject.findLocation(location.id));
    }
}