package io.pivotal.orders.gemfiredemo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.annotation.Region;

@Region("Accounts")
public class Account
{
	
	@Id
	private String id;
	private String name;
	/**
	 * @return the id
	 */
	public String getId()
	{
		return id;
	}
	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id)
	{
		this.id = id;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	
	
}
