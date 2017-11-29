package io.pivotal.orders.gemfiredemo.dao;

import org.springframework.data.gemfire.repository.GemfireRepository;

import io.pivotal.orders.gemfiredemo.domain.Account;

public interface AccountCrudRepository extends GemfireRepository<Account, String>
{
	
}
