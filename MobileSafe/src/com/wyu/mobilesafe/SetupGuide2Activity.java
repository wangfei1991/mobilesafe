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
		
		/************************��ȡsim��ǰ�����á�������sim����״̬*****************/
		pref = getSharedPreferences("config",Context.MODE_PRIVATE);
		String savedSimString = pref.getString("sim", "");
		if(TextUtils.isEmpty(savedSimString))
		{
			simSetup.setChecked(false);
		}else {
			simSetup.setChecked(true);
		}
		/*******************************************************************/
		
		/**********************����sim�󶨣�����������****************************/
		simSetup.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Editor edit = pref.edit();
				if (simSetup.isChecked()) {
					simSetup.setChecked(false);
					edit.putString("sim", "");
				}else {
					/******************��ǰ״̬��δ�󶨣����********************/
					String sim="";
					int simState = teleManager.getSimState();					
					if (simState== TelephonyManager.SIM_STATE_READY ) {		//1.����sim����ȡsim����Ϣ
						sim = teleManager.getSimSerialNumber();
					}
					if (TextUtils.isEmpty(sim)) {
						Toast.makeText(SetupGuide2Activity.this, "SIMû�а�װ",//2.��û�о͵�����ʾ
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
		if (TextUtils.isEmpty(pref.getString("sim", ""))) {				  //��δ�󶨾Ͳ��ܽ�����һ������
			Toast.makeText(SetupGuide2Activity.this, "SIMδ��", 
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
