package com.wyu.mobilesafe.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

/**
 * 用于设置字体的工具类
 * 
 * @author wangfei
 *
 */
public class FontTools {	
	
	private FontTools() {	}			//为了不让被创建
	
	public static Typeface setFont(Context context,TextView textView)
	{
		SharedPreferences preferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		int font = preferences.getInt("fonts", 0);
		Typeface typeface = null;
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
		textView.setTypeface(typeface);
		return typeface;
	}
}
