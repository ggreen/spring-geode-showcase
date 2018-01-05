package io.pivotal.gemfire.playground.orders.demo.dao;

import org.springframework.data.gemfire.repository.GemfireRepository;

import io.pivotal.gemfire.playground.orders.demo.domain.Order;

public interface OrderCrudRepository extends GemfireRepository<Order, String>
{
	Order findBySymbol(String symbol);
}
