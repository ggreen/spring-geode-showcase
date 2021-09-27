package com.vmware.spring.geode.showcase.account.service

import com.vmware.spring.geode.showcase.account.domain.account.Account
import com.vmware.spring.geode.showcase.account.repository.AccountRepository
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

/**
 * @author Gregory Green
 */
@Service
class AccountDataService (private val accountRepository : AccountRepository) : AccountService {

    @CacheEvict(value = ["AccountCache"], key = "#account.id")
    override fun save(account: Account): Account {
        return accountRepository.save(account)
    }

    @Cacheable(value = ["AccountCache"])
    override fun findByAccountId(id: String): Account? {
        var optional = accountRepository.findById(id)
        if (optional.isEmpty)
            return null

        return optional.get()
    }
}
