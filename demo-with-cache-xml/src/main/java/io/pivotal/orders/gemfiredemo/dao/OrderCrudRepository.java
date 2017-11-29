package io.pivotal.orders.gemfiredemo.dao;

import org.springframework.data.gemfire.repository.GemfireRepository;

import io.pivotal.orders.gemfiredemo.domain.Order;

public interface OrderCrudRepository extends GemfireRepository<Order, String>
{
	Order findBySymbol(String symbol);
}
