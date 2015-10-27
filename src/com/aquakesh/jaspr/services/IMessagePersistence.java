package com.aquakesh.jaspr.services;

public interface IMessagePersistence {
	long add(String recevingPhoneNumber, String sourcePhone,  String contactName, String message);
	void delete(int messageId);
	String get(int messageId);
	int getStatus(int messageId);
}
