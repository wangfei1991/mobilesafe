package com.wyu.mobilesafe.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AddressQueryUtils {
	private static String path = 
					   "/data/data/com.wyu.mobilesafe/files/address.db";
	private static SQLiteDatabase database;
	private AddressQueryUtils(){}
	static{
		
	}
	public static String queryAddress(String phone)
	{
		String address = phone;
		database = SQLiteDatabase.openDatabase(path,
				null, SQLiteDatabase.OPEN_READONLY);
		Cursor cursor = database.rawQuery("select location from data2 where id="
							+ "(select outkey from data1 where id= ?)", 
							new String[]{phone.substring(0, 7)});
		if (cursor.moveToFirst()) {
			address = cursor.getString(0);			
		}
		cursor.close();
		return address;
	}
}
