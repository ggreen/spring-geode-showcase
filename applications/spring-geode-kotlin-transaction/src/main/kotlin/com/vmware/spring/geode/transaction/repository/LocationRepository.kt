package com.vmware.spring.geode.transaction.repository

import com.vmware.spring.geode.transaction.domain.Location
import org.springframework.data.repository.CrudRepository

/**
 * @author Gregory Green
 */
interface LocationRepository : CrudRepository<Location,String> {
}