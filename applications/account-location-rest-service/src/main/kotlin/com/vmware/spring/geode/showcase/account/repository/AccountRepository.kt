package com.vmware.spring.geode.showcase.account.repository

import com.vmware.spring.geode.showcase.account.domain.account.Account
import org.springframework.data.repository.CrudRepository

interface AccountRepository : CrudRepository<Account,String> {
}