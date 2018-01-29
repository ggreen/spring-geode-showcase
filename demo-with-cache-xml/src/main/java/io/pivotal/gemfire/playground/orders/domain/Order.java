package io.pivotal.gemfire.playground.orders.domain;

import org.apache.geode.pdx.PdxReader;
import org.apache.geode.pdx.PdxSerializable;
import org.apache.geode.pdx.PdxWriter;

public class Order implements PdxSerializable
{
	public Order () {}
	
	

	public Order(String orderId, String clOrdId, String orderStatus, String orderSource, String rootId,
	String parentId, String msgType, String orderString, String tsReceived, String tsSent, double avgPx, double cumQty,
	double orderQty, double leaves)
	{
		this.orderId = orderId;
		this.clOrdId = clOrdId;
		this.orderStatus = orderStatus;
		this.orderSource = orderSource;
		this.rootId = rootId;
		this.parentId = parentId;
		this.msgType = msgType;
		this.orderString = orderString;
		this.tsReceived = tsReceived;
		this.tsSent = tsSent;
		this.avgPx = avgPx;
		this.cumQty = cumQty;
		this.orderQty = orderQty;
		this.leaves = leaves;
	}



	public Order(String orderId)
	{
		this.orderId = orderId;
	}



	/**
	 * @return the orderId
	 */
	public String getOrderId()
	{
		return orderId;
	}
	/**
	 * @return the clOrdId
	 */
	public String getClOrdId()
	{
		return clOrdId;
	}
	/**
	 * @return the orderStatus
	 */
	public String getOrderStatus()
	{
		return orderStatus;
	}
	/**
	 * @return the orderSource
	 */
	public String getOrderSource()
	{
		return orderSource;
	}
	/**
	 * @return the rootId
	 */
	public String getRootId()
	{
		return rootId;
	}
	/**
	 * @return the parentId
	 */
	public String getParentId()
	{
		return parentId;
	}
	/**
	 * @return the msgType
	 */
	public String getMsgType()
	{
		return msgType;
	}
	/**
	 * @return the orderString
	 */
	public String getOrderString()
	{
		return orderString;
	}
	/**
	 * @return the tsReceived
	 */
	public String getTsReceived()
	{
		return tsReceived;
	}
	/**
	 * @return the tsSent
	 */
	public String getTsSent()
	{
		return tsSent;
	}
	/**
	 * @return the avgPx
	 */
	public double getAvgPx()
	{
		return avgPx;
	}
	/**
	 * @return the cumQty
	 */
	public double getCumQty()
	{
		return cumQty;
	}
	/**
	 * @return the orderQty
	 */
	public double getOrderQty()
	{
		return orderQty;
	}
	/**
	 * @return the leaves
	 */
	public double getLeaves()
	{
		return leaves;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId)
	{
		this.orderId = orderId;
	}
	/**
	 * @param clOrdId the clOrdId to set
	 */
	public void setClOrdId(String clOrdId)
	{
		this.clOrdId = clOrdId;
	}
	/**
	 * @param orderStatus the orderStatus to set
	 */
	public void setOrderStatus(String orderStatus)
	{
		this.orderStatus = orderStatus;
	}
	/**
	 * @param orderSource the orderSource to set
	 */
	public void setOrderSource(String orderSource)
	{
		this.orderSource = orderSource;
	}
	/**
	 * @param rootId the rootId to set
	 */
	public void setRootId(String rootId)
	{
		this.rootId = rootId;
	}
	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(String parentId)
	{
		this.parentId = parentId;
	}
	/**
	 * @param msgType the msgType to set
	 */
	public void setMsgType(String msgType)
	{
		this.msgType = msgType;
	}
	/**
	 * @param orderString the orderString to set
	 */
	public void setOrderString(String orderString)
	{
		this.orderString = orderString;
	}
	/**
	 * @param tsReceived the tsReceived to set
	 */
	public void setTsReceived(String tsReceived)
	{
		this.tsReceived = tsReceived;
	}
	/**
	 * @param tsSent the tsSent to set
	 */
	public void setTsSent(String tsSent)
	{
		this.tsSent = tsSent;
	}
	/**
	 * @param avgPx the avgPx to set
	 */
	public void setAvgPx(double avgPx)
	{
		this.avgPx = avgPx;
	}
	/**
	 * @param cumQty the cumQty to set
	 */
	public void setCumQty(double cumQty)
	{
		this.cumQty = cumQty;
	}
	/**
	 * @param orderQty the orderQty to set
	 */
	public void setOrderQty(double orderQty)
	{
		this.orderQty = orderQty;
	}
	/**
	 * @param leaves the leaves to set
	 */
	public void setLeaves(double leaves)
	{
		this.leaves = leaves;
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(avgPx);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((clOrdId == null) ? 0 : clOrdId.hashCode());
		temp = Double.doubleToLongBits(cumQty);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(leaves);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((msgType == null) ? 0 : msgType.hashCode());
		result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
		temp = Double.doubleToLongBits(orderQty);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((orderSource == null) ? 0 : orderSource.hashCode());
		result = prime * result + ((orderStatus == null) ? 0 : orderStatus.hashCode());
		result = prime * result + ((orderString == null) ? 0 : orderString.hashCode());
		result = prime * result + ((parentId == null) ? 0 : parentId.hashCode());
		result = prime * result + ((rootId == null) ? 0 : rootId.hashCode());
		result = prime * result + ((tsReceived == null) ? 0 : tsReceived.hashCode());
		result = prime * result + ((tsSent == null) ? 0 : tsSent.hashCode());
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
		Order other = (Order) obj;
		if (Double.doubleToLongBits(avgPx) != Double.doubleToLongBits(other.avgPx))
			return false;
		if (clOrdId == null)
		{
			if (other.clOrdId != null)
				return false;
		}
		else if (!clOrdId.equals(other.clOrdId))
			return false;
		if (Double.doubleToLongBits(cumQty) != Double.doubleToLongBits(other.cumQty))
			return false;
		if (Double.doubleToLongBits(leaves) != Double.doubleToLongBits(other.leaves))
			return false;
		if (msgType == null)
		{
			if (other.msgType != null)
				return false;
		}
		else if (!msgType.equals(other.msgType))
			return false;
		if (orderId == null)
		{
			if (other.orderId != null)
				return false;
		}
		else if (!orderId.equals(other.orderId))
			return false;
		if (Double.doubleToLongBits(orderQty) != Double.doubleToLongBits(other.orderQty))
			return false;
		if (orderSource == null)
		{
			if (other.orderSource != null)
				return false;
		}
		else if (!orderSource.equals(other.orderSource))
			return false;
		if (orderStatus == null)
		{
			if (other.orderStatus != null)
				return false;
		}
		else if (!orderStatus.equals(other.orderStatus))
			return false;
		if (orderString == null)
		{
			if (other.orderString != null)
				return false;
		}
		else if (!orderString.equals(other.orderString))
			return false;
		if (parentId == null)
		{
			if (other.parentId != null)
				return false;
		}
		else if (!parentId.equals(other.parentId))
			return false;
		if (rootId == null)
		{
			if (other.rootId != null)
				return false;
		}
		else if (!rootId.equals(other.rootId))
			return false;
		if (tsReceived == null)
		{
			if (other.tsReceived != null)
				return false;
		}
		else if (!tsReceived.equals(other.tsReceived))
			return false;
		if (tsSent == null)
		{
			if (other.tsSent != null)
				return false;
		}
		else if (!tsSent.equals(other.tsSent))
			return false;
		return true;
	}




	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("OrderDetail [orderId=").append(orderId).append(", clOrdId=").append(clOrdId)
		.append(", orderStatus=").append(orderStatus).append(", orderSource=").append(orderSource).append(", rootId=")
		.append(rootId).append(", parentId=").append(parentId).append(", msgType=").append(msgType)
		.append(", orderString=").append(orderString).append(", tsReceived=").append(tsReceived).append(", tsSent=")
		.append(tsSent).append(", avgPx=").append(avgPx).append(", cumQty=").append(cumQty).append(", orderQty=")
		.append(orderQty).append(", leaves=").append(leaves).append("]");
		return builder.toString();
	}


	private String orderId;
	private String clOrdId;
	private String orderStatus;
	private String orderSource;
	private String rootId;
	private String parentId;
	private String msgType;

	private String orderString;
	private String tsReceived, tsSent;
	private double avgPx;
	private double cumQty;
	private double orderQty;
	private double leaves;
	@Override
	public void fromData(PdxReader pdxReader)
	{
		this.setAvgPx(pdxReader.readDouble("avgPx"));
		this.setClOrdId(pdxReader.readString("clOrdId"));
		this.setCumQty(pdxReader.readDouble("cumQty"));
		this.setLeaves(pdxReader.readDouble("leaves"));
		this.setMsgType(pdxReader.readString("msgType"));
		this.setOrderId(pdxReader.readString("orderId"));
		this.setOrderQty(pdxReader.readDouble("orderQty"));
		this.setOrderSource(pdxReader.readString("orderSource"));
		this.setOrderStatus(pdxReader.readString("orderStatus"));
		this.setOrderString(pdxReader.readString("orderString"));
		this.setParentId(pdxReader.readString("parentId"));
		this.setRootId(pdxReader.readString("rootId"));
		this.setTsReceived(pdxReader.readString("tsReceived"));
		this.setTsSent(pdxReader.readString("tsSent"));
		
	}



	@Override
	public void toData(PdxWriter pdxWriter)
	{
		
		
		pdxWriter.writeDouble("avgPx",this.getAvgPx());
		pdxWriter.writeString("clOrdId",this.getClOrdId());
		pdxWriter.writeDouble("cumQty",this.getCumQty());
		pdxWriter.writeDouble("leaves",this.getLeaves());
		pdxWriter.writeString("msgType",this.getMsgType());
		pdxWriter.writeString("orderId",this.getOrderId());
		pdxWriter.writeDouble("orderQty",this.getOrderQty());
		pdxWriter.writeString("orderSource",this.getOrderSource());
		pdxWriter.writeString("orderStatus",this.getOrderStatus());
		pdxWriter.writeString("orderString",this.getOrderString());
		pdxWriter.writeString("parentId",this.getParentId());
		pdxWriter.writeString("rootId",this.getRootId());
		pdxWriter.writeString("tsReceived",this.getTsReceived());
		pdxWriter.writeString("tsSent",this.getTsSent());
		
	}
}
