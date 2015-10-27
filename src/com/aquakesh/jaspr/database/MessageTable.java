package com.aquakesh.jaspr.database;

public class MessageTable {
	public static final String TABLE_NAME = "Message";
	public static final String COLUMN_SOURCE_PHONE_NUMBER = "SourcePhoneNumber";
	public static final String COLUMN_RECEIVING_PHONE_NUMBER = "ReceiverPhoneNumber";
	public static final String COLUMN_CONTACT_NAME = "ContactName";
	public static final String COLUMN_MESSAGE = "Message";
	public static final String COLUMN_STATUS = "Status";
	public static final String COLUMN_TIMESTAMP = "TimeStamp";
	public static final String COLUMN_ID = "_id";
	
	public static final int STATUS_PENDING = 0;
	public static final int STATUS_UPLOADED = 1;
	public static final int STATUS_ORDERGENERATED = 2;
	public static final int STATUS_DELIVERED = 3;
	
	
}
