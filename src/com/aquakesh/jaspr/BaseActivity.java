package com.aquakesh.jaspr;

import java.lang.reflect.Method;
import java.util.ArrayList;

import com.aquakesh.jaspr.model.Keyword;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

public class BaseActivity extends Activity {

	private static final String TAG = BaseActivity.class.getSimpleName();
	public static final String ApplicationPreferences = "JasperPreferences" ;
	public static final String KeywordPreferences = "KeywordPreferences" ;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.d(TAG, "onCreateOptinsMenu");
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d(TAG, "onMenuItemSelected");
		int id = item.getItemId();
		switch (id) {
		case R.id.itemSettings:
			startActivity(new Intent(this, SettingsActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
			break;
		case R.id.itemKeyword:
			startActivity(new Intent(this, KeywordActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
			break;
		default:
			break;
		}
		return true;
	}

	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		Log.d(TAG, "onMenuOpened");
		Log.d(TAG, "Rendering icons");
		if (featureId == Window.FEATURE_ACTION_BAR && menu != null
				&& menu.getClass().getSimpleName().contentEquals("MenuBuilder")) {
			try {
				Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
				m.setAccessible(true);
				m.invoke(menu, true);

			} catch (NoSuchMethodException ex) {
				Log.e(TAG, "onMenuOpened", ex);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return super.onMenuOpened(featureId, menu);
	}
	
	private static final String DefaultKeywords = "deliver,dlvr,slim,round";
	protected ArrayList<Keyword> getKeyWords() {
		ArrayList<Keyword> keywords = new ArrayList<Keyword>();
		
		Log.d(TAG, "getKeyWords");
		SharedPreferences prefs = this.getSharedPreferences(ApplicationPreferences, Context.MODE_PRIVATE);
		String keywordPreferences = prefs.getString(KeywordPreferences, DefaultKeywords);

		String[] keywordArray = keywordPreferences.split("\\,");
		for (String string : keywordArray) {
			keywords.add(new Keyword(string));
		}
		return keywords;
	}
	
	protected void saveKeywords(ArrayList<Keyword> keywords) {
		SharedPreferences prefs = this.getSharedPreferences(ApplicationPreferences, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		String values = "";
		for (Keyword k : keywords) {
			values = values + (values.length() > 0 ? "," : "") + k.keyword;
		}
		Log.d(TAG, "Adding values to the shared preferences: " + values);
		editor.putString(KeywordPreferences, values);
		editor.commit();
	}
}
