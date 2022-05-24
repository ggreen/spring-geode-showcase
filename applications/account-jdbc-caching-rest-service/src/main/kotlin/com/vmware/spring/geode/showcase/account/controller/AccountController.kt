package com.vmware.spring.geode.showcase.account.controller

import com.vmware.spring.geode.showcase.account.domain.account.Account
import com.vmware.spring.geode.showcase.account.service.AccountService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * @author Gregory Green
 */
@RestController
class AccountController(private val accountService: AccountService) {

    @PostMapping("accounts")
    fun save(@RequestBody account: Account) {
        accountService.save(account)
    }

    @GetMapping("accounts/{id}")
    fun findByAccountId(@PathVariable("id") id: String): ResponseEntity<Account> {
        var account: Account? = accountService.findByAccountId(id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(account)
    }
}