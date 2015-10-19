package com.aquakesh.jaspr.broadcastreceivers;

import com.aquakesh.jaspr.model.Message;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.PhoneLookup;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

public class MessageReceiver extends BroadcastReceiver {

	private static final String TAG = "MessageReceiver";
	public static final String NEW_MESSAGE_INTENT = "com.aquakesh.jaspr.NEW_MESSAGE";
	public static final String NEW_MESSAGE_EXTRA_MESSAGE = "com.aquakesh.jaspr.NEW_MESSAGE_EXTRA_MESSAGE";
	//private static final String NEW_MESSAGE_RECEIVER_PERMISSION = "com.aquakesh.jaspr.NEW_MESSAGE_RECEIVER_PERMISSION"; 
	
	final SmsManager sms = SmsManager.getDefault();
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "onReceive");
		final Bundle bundle = intent.getExtras();
		try{
			if (bundle != null){
				final Object[] pdusObject = (Object[]) bundle.get("pdus");
				for (int i = 0; i < pdusObject.length; i++) {
					@SuppressWarnings("deprecation")
					SmsMessage message = SmsMessage.createFromPdu((byte[]) pdusObject[i]);
					
					broadCast(message, context);
					
					Log.d(TAG, "onReceive completed");
				}
			} else{
				Log.d(TAG, "Bundle is null - no message found");
			}
			
		} catch (Exception e){
			Log.e(TAG, "Error reading the message",e);
		}
	}

	private void broadCast(SmsMessage message, Context context) {
		Log.d(TAG, "broadCast starting");
		String senderPhoneNumber = message.getOriginatingAddress() ;
		String messageBody = message.getMessageBody();
		String contactName = getContactName(context, senderPhoneNumber);
		Message jasprMessage = new Message(senderPhoneNumber, messageBody, contactName);
		Log.d(TAG, "broadcast message retrieved");
		Intent broadcastIntent = new Intent(MessageReceiver.NEW_MESSAGE_INTENT);
		broadcastIntent.putExtra(MessageReceiver.NEW_MESSAGE_EXTRA_MESSAGE, jasprMessage);
		context.sendBroadcast(broadcastIntent); //, MessageReceiver.NEW_MESSAGE_RECEIVER_PERMISSION);
		Log.d(TAG, "broadcast sent");
	}
	
	public String getContactName(Context context, String phoneNumber) {
		Log.d(TAG, "getContactName");
	    ContentResolver cr = context.getContentResolver();
	    Uri uri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI,Uri.encode(phoneNumber));
	    
	    Cursor cursor = cr.query(uri, new String[] { PhoneLookup.DISPLAY_NAME }, null, null, null);

	    if (cursor == null) {
	    	Log.d(TAG, "getContactName null cursor");
	        return null;
	    }
	    
	    String contactName = null;
	    if (cursor.moveToFirst()) {
	        contactName = cursor.getString(cursor.getColumnIndex(PhoneLookup.DISPLAY_NAME));
	        Log.d(TAG, "getContactName contact found: " + contactName);
	    }
	    if (cursor != null && !cursor.isClosed()) {
	        cursor.close();
	    }
	    return contactName;
	}
}
