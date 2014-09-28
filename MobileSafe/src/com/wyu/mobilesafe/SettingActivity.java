package com.wyu.mobilesafe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.gesture.Prediction;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.wyu.mobilesafe.customeui.SettingItemVIew2;
import com.wyu.mobilesafe.customeui.SettingItemView;
import com.wyu.mobilesafe.server.AddressListenerServer;
import com.wyu.mobilesafe.server.BlackNumberServer;
import com.wyu.mobilesafe.utils.FontTools;
import com.wyu.mobilesafe.utils.RunningServerUtils;

/**
 * 设置中心的activity
 * 
 * @author wangfei
 * 
 */
public class SettingActivity extends Activity {

	protected static final int SETFONTCODE = 1;
	private SettingItemView updateItemView;
	private SettingItemView blockSettingItem;
	private SettingItemView addressShow;
	private SettingItemVIew2 addressShowPat;
	private SettingItemVIew2 settingFont;
	
	private TextView settingTitle;
//	private TextView settedFonts_TV;
//	private TextView settingfont_TV;
	public static String update = "UPDATE";
	private SharedPreferences pref;
	private String[] fonts = { "stxingka", "default", "sans", "serif",
							   "monospace" };
	private String[] patterns = { "正常", "蓝色", "橙色", "灰色", "绿色" };
	private int saveFont;
	private int patternCheckedItem;
	private Intent addressIntent;
	private Intent blackIntent;

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case SETFONTCODE:
				Typeface typeface = 
					FontTools.setFont(SettingActivity.this, settingFont.getItemTitTextView());
				
				addressShowPat.getItemTitTextView().setTypeface(typeface);
				addressShowPat.getItemConTextView().setTypeface(typeface);				
				settingTitle.setTypeface(typeface);
				blockSettingItem.setFont(settingTitle.getTypeface());
				updateItemView.setFont(settingTitle.getTypeface());
				addressShow.setFont(settingTitle.getTypeface());
				int currentFont = pref.getInt("fonts", 0);
				settingFont.setItemContext(fonts[currentFont]);
				saveFont = currentFont;
				break;
			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);

		pref = getSharedPreferences("config", MODE_PRIVATE);
		saveFont = pref.getInt("fonts", 0);
		patternCheckedItem = pref.getInt("pattern", 0);

		updateItemView = (SettingItemView) findViewById(R.id.updateSettingItem);
		blockSettingItem = 
						 (SettingItemView) findViewById(R.id.blockSettingItem);
		addressShow    = (SettingItemView) findViewById(R.id.addressShow);
		addressShowPat = (SettingItemVIew2) findViewById(R.id.addressShowPattern);
		settingFont    = (SettingItemVIew2) findViewById(R.id.settingFont);
		settingTitle   = (TextView) findViewById(R.id.setting_tv_title);
		
		settingFont.setItemContext(fonts[saveFont]);
		
		
		/******************** 获取，设置，保存“是否更新”的的状态 **********************/
		boolean checked = pref.getBoolean(update, true);
		updateItemView.setChecked(checked);
		updateItemView.setOnClickListener(new OnClickListener() {
			SharedPreferences.Editor editor = pref.edit();

			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (updateItemView.isChecked()) {
					updateItemView.setChecked(false);
					editor.putBoolean(update, false);
				} else {
					updateItemView.setChecked(true);
					editor.putBoolean(update, true);
				}
				editor.commit();
			}
		});
		/**************************************************************/
		
		/********************设置是否拦截黑名单****************************/
		boolean blockRuning = RunningServerUtils.isRunningServer(
						SettingActivity.this, 
						"com.wyu.mobilesafe.server.BlackNumberServer");
		if (blockRuning) {
			blockSettingItem.setChecked(true);
		}else{
			blockSettingItem.setChecked(false);
		}
		blackIntent = new Intent(SettingActivity.this, 
				BlackNumberServer.class);
		blockSettingItem.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				
				if (blockSettingItem.isChecked()) {
					blockSettingItem.setChecked(false);
					stopService(blackIntent);
				}else {
					blockSettingItem.setChecked(true);
					startService(blackIntent);
				}
			}
		});
		/**************************************************************/
		
		/********************** 设置监听是否显示来电归属地显示 ****************/
		boolean isRunning = RunningServerUtils.isRunningServer(
				SettingActivity.this,
				"com.wyu.mobilesafe.server.AddressListenerServer");
		if (isRunning) {
			addressShow.setChecked(true);
		} else {
			addressShow.setChecked(false);
		}
		addressIntent = new Intent(SettingActivity.this,
				AddressListenerServer.class);
		addressShow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (addressShow.isChecked()) {
					addressShow.setChecked(false);
					stopService(addressIntent);
				} else {
					addressShow.setChecked(true);
					startService(addressIntent);
				}
			}
		});
		/****************************************************************/
		
		addressShowPat.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = 
							new AlertDialog.Builder(SettingActivity.this);	
				builder.setTitle("样式设置");
				builder.setSingleChoiceItems(patterns, patternCheckedItem, 
						new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								Editor edit = pref.edit();
								edit.putInt("pattern", which);
								edit.commit();
								addressShowPat.setItemContext(patterns[which]);
								dialog.dismiss();
							}
						});
				builder.setNegativeButton("取消", null);
				builder.show();
			}
		});
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		boolean isRunning = RunningServerUtils.isRunningServer(
				SettingActivity.this,
				"com.wyu.mobilesafe.server.AddressListenerServer");
		if (isRunning) {
			addressShow.setChecked(true);
		} else {
			addressShow.setChecked(false);
		}
		addressShowPat.setItemContext(patterns[patternCheckedItem]);
	}

	public void settingFont(View view) {
		final Editor edit = pref.edit();
		AlertDialog.Builder builder = new AlertDialog.Builder(
				SettingActivity.this);
		builder.setTitle("设置字体");

		builder.setSingleChoiceItems(fonts, saveFont,
				new AlertDialog.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						edit.putInt("fonts", which);
					}
				});
		builder.setPositiveButton("确定", new AlertDialog.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				edit.commit();
				Message msg = new Message();
				msg.what = SETFONTCODE;
				mHandler.sendMessage(msg);
			}
		});
		builder.setNegativeButton("取消", new AlertDialog.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				edit.clear();
			}
		});
		builder.setCancelable(false);
		builder.show();
	}

}
