package com.vmware.spring.geode.showcase.account.repository

import com.vmware.spring.geode.showcase.account.domain.account.Account
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.PreparedStatementSetter
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import java.util.*

private val s = "select acct_id as id, acct_nm as name from ACCOUNTS where acct_id = ?"

/**
 * @author Gregory Green
 */
@Repository
class AccountJdbcRepository(private val jdbcTemplate: JdbcTemplate) : AccountRepository {

    private var logger: Logger = LogManager.getLogger(AccountJdbcRepository::class)
    private val updateSql = "update ACCOUNTS set acct_nm = ? where acct_id = ?"
    private val insertSql = "insert into ACCOUNTS (acct_id,acct_nm) values (?,?)"
    private val selectSql = "select acct_id as id, acct_nm as name from ACCOUNTS where acct_id = ?"

    override fun save(account: Account): Account {
        var updateSetter : PreparedStatementSetter = PreparedStatementSetter{ ps ->
            run {
                ps.setString(1, account.name)
                ps.setString(2, account.id)
            }
        }

        logger.info(updateSql);

        var cnt = jdbcTemplate.update(updateSql,
            updateSetter)


        var insertSetter : PreparedStatementSetter = PreparedStatementSetter{ ps ->
            run {
                ps.setString(1, account.id)
                ps.setString(2, account.name)
            }
        }

        if(cnt == 0)
        {
            logger.info(insertSql)
            jdbcTemplate.update(insertSql, insertSetter)
        }

        return account
    }

    override fun findById(id: String): Optional<Account> {

        var rowMapper: RowMapper<Account> = RowMapper<Account> { rs: ResultSet, rowIndex: Int ->
            Account(rs.getString(1), rs.getString(2))
        }

        logger.info(selectSql)

        try
        {
            var account : Account? = jdbcTemplate.queryForObject(
                selectSql,
                rowMapper,
                id
            )

            if(account == null)
                return Optional.empty()

            return Optional.of(account)
        }
        catch( e : EmptyResultDataAccessException)
        {
            return Optional.empty()
        }
    }
}