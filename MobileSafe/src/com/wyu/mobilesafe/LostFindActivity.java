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
		
		if (setupGuide) {														//设置了向导就进入界面，否则进入设置向导
			setContentView(R.layout.activity_lost_find);
			
			protectImageView = (ImageView) findViewById(R.id.protectImageView);
			safePhoneTV = (TextView) findViewById(R.id.safePhoneTV);
			
			/********************从配置文件中获取安全号码*****************************/
			String safePhone = preferences.getString("phone", "");
			safePhoneTV.setText(safePhone);
			/*******************************************************************/
			
			/*******************************设置是否开启了防盗保护******************/
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
