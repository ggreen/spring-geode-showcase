package com.vmware.spring.geode.showcase.account.repository

import com.vmware.spring.geode.showcase.account.domain.account.Account
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.PreparedStatementSetter
import org.springframework.jdbc.core.RowMapper
import java.util.*

internal class AccountJdbcRepositoryTest{

    private val account: Account = JavaBeanGeneratorCreator.of(Account::class.java).create()
    private lateinit var jdbcTemplate: JdbcTemplate
    private lateinit var subject : AccountJdbcRepository


    @BeforeEach
    internal fun setUp() {
        jdbcTemplate = mock<JdbcTemplate>()
        {
            on { queryForObject(any<String>(), any<RowMapper<Account>>(),any<String>())} doReturn account
        }
        subject = AccountJdbcRepository(jdbcTemplate)
    }

    @Test
    internal fun save() {

        var actual = subject.save(account)
        verify(jdbcTemplate, atLeastOnce()).update(any<String>(),any<PreparedStatementSetter>())
        assertEquals(account,actual);
    }

    @Test
    internal fun findById() {
        assertEquals(Optional.of(account),subject.findById(account.id));
    }
}