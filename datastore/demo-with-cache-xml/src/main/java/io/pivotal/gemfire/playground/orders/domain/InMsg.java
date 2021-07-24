package io.pivotal.gemfire.playground.orders.domain;

import org.apache.geode.pdx.PdxReader;
import org.apache.geode.pdx.PdxSerializable;
import org.apache.geode.pdx.PdxWriter;

public class InMsg implements PdxSerializable
{	
	public InMsg()
	{
	}
	public InMsg(String msgId)
	{
		this.msgId = msgId;
		
	}
	/**
	 * @return the destSessions
	 */
	public String getDestSession()
	{
		return destSession;
	}
	/**
	 * @return the itemFormat
	 */
	public String getItemFormat()
	{
		return itemFormat;
	}
	/**
	 * @return the itemType
	 */
	public String getItemType()
	{
		return itemType;
	}
	/**
	 * @return the msgId
	 */
	public String getMsgId()
	{
		return msgId;
	}
	/**
	 * @return the msgType
	 */
	public String getMsgType()
	{
		return msgType;
	}
	/**
	 * @return the srcSession
	 */
	public String getSrcSession()
	{
		return srcSession;
	}
	/**
	 * @return the timeStamp
	 */
	public String getTimeStamp()
	{
		return timeStamp;
	}
	/**
	 * @return the payload
	 */
	public FixMap getPayload()
	{
		return payload;
	}
	/**
	 * @param destSession the destSession to set
	 */
	public void setDestSession(String destSession)
	{
		this.destSession = destSession;
	}
	/**
	 * @param itemFormat the itemFormat to set
	 */
	public void setItemFormat(String itemFormat)
	{
		this.itemFormat = itemFormat;
	}
	/**
	 * @param itemType the itemType to set
	 */
	public void setItemType(String itemType)
	{
		this.itemType = itemType;
	}
	/**
	 * @param msgId the msgId to set
	 */
	public void setMsgId(String msgId)
	{
		this.msgId = msgId;
	}
	/**
	 * @param msgType the msgType to set
	 */
	public void setMsgType(String msgType)
	{
		this.msgType = msgType;
	}
	/**
	 * @param srcSession the srcSession to set
	 */
	public void setSrcSession(String srcSession)
	{
		this.srcSession = srcSession;
	}
	/**
	 * @param timeStamp the timeStamp to set
	 */
	public void setTimeStamp(String timeStamp)
	{
		this.timeStamp = timeStamp;
	}
	/**
	 * @param payload the payload to set
	 */
	public void setPayload(FixMap payload)
	{
		this.payload = payload;
	}
	
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((destSession == null) ? 0 : destSession.hashCode());
		result = prime * result + ((itemFormat == null) ? 0 : itemFormat.hashCode());
		result = prime * result + ((itemType == null) ? 0 : itemType.hashCode());
		result = prime * result + ((msgId == null) ? 0 : msgId.hashCode());
		result = prime * result + ((msgType == null) ? 0 : msgType.hashCode());
		result = prime * result + ((payload == null) ? 0 : payload.hashCode());
		result = prime * result + ((srcSession == null) ? 0 : srcSession.hashCode());
		result = prime * result + ((timeStamp == null) ? 0 : timeStamp.hashCode());
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
		InMsg other = (InMsg) obj;
		if (destSession == null)
		{
			if (other.destSession != null)
				return false;
		}
		else if (!destSession.equals(other.destSession))
			return false;
		if (itemFormat == null)
		{
			if (other.itemFormat != null)
				return false;
		}
		else if (!itemFormat.equals(other.itemFormat))
			return false;
		if (itemType == null)
		{
			if (other.itemType != null)
				return false;
		}
		else if (!itemType.equals(other.itemType))
			return false;
		if (msgId == null)
		{
			if (other.msgId != null)
				return false;
		}
		else if (!msgId.equals(other.msgId))
			return false;
		if (msgType == null)
		{
			if (other.msgType != null)
				return false;
		}
		else if (!msgType.equals(other.msgType))
			return false;
		if (payload == null)
		{
			if (other.payload != null)
				return false;
		}
		else if (!payload.equals(other.payload))
			return false;
		if (srcSession == null)
		{
			if (other.srcSession != null)
				return false;
		}
		else if (!srcSession.equals(other.srcSession))
			return false;
		if (timeStamp == null)
		{
			if (other.timeStamp != null)
				return false;
		}
		else if (!timeStamp.equals(other.timeStamp))
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
		builder.append("InMsg [destSession=").append(destSession).append(", itemFormat=").append(itemFormat)
		.append(", itemType=").append(itemType).append(", msgId=").append(msgId).append(", msgType=").append(msgType)
		.append(", srcSession=").append(srcSession).append(", timeStamp=").append(timeStamp).append(", payload=")
		.append(payload).append("]");
		return builder.toString();
	}



	private String destSession;
	private String itemFormat;
	private String itemType;
	private String msgId;
	private String msgType;
	private String srcSession;
	private String timeStamp;
	private FixMap payload;
	@Override
	public void fromData(PdxReader pdxReader)
	{
		
		this.setDestSession(pdxReader.readString("destSession"));
		this.setItemFormat(pdxReader.readString("itemFormat"));
		this.setItemType(pdxReader.readString("itemType"));
		this.setMsgId(pdxReader.readString("msgId"));
		this.setMsgType(pdxReader.readString("msgType"));
		String payload = pdxReader.readString("fixMap.payload");
		this.setPayload(new FixMap(payload));
		this.setSrcSession(pdxReader.readString("srcSession"));
		this.setTimeStamp(pdxReader.readString("timeStamp"));
		
	}
	@Override
	public void toData(PdxWriter pdxWriter)
	{
		pdxWriter.writeString("destSession",this.getDestSession());
		pdxWriter.writeString("itemFormat",this.getItemFormat());
		pdxWriter.writeString("itemType",this.getItemType());
		pdxWriter.writeString("msgId",this.getMsgId());
		pdxWriter.writeString("msgType",this.getMsgType());

		FixMap fixMap = this.getPayload();
		if(fixMap == null)
			pdxWriter.writeString("fixMap.payload","" );
		else
			pdxWriter.writeString("fixMap.payload","" );
		
		pdxWriter.writeString("srcSession",this.getSrcSession());
		pdxWriter.writeString("timeStamp",this.getTimeStamp());
		
	}
}
