package com.vmware.spring.geode.showcase.account.repository

import com.vmware.spring.geode.showcase.account.domain.account.Account
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AccountRepository {

    fun save(account: Account): Account

    fun findById(id: String): Optional<Account>
}