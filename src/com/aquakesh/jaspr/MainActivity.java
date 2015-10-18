package com.aquakesh.jaspr;

import java.util.ArrayList;

import com.aquakesh.jaspr.model.Keyword;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends BaseActivity {

	ArrayList<Keyword> keywords;
	TextView mainTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		keywords = getKeyWords();
		mainTextView =  (TextView) findViewById(R.id.mainTextView);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		StringBuilder builder = new StringBuilder();
		builder.append("Keywords are:\n");
		for (Keyword k : keywords) {
			builder.append(k.keyword + "\n");
		}
		mainTextView.setText(builder.toString());
	}
}
