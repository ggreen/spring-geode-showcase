package com.vmware.spring.geode.showcase.account.repository

import com.vmware.spring.geode.showcase.account.domain.account.Location
import org.springframework.data.repository.CrudRepository

/**
 * @author Gregory Green
 */
interface LocationRepository : CrudRepository<Location,String> {
}