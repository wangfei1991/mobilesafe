package com.wyu.mobilesafe.customeui;

import java.util.zip.Inflater;

import com.wyu.mobilesafe.R;
import com.wyu.mobilesafe.utils.FontTools;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

/**
 * Ϊ��ʹ�������ĵ�item�����ܹ����µ�ʹ�ã��Զ���һ��RelativeLayout
 * 
 * @author wangfei
 *
 */
public class SettingItemView extends RelativeLayout {
	
	private static final  String TAG = "SettingItemView";
	
	private Switch switchCheck;
	private TextView textView;
	public SettingItemView(Context context, AttributeSet attrs, 
													int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
		Log.e(TAG,attrs.getAttributeValue(0));
		Log.e(TAG,attrs.getAttributeValue(1));
		Log.e(TAG,attrs.getAttributeValue(2));
		Log.e(TAG,attrs.getAttributeValue(3));
		String titleContent = attrs.getAttributeValue(
				"http://schemas.android.com/apk/res/com.wyu.mobilesafe", "item_title");
	}

	public SettingItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
		String titleContent = attrs.getAttributeValue(
				"http://schemas.android.com/apk/res/com.wyu.mobilesafe", "item_title");
		textView.setText(titleContent);
	}

	public SettingItemView(Context context) {
		super(context);
		initView(context);
	}

	/**
	 * ���ڳ�ʼ������
	 * @param context
	 */
	private void initView(Context context) {
	
		
		View view = View.inflate(context, R.layout.setting_item_view, this);
		 
		switchCheck = (Switch) view.findViewById(R.id.setting_item_switch);
		textView = (TextView) findViewById(R.id.setting_item_title);
		FontTools.setFont(context, textView);
	}

	/**
	 * ��ȡSwitch��״̬
	 * @return
	 */
	public boolean isChecked()
	{
		return switchCheck.isChecked();
	}
	
	/**
	 * ����Switch��״̬
	 * @param check
	 */
	public void setChecked(boolean check)
	{
		switchCheck.setChecked(check);
	}
	
}
