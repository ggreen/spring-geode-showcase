package com.vmware.spring.geode.showcase.account.service

import com.vmware.spring.geode.showcase.account.domain.account.Account

/**
 * @author Gregory Green
 */
interface AccountService {
    fun save(account: Account) : Account

    fun findByAccountId(id: String): Account?
}