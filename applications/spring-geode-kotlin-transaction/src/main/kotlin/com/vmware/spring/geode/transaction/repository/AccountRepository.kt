package com.vmware.spring.geode.transaction.repository

import com.vmware.spring.geode.transaction.domain.Account
import org.springframework.data.repository.CrudRepository

interface AccountRepository : CrudRepository<Account,String> {
}