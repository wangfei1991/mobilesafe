package com.wyu.mobilesafe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.wyu.mobilesafe.customeui.SettingItemView;
import com.wyu.mobilesafe.utils.FontTools;

/**
 * 设置中心的activity
 * 
 * @author wangfei
 * 
 */
public class SettingActivity extends Activity {

	protected static final int SETFONTCODE = 1;
	private SettingItemView updateItemView;
	private TextView settingTitle;
	private TextView settedFonts_TV;
	private TextView settingfont_TV;
	public static String update = "UPDATE";
	private SharedPreferences pref;
	private String[] fonts={"stxingka","default","sans","serif","monospace"};
	private int saveFont;
	
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case SETFONTCODE:
				FontTools.setFont(SettingActivity.this, settingTitle);
				FontTools.setFont(SettingActivity.this, settingfont_TV);
				updateItemView.setFont(settingTitle.getTypeface());
				int currentFont = pref.getInt("fonts", 0);
				settedFonts_TV.setText(fonts[currentFont]);
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
		
		updateItemView = (SettingItemView) findViewById(R.id.updateSettingItem);
		settingTitle = (TextView) findViewById(R.id.setting_tv_title);
		settedFonts_TV = (TextView) findViewById(R.id.settedFonts_TV);
		settingfont_TV = (TextView) findViewById(R.id.settingfont_TV);
		
		FontTools.setFont(this, settingTitle); // 设置字体
		FontTools.setFont(this, settingfont_TV); // 设置字体
		settedFonts_TV.setText(fonts[saveFont]);

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
	}
	public void settingFont(View view)
	{
		final Editor edit = pref.edit();
		AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
		builder.setTitle("设置字体");	
		
		builder.setSingleChoiceItems(fonts, saveFont, new AlertDialog.OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{				
				edit.putInt("fonts",which);
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
