package com.vmware.spring.geode.showcase.account.controller

import com.vmware.spring.geode.showcase.account.domain.account.Account
import com.vmware.spring.geode.showcase.account.repository.AccountRepository
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.springframework.http.ResponseEntity
import java.util.*

internal class AccountControllerTest{
    private val account: Account = JavaBeanGeneratorCreator.of(Account::class.java).create()
    private lateinit var accountRepository: AccountRepository
    private lateinit var subject : AccountController

    @BeforeEach
    internal fun setUp() {
        accountRepository = mock<AccountRepository>(){
            on{ findById(any<String>())} doReturn Optional.of(account)
        }
        subject = AccountController(accountRepository)
    }

    @Test
    internal fun save() {
        subject.save(account)
        verify(accountRepository).save(any<Account>())
    }




    @Test
    internal fun findAccount() {
        assertEquals(ResponseEntity.ok(account),subject.findByAccountId(account.id));

    }


    @Test
    internal fun findAccountNotFound_WhenEmpty() {

        accountRepository = mock<AccountRepository>(){
            on{ findById(any<String>())} doReturn Optional.empty()
        }
        subject = AccountController(accountRepository)

        assertEquals(ResponseEntity.notFound().build<Account>(),subject.findByAccountId("doesNotExist"));

    }
}