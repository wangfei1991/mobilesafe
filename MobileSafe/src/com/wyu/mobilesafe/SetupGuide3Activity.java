package com.wyu.mobilesafe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class SetupGuide3Activity extends BaseSetupGuideActivity {

	private SharedPreferences preferences;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup_guide3);
		
		//listView.setAdapter(new SimpleAdapter(SetupGuide3Activity.this, data, resource, from, to)
		preferences = getSharedPreferences("config",Context.MODE_PRIVATE);
		
	}
	
	public void selectConta(View view)
	{
		Intent intent = new Intent(SetupGuide3Activity.this, SelectContactorActivity.class);
		startActivity(intent);
	}
	

	
	public void next(View view)
	{
		showNext();
	}
	public void pre(View view)
	{
		showPre();
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(R.anim.guide_pre_in_anim,R.anim.guide_pre_out_anim);
	}
	@Override
	public void showNext() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(SetupGuide3Activity.this, SetupGuide4Activity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.guide_next_in_anim, R.anim.guide_next_out_anim);
	}
	@Override
	public void showPre() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(SetupGuide3Activity.this, SetupGuide2Activity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.guide_pre_in_anim,R.anim.guide_pre_out_anim);
	}
}
