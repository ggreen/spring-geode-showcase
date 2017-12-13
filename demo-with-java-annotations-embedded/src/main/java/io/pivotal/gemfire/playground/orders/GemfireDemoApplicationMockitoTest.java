package io.pivotal.gemfire.playground.orders;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Mockito.*;

import java.util.Optional;

import io.pivotal.gemfire.playground.orders.dao.AccountCrudRepository;
import io.pivotal.gemfire.playground.orders.dao.OrderCrudRepository;
import io.pivotal.gemfire.playground.orders.domain.Account;
import io.pivotal.gemfire.playground.orders.domain.Order;

public class GemfireDemoApplicationMockitoTest
{
	
	private  static GemfireDemoApplication app = null;
	private static Account account;
	private static Order order;
	private static Optional<Account> accOpt;
	private static Optional<Order> orderOpt;

	@BeforeClass
	public static void setup()
	{
		account = new Account();
		app = new GemfireDemoApplication();
		app.accountCrudRepos = mock(AccountCrudRepository.class);
		accOpt = Optional.of(account);
		when(app.accountCrudRepos.findById(any())).thenReturn(accOpt);
		
		order = new Order();
		
		app.orderCrudRepos = mock(OrderCrudRepository.class);
		orderOpt = Optional.of(order);
		when(app.orderCrudRepos.findById(any())).thenReturn(orderOpt);
		when(app.orderCrudRepos.findBySymbol(any())).thenReturn(order);
		
	}//------------------------------------------------
	@Test
	public void testSaveAccount()
	{
		account.setId("junit");
		account.setName("account");
		
		assertTrue(app.saveAccount(account));
		assertEquals(account,app.findAccountById(account.getId()));
	}
	@Test
	public void testSaveOrder()
	{
		order.setId("junit");
		order.setSymbol("JU");
		
		assertTrue(app.saveOrder(order));
		assertEquals(order,app.findOrderById(order.getId()));
	}
	
	@Test
	public void testFindOrderBySymbol()
	{
		assertTrue(app.saveOrder(order));
		assertEquals(order,app.findOrderBySymbol(order.getSymbol()));
		
	}

}
