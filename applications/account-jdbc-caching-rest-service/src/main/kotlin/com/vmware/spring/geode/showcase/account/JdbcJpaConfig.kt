package com.vmware.spring.geode.showcase.account

import com.vmware.spring.geode.showcase.account.domain.account.Account
import com.vmware.spring.geode.showcase.account.repository.AccountJdbcRepository
import com.vmware.spring.geode.showcase.account.repository.AccountRepository
import org.hibernate.cfg.AvailableSettings
import org.hibernate.dialect.PostgreSQL9Dialect
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.hibernate5.SpringSessionContext
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource


@Configuration
class JdbcJpaConfig {

//    @Bean
//    fun accountJdbcRepository() : AccountRepository
//    {
//        return AccountJdbcRepository()
//    }
}