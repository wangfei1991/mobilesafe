package com.wyu.mobilesafe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.wyu.mobilesafe.customeui.SettingItemView;

public class SetupGuide2Activity extends BaseSetupGuideActivity {

	private GestureDetector detector;
	private SettingItemView simSetup;
	private TelephonyManager teleManager;
	private SharedPreferences pref;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup_guide2);
		
		simSetup = (SettingItemView) findViewById(R.id.simSetup);
		
		teleManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		
		/************************获取sim以前的设置。并设置sim卡绑定状态*****************/
		pref = getSharedPreferences("config",Context.MODE_PRIVATE);
		String savedSimString = pref.getString("sim", "");
		if(TextUtils.isEmpty(savedSimString))
		{
			simSetup.setChecked(false);
		}else {
			simSetup.setChecked(true);
		}
		/*******************************************************************/
		
		/**********************监听sim绑定，并做出处理****************************/
		simSetup.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Editor edit = pref.edit();
				if (simSetup.isChecked()) {
					simSetup.setChecked(false);
					edit.putString("sim", "");
				}else {
					/******************当前状态是未绑定，则绑定********************/
					String sim="";
					int simState = teleManager.getSimState();					
					if (simState== TelephonyManager.SIM_STATE_READY ) {		//1.当有sim卡获取sim卡信息
						sim = teleManager.getSimSerialNumber();
					}
					if (TextUtils.isEmpty(sim)) {
						Toast.makeText(SetupGuide2Activity.this, "SIM没有安装",//2.当没有就弹出提示
												Toast.LENGTH_LONG).show();
					}
					simSetup.setChecked(true);
					edit.putString("sim", sim);								
					/*******************************************************/
				}
				edit.commit();
			}
		});
		/******************************************************************/
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(R.anim.guide_pre_in_anim,
												R.anim.guide_pre_out_anim);
	}
	
	public void next(View view)
	{
		showNext();
	}
	public void pre(View view)
	{
		showPre();
	}
	public void showNext() {
		if (TextUtils.isEmpty(pref.getString("sim", ""))) {				  //当未绑定就不能进行下一步操作
			Toast.makeText(SetupGuide2Activity.this, "SIM未绑定", 
										Toast.LENGTH_LONG).show();
			return ;
		}
		Intent intent = new Intent(SetupGuide2Activity.this, SetupGuide3Activity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.guide_next_in_anim, R.anim.guide_next_out_anim);
	}
	public  void showPre() {
		Intent intent = new Intent(SetupGuide2Activity.this, SetupGuide1Activity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.guide_pre_in_anim,R.anim.guide_pre_out_anim);
	}
	
	
}
