package com.wyu.mobilesafe;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.wyu.mobilesafe.customeui.SettingItemView;
import com.wyu.mobilesafe.utils.FontTools;

/**
 * �������ĵ�activity
 * 
 * @author wangfei
 * 
 */
public class SettingActivity extends Activity {

	private SettingItemView updateItemView;
	private TextView settingTitle;

	public static String update = "UPDATE";
	private SharedPreferences pref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);

		pref = getSharedPreferences("config", MODE_PRIVATE); // ���ڱ����Ƿ���µ�״̬

		updateItemView = (SettingItemView) findViewById(R.id.updateSettingItem);
		settingTitle = (TextView) findViewById(R.id.setting_tv_title);

		FontTools.setFont(this, settingTitle); // ��������

		/******************** ��ȡ�����ã����桰�Ƿ���¡��ĵ�״̬ **********************/
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

}
