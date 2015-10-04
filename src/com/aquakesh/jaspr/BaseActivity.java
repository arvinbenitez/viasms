package com.aquakesh.jaspr;

import java.lang.reflect.Method;

import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

public class BaseActivity extends Activity  {
	
	private static final String TAG = BaseActivity.class.getSimpleName();
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.d(TAG, "onCreateOptinsMenu");
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d(TAG, "onMenuItemSelected");
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id){
		default:
			//TODO handle the clicks here
				break;
		}
		return true;
	}
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		Log.d(TAG, "onMenuOpened");
		Log.d(TAG, "Rendering icons");
		if (featureId == Window.FEATURE_ACTION_BAR && menu != null && menu.getClass().getSimpleName().contentEquals("MenuBuilder")){
			try{
				Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
				m.setAccessible(true);
				m.invoke(menu, true);
				
			} catch(NoSuchMethodException ex){
				Log.e(TAG, "onMenuOpened", ex);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return super.onMenuOpened(featureId, menu);
	}
}