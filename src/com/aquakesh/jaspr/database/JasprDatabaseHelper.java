package com.aquakesh.jaspr.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class JasprDatabaseHelper extends SQLiteOpenHelper {

	private static final String TAG = "JasprDatabaseHelper";
	private static final String DB_NAME = "jaspr.db";
	private static final int DB_Version = 1;
	
	public JasprDatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_Version); 
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d(TAG, "onCreate");
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("create table " + MessageTable.TABLE_NAME);
			sql.append("(");
			sql.append(MessageTable.COLUMN_ID + " int primary key,");
			sql.append(MessageTable.COLUMN_SOURCE_PHONE_NUMBER + " text,");
			sql.append(MessageTable.COLUMN_RECEIVING_PHONE_NUMBER + " text,");
			sql.append(MessageTable.COLUMN_CONTACT_NAME + " text,");
			sql.append(MessageTable.COLUMN_MESSAGE + " text,");
			sql.append(MessageTable.COLUMN_STATUS + " int,");
			sql.append(MessageTable.COLUMN_TIMESTAMP + " int");
			sql.append(")");
			Log.d(TAG, "onCreated: " + sql.toString());
			db.execSQL(sql.toString());
		}catch (SQLException sex){
			Log.e(TAG, "onCreate Error", sex);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//Do nothing for now
		Log.d(TAG, String.format("OnUpgrade - OldVersion=%d, NewVersion=%d", oldVersion, newVersion));
	}

}
