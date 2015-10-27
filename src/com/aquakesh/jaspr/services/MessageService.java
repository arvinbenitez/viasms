package com.aquakesh.jaspr.services;

import java.util.ArrayList;
import java.util.Locale;

import com.aquakesh.jaspr.broadcastreceivers.MessageReceiver;
import com.aquakesh.jaspr.model.Keyword;
import com.aquakesh.jaspr.model.KeywordPreferencesHelper;
import com.aquakesh.jaspr.model.Message;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

public class MessageService extends Service implements OnSharedPreferenceChangeListener {

	private static final String TAG = "MessageService";
	private SmsReceiver receiver;
	ArrayList<Keyword> keywords;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreate");
		receiver = new SmsReceiver();
		KeywordPreferencesHelper.registerKeywordChangeListener(this);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "onStartCommand");
		keywords = KeywordPreferencesHelper.getKeyWords(this);
		IntentFilter intentFilter = new IntentFilter(MessageReceiver.NEW_MESSAGE_INTENT);
		registerReceiver(receiver, intentFilter);
		return START_STICKY;
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		Log.d(TAG, "keywords changed");
		keywords = KeywordPreferencesHelper.getKeyWords(this);
	}

	public class SmsReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent broadcastIntent) {
			Log.d(TAG, "SmsReceiver onReceive");
			Message sms = (Message) broadcastIntent.getSerializableExtra(MessageReceiver.NEW_MESSAGE_EXTRA_MESSAGE);
			if (sms != null) {
				String message = sms.getMessage();
				String contact = sms.getContactName();
				String sourcePhone = sms.getSenderPhoneNumber();
				String thisPhoneNumber = getThisPhoneNumber();
				Log.d(TAG, "SmsReceiver onReceive " + message);
				if (isOrder(message)) {
					Log.d(TAG, "SmsReceiver onReceive : Message is an order");
					IMessagePersistence persistence = new MessageDatabasePersistence(context);
					long id = persistence.add(thisPhoneNumber , sourcePhone, contact, message);
					Log.d(TAG, "SmsReceiver onReceive : Message saved to database with Id = " + id);
				} else {
					Log.d(TAG, "SmsReceiver onReceive : Message is NOT an order");
				}
			} else {
				Log.d(TAG, "SmsReceiver onReceive [NoMessage]");
			}
		}
		
		private boolean isOrder(String message) {
			Locale locale = Locale.getDefault();
			for (Keyword keyword : keywords) {
				if (message.toLowerCase(locale).contains(keyword.keyword.toLowerCase(locale))) {
					return true;
				}
			}
			return false;
		}
	}
	
	private String getThisPhoneNumber(){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		String thisPhoneNumber = prefs.getString("phoneNumber", "");
		Log.d(TAG, "This phone number is " +thisPhoneNumber);
		return thisPhoneNumber;
	}


}
