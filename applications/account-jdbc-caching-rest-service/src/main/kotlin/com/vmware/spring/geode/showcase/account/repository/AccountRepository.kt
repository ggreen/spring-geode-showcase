package com.vmware.spring.geode.showcase.account.repository

import com.vmware.spring.geode.showcase.account.domain.account.Account
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AccountRepository {

    fun save(var1: Account): Account

    fun findById(var1: String): Optional<Account>
}