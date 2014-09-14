package com.wyu.mobilesafe;

import com.wyu.mobilesafe.customeui.SettingItemView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class SetupGuide4Activity extends BaseSetupGuideActivity {

	private SharedPreferences preferences;
	private SettingItemView settingItemView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup_guide4);
		
		settingItemView = 
					(SettingItemView) findViewById(R.id.settingProtecting);
		
		/***************************获取配置并设置****************************/
		preferences = getSharedPreferences("config", Context.MODE_PRIVATE);
		boolean protect = preferences.getBoolean("startProtection", false);
		settingItemView.setChecked(protect);
		/******************************************************************/		
		
		/***************************保存配置********************************/
		settingItemView.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Editor edit = preferences.edit();
				if (settingItemView.isChecked()) {
					settingItemView.setChecked(false);
					edit.putBoolean("startProtection", false);
				}else{
					settingItemView.setChecked(true);
					edit.putBoolean("startProtection", true);
				}
				edit.commit();
			}
		});
		/*****************************************************************/
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
		SharedPreferences preferences = getSharedPreferences("config", Context.MODE_PRIVATE);
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
