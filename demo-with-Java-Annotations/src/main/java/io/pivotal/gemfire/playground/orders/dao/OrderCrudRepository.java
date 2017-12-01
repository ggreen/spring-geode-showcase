package io.pivotal.gemfire.playground.orders.dao;

import org.springframework.data.gemfire.repository.GemfireRepository;

import io.pivotal.gemfire.playground.orders.domain.Order;

public interface OrderCrudRepository extends GemfireRepository<Order, String>
{
	Order findBySymbol(String symbol);
}
