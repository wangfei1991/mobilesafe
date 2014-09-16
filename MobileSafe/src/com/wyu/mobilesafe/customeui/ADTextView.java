package com.wyu.mobilesafe.customeui;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.ViewDebug.ExportedProperty;
import android.widget.Switch;
import android.widget.TextView;

/**
 * ���ڴ�ͳ��TextViewĬ�ϵ�û�л�ȡ���㣬�����Զ���һ���ؼ�ʹ���ȡ���㣬�Ӷ�ʵ����Ӧ����
 * @author wangfei
 *
 */
public class ADTextView extends TextView {

	public ADTextView(Context context, AttributeSet attrs, 
												int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
		// TODO Auto-generated constructor stub
	}

	public ADTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
		// TODO Auto-generated constructor stub
	}

	public ADTextView(Context context) {
		super(context);
		initView(context);
		// TODO Auto-generated constructor stub
	}
	/**
	 * ��textview���ڽ����ϣ���������ƾͿ���ʵ����
	 */
	@Override
	@ExportedProperty(category = "focus")
	public boolean isFocused() {
		// TODO Auto-generated method stub
		return true;
	}
	public void initView(Context context)
	{
		SharedPreferences preferences = 
				context.getSharedPreferences("config", Context.MODE_PRIVATE);
		int font = preferences.getInt("fonts", 0);
		Typeface typeface=null;
		switch (font) {
		case 0:
			typeface = Typeface.createFromAsset(context.getAssets(), "STXINGKA.TTF");
			break;
		case 1:
			typeface = Typeface.DEFAULT;
			break;
		case 2:
			typeface = Typeface.SANS_SERIF;
			break;
		case 3:
			typeface = Typeface.SERIF;
			break;
		case 4:
			typeface = Typeface.MONOSPACE;
			break;
		}
		this.setTypeface(typeface);
	}
}
