package com.wyu.mobilesafe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class LostFindActivity extends Activity {
	private TextView safePhoneTV;	
	private ImageView protectImageView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SharedPreferences preferences = getSharedPreferences("config",
												 Context.MODE_PRIVATE);
		boolean setupGuide = preferences.getBoolean("lostfind", false);
		
		if (setupGuide) {														//�������򵼾ͽ�����棬�������������
			setContentView(R.layout.activity_lost_find);
			
			protectImageView = (ImageView) findViewById(R.id.protectImageView);
			safePhoneTV = (TextView) findViewById(R.id.safePhoneTV);
			
			/********************�������ļ��л�ȡ��ȫ����*****************************/
			String safePhone = preferences.getString("phone", "");
			safePhoneTV.setText(safePhone);
			/*******************************************************************/
			
			/*******************************�����Ƿ����˷�������******************/
			boolean protect = preferences.getBoolean("startProtection",false);
			if (protect) {
				protectImageView.setImageResource(R.drawable.locked);
			}else{
				protectImageView.setImageResource(R.drawable.unlock);
			}
			/******************************************************************/
			
		}else {			
			Intent intent = new Intent(LostFindActivity.this, 
										   SetupGuide1Activity.class);
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
