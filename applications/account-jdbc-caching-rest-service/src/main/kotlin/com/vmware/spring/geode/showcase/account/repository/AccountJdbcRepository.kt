package com.vmware.spring.geode.showcase.account.repository

import com.vmware.spring.geode.showcase.account.domain.account.Account
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.PreparedStatementSetter
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import java.util.*

/**
 * @author Gregory Green
 */
@Repository
class AccountJdbcRepository(private val jdbcTemplate: JdbcTemplate) : AccountRepository {


    override fun save(account: Account): Account {

        var updateSetter : PreparedStatementSetter = PreparedStatementSetter{ ps ->
            run {
                ps.setString(1, account.name)
                ps.setString(2, account.id)
            }
        }


        var cnt = jdbcTemplate.update("update ACCOUNTS set acct_nm = ? where acct_id = ?",
            updateSetter)


        var insertSetter : PreparedStatementSetter = PreparedStatementSetter{ ps ->
            run {
                ps.setString(1, account.id)
                ps.setString(2, account.name)
            }
        }

        if(cnt == 0)
        {
            jdbcTemplate.update("insert into ACCOUNTS (acct_id,acct_nm) values (?,?)", insertSetter)

        }

        return account
    }

    override fun findById(id: String): Optional<Account> {

        var rowMapper: RowMapper<Account> = RowMapper<Account> { rs: ResultSet, rowIndex: Int ->
            Account(rs.getString(1), rs.getString(2))
        }

        var account : Account? = jdbcTemplate.queryForObject("select acct_id as id, acct_nm as name from ACCOUNTS where acct_id = ?",
            rowMapper,
            id
        )

        if(account == null)
            return Optional.empty()

        return Optional.of(account)
    }
}