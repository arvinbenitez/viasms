package com.aquakesh.jaspr.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Message implements Serializable {

	private String senderPhoneNumber;
	private String message;
	private String contactName;
	
	public Message(String senderPhoneNumber, String message, String contactName){
		this.senderPhoneNumber = senderPhoneNumber;
		this.message = message;
		this.contactName = contactName;
	}

	public String getSenderPhoneNumber() {
		return senderPhoneNumber;
	}

	public void setSenderPhoneNumber(String senderPhoneNumber) {
		this.senderPhoneNumber = senderPhoneNumber;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
}
