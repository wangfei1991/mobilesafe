package com.wyu.mobilesafe.customeui;

import com.wyu.mobilesafe.R;
import com.wyu.mobilesafe.utils.FontTools;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SettingItemVIew2 extends RelativeLayout{
	
	private TextView settingItemTitle;
	private TextView settingItemContex;
	
	public SettingItemVIew2(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		initView(context);
		String title = attrs.getAttributeValue(
				"http://schemas.android.com/apk/res/com.wyu.mobilesafe", "item_tit");
		settingItemTitle.setText(title);
	}

	public SettingItemVIew2(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initView(context);
		String title = attrs.getAttributeValue(
				"http://schemas.android.com/apk/res/com.wyu.mobilesafe", "item_tit");
		settingItemTitle.setText(title);
	}

	public SettingItemVIew2(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView(context);		
	}
	public void setItemContext(CharSequence patterns)
	{
		settingItemContex.setText(patterns);
	}
	private void initView(Context context) {
		// TODO Auto-generated method stub
		View view = View.inflate(context, R.layout.setting_item_view2, this);
		settingItemTitle  = (TextView) view.findViewById(R.id.settingItemTitle);
		settingItemContex = (TextView) findViewById(R.id.settingItemContex);
		FontTools.setFont(context, settingItemContex);
		FontTools.setFont(context, settingItemTitle);
	}
	public TextView getItemTitTextView()
	{
		return settingItemTitle;
	}
	public TextView getItemConTextView()
	{
		return settingItemContex;
	}

}
