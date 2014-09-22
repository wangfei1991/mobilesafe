package com.wyu.mobilesafe.test;

import com.wyu.mobilesafe.db.BlackNumberOpenHelper;

import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

public class BlackNumberOpenHelperTest extends AndroidTestCase{
	public void create() throws Exception
	{
		BlackNumberOpenHelper helper = new BlackNumberOpenHelper(getContext());
		SQLiteDatabase readableDatabase = helper.getReadableDatabase();
		readableDatabase.close();
	}
}
