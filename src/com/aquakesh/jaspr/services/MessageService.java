package com.aquakesh.jaspr.services;

import java.util.ArrayList;

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
import android.util.Log;

public class MessageService extends Service implements OnSharedPreferenceChangeListener{

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
			if (sms != null){
				Log.d(TAG, "SmsReceiver onReceive " + sms.getMessage());
				//TODO: process here if it's an order then save or call API
			} else {
				Log.d(TAG, "SmsReceiver onReceive [NoMessage]");
			}
		}
	}

}
