package com.aquakesh.jaspr;

import java.util.ArrayList;

import com.aquakesh.jaspr.model.Keyword;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class KeywordActivity extends BaseActivity {
	
	private static final String TAG = "KeywordActivity";
	ListView listKeyword;
	ArrayAdapter<Keyword> adapter;
	
 	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate");
		setContentView(R.layout.keywords);
		
		listKeyword = (ListView) findViewById(R.id.listKeywords);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, "onResume");
		
		ArrayList<Keyword> keywords = new ArrayList<Keyword>();
		getKeyWords(keywords);
		
		adapter = new ArrayAdapter<Keyword>(this, R.layout.keywordrow, R.id.keywordText, keywords);
		listKeyword.setAdapter(adapter);
	}

	//TODO This should come from the SharedPreferences
	private void getKeyWords(ArrayList<Keyword> keywords) {
		keywords.add(new Keyword("deliver"));
		keywords.add(new Keyword("slim"));
		keywords.add(new Keyword("round"));
	}
}
