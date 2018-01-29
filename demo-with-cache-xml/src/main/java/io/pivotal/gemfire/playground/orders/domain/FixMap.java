package io.pivotal.gemfire.playground.orders.domain;

public class FixMap
{
	
	
	public FixMap()
	{
	}
	
	public FixMap(String fixString)
	{
		this.fixString = fixString;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("FixMap [fixString=").append(fixString).append("]");
		return builder.toString();
	}

	/**
	 * @return the fixString
	 */
	public String getFixString()
	{
		return fixString;
	}

	/**
	 * @param fixString the fixString to set
	 */
	public void setFixString(String fixString)
	{
		this.fixString = fixString;
	}

	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fixString == null) ? 0 : fixString.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FixMap other = (FixMap) obj;
		if (fixString == null)
		{
			if (other.fixString != null)
				return false;
		}
		else if (!fixString.equals(other.fixString))
			return false;
		return true;
	}



	private String fixString = "8u003dFIX.4.2u00019u003d174u000135u003d8u000134u003d52u000149u003dFIXIMULATORu000152u003d20180104-16:07:49.361u000156u003dGFR1u00016u003d0u000111u003dgfr.17535.13u000114u003d0u000117u003dE1515082069374u000120u003d0u000131u003d0u000132u003d0u000137u003dO1515082069374u000138u003d300u000139u003d0u000154u003d2u000155u003dGOOGu0001150u003d0u0001151u003d300u000110u003d148u0001";
	
}
