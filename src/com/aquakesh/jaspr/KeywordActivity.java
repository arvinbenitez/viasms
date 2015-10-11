package com.aquakesh.jaspr;

import java.util.ArrayList;
import java.util.List;

import com.aquakesh.jaspr.model.Keyword;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class KeywordActivity extends BaseActivity implements OnClickListener, OnCheckedChangeListener {

	private static final String TAG = "KeywordActivity";
	ListView listKeyword;
	ArrayAdapter<Keyword> adapter;
	ArrayList<Keyword> keywords;
	ImageButton addButton;
	ImageButton deleteButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate");
		setContentView(R.layout.keywords);

		listKeyword = (ListView) findViewById(R.id.listKeywords);
		addButton = (ImageButton) findViewById(R.id.buttonAddKeyword);
		addButton.setOnClickListener(this);

		deleteButton = (ImageButton) findViewById(R.id.deleteKeywordsButton);
		deleteButton.setOnClickListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, "onResume");

		keywords = new ArrayList<Keyword>();
		getKeyWords(keywords);

		adapter = new KeywordArrayAdapter(this, R.layout.keywordrow, R.id.keywordText, keywords);
		listKeyword.setAdapter(adapter);
	}

	public class KeywordArrayAdapter extends ArrayAdapter<Keyword> {

		Context context;

		public KeywordArrayAdapter(Context context, int resource, int textViewResourceId, List<Keyword> objects) {
			super(context, resource, textViewResourceId, objects);
			this.context = context;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = super.getView(position, convertView, parent);
			CheckBox checkbox = (CheckBox) view.findViewById(R.id.keywordCheck);
			checkbox.setOnCheckedChangeListener((OnCheckedChangeListener) context);
			return view;
		}
	}

	private static final String DefaultKeywords = "deliver,dlvr,slim,round";

	private void getKeyWords(ArrayList<Keyword> keywords) {
		Log.d(TAG, "getKeyWords");
		SharedPreferences prefs = this.getSharedPreferences(ApplicationPreferences, Context.MODE_PRIVATE);
		String keywordPreferences = prefs.getString(KeywordPreferences, DefaultKeywords);

		String[] keywordArray = keywordPreferences.split("\\,");
		for (String string : keywordArray) {
			keywords.add(new Keyword(string));
		}
	}

	private void saveKeywords() {
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.buttonAddKeyword:
			EditText text = (EditText) findViewById(R.id.textAddKeyword);
			if (text.getText().length() > 0) {
				if (AddText(text.getText().toString())) {
					text.setText("");
				}
			}
		case R.id.deleteKeywordsButton:
			for (int i = keywords.size()-1; i >=0; i--){
				if (keywords.get(i).selected){
					Log.d(TAG, "Deleting " + keywords.get(i).keyword);
					keywords.remove(i);
				}
			}
			updateDeletionView();
			adapter.notifyDataSetChanged();
			break;
		}
	}

	private boolean AddText(String word) {
		if (FindKeyword(word) == null) {
			Keyword keyword = new Keyword(word);
			keywords.add(keyword);
			adapter.notifyDataSetChanged();
			saveKeywords();
			return true;
		}
		return false;
	}

	private Keyword FindKeyword(String word) {
		for (int i = 0; i < keywords.size(); i++) {
			if (keywords.get(i).keyword.equalsIgnoreCase(word)) {
				return keywords.get(i);
			}
		}
		return null;
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// Find the keyword from the ArrayList and set the on/off value
		Log.d(TAG, "onCheckedChanged ");
		View rowView = (View) buttonView.getParent();
		TextView text = (TextView) rowView.findViewById(R.id.keywordText);

		Log.d(TAG, text.getText() + " was " + (isChecked ? " selected" : "unselected"));
		String word = text.getText().toString();
		
		Keyword keyword = FindKeyword(word);
		if (keyword != null) {
			keyword.selected = isChecked;
		}
		updateDeletionView();
	}

	private void updateDeletionView() {
		int selectionCount = 0;
		for (Keyword k : keywords) {
			selectionCount += k.selected ? 1: 0;
		}
		String s = "" + selectionCount + " keyword(s) selected";
		TextView display = (TextView) findViewById(R.id.deleteKeywordsText);
		display.setText(s);
		LinearLayout layout = (LinearLayout) findViewById(R.id.deleteKeywordsLayout);
		layout.setVisibility(selectionCount > 0 ? View.VISIBLE: View.GONE);;
	}
}
