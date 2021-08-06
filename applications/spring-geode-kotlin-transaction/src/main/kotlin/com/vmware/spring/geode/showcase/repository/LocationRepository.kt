package com.vmware.spring.geode.showcase.repository

import com.vmware.spring.geode.showcase.domain.Location
import org.springframework.data.repository.CrudRepository

/**
 * @author Gregory Green
 */
interface LocationRepository : CrudRepository<Location,String> {
}