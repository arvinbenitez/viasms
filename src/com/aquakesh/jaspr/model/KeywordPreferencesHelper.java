package com.aquakesh.jaspr.model;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.util.Log;

public class KeywordPreferencesHelper {
	
	private static final String DefaultKeywords = "deliver,dlvr,slim,round";
	private static final String TAG = "KeywordPreferencesHelper";
	private static final String ApplicationPreferences = "JasperPreferences" ;
	private static final String KeywordPreferences = "KeywordPreferences" ;
	
	public static ArrayList<Keyword> getKeyWords(Context context) {
		ArrayList<Keyword> keywords = new ArrayList<Keyword>();
		
		Log.d(TAG, "getKeyWords");
		SharedPreferences prefs = context.getSharedPreferences(ApplicationPreferences, Context.MODE_PRIVATE);
		String keywordPreferences = prefs.getString(KeywordPreferences, DefaultKeywords);

		String[] keywordArray = keywordPreferences.split("\\,");
		for (String string : keywordArray) {
			keywords.add(new Keyword(string));
		}
		return keywords;
	}
	
	public static void saveKeywords(Context context, ArrayList<Keyword> keywords) {
		SharedPreferences prefs = context.getSharedPreferences(ApplicationPreferences, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		String values = "";
		for (Keyword k : keywords) {
			values = values + (values.length() > 0 ? "," : "") + k.keyword;
		}
		Log.d(TAG, "Adding values to the shared preferences: " + values);
		editor.putString(KeywordPreferences, values);
		editor.commit();
	}
	
	public static void registerKeywordChangeListener(OnSharedPreferenceChangeListener listener){
		SharedPreferences prefs = ((Context) listener).getSharedPreferences(ApplicationPreferences, Context.MODE_PRIVATE);
		prefs.registerOnSharedPreferenceChangeListener(listener);
	}
}
