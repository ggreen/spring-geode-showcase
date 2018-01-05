package io.pivotal.gemfire.playground.orders.demo.dao;

import org.springframework.data.gemfire.repository.GemfireRepository;

import io.pivotal.gemfire.playground.orders.demo.domain.Account;

public interface AccountCrudRepository extends GemfireRepository<Account, String>
{
	
}
