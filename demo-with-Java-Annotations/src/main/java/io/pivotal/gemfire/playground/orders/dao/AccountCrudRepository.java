package io.pivotal.gemfire.playground.orders.dao;

import org.springframework.data.gemfire.repository.GemfireRepository;

import io.pivotal.gemfire.playground.orders.domain.Account;

public interface AccountCrudRepository extends GemfireRepository<Account, String>
{
	
}
