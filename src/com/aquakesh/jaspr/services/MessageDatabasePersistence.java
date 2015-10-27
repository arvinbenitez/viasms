package com.aquakesh.jaspr.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.aquakesh.jaspr.database.JasprDatabaseHelper;
import com.aquakesh.jaspr.database.MessageTable;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MessageDatabasePersistence implements IMessagePersistence {

	private static final String TAG = "MessageDatabasePersistence";

	JasprDatabaseHelper dbHelper;

	public MessageDatabasePersistence(Context context) {
		dbHelper = new JasprDatabaseHelper(context);
	}

	@Override
	public void delete(int messageId) {
		// TODO Auto-generated method stub
	}

	@Override
	public String get(int messageId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getStatus(int messageId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long add(String recevingPhoneNumber, String sourcePhone, String contactName, String message) {
		long messageId = 0;
		Log.d(TAG, "adding message");
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		try {
			ContentValues values = new ContentValues();
			// assign the value
			values.put(MessageTable.COLUMN_SOURCE_PHONE_NUMBER, sourcePhone);
			values.put(MessageTable.COLUMN_RECEIVING_PHONE_NUMBER, recevingPhoneNumber);
			values.put(MessageTable.COLUMN_CONTACT_NAME, contactName);
			values.put(MessageTable.COLUMN_MESSAGE, message);
			values.put(MessageTable.COLUMN_STATUS, MessageTable.STATUS_PENDING);
			values.put(MessageTable.COLUMN_TIMESTAMP,
					new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()));
			messageId = db.insertWithOnConflict(MessageTable.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);
			Log.d(TAG, "Success!!! MessageId=" + messageId);
		} finally {
			db.close();
		}
		return messageId;
	}

}
