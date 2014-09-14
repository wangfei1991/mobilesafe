package com.wyu.mobilesafe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class LostFindActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SharedPreferences preferences = getSharedPreferences("lostfind", Context.MODE_PRIVATE);
		boolean setupGuide = preferences.getBoolean("lostfind", false);
		if (setupGuide) {
			setContentView(R.layout.activity_lost_find);
		}else {			
			Intent intent = new Intent(LostFindActivity.this, SetupGuide1Activity.class);
			startActivity(intent);
			finish();
		}
	}
	public void reSetup(View view)
	{
		Intent intent = new Intent(this, SetupGuide1Activity.class);
		startActivity(intent);
	}
}
