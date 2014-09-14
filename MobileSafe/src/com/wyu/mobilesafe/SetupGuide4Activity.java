package com.wyu.mobilesafe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;

public class SetupGuide4Activity extends BaseSetupGuideActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup_guide4);
		overridePendingTransition(R.anim.guide_next_in_anim, R.anim.guide_next_out_anim);
	}
	public void pre(View view)
	{
		showPre();
	}
	public void complete(View view)
	{
		showNext();
	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.guide_pre_in_anim,R.anim.guide_pre_out_anim);
	}
	@Override
	public void showNext() {
		// TODO Auto-generated method stub
		SharedPreferences preferences = getSharedPreferences("lostfind", Context.MODE_PRIVATE);
		Editor edit = preferences.edit();
		edit.putBoolean("lostfind", true);
		edit.commit();
		Intent intent = new Intent(SetupGuide4Activity.this, LostFindActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.guide_next_in_anim, R.anim.guide_next_out_anim);
	}
	@Override
	public void showPre() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(SetupGuide4Activity.this, SetupGuide3Activity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.guide_pre_in_anim,R.anim.guide_pre_out_anim);
	}

}
