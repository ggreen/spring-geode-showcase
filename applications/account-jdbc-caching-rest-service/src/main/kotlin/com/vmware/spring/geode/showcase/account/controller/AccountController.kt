package com.vmware.spring.geode.showcase.account.controller

import com.vmware.spring.geode.showcase.account.domain.account.Account
import com.vmware.spring.geode.showcase.account.repository.AccountRepository
import org.springframework.http.HttpEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * @author Gregory Green
 */
@RestController
class AccountController(private val accountRepository: AccountRepository) {

    @PostMapping("accounts")
    fun save(@RequestBody account: Account) {
        accountRepository.save(account)
    }

    @GetMapping("accounts/{id}")
    fun findByAccountId(@PathVariable("id") id: String): ResponseEntity<Account> {
        var optional = accountRepository.findById(id)
        if(optional.isEmpty)
            return ResponseEntity.notFound().build()
        return ResponseEntity.ok(optional.get())
    }
}