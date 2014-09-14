package com.wyu.mobilesafe.utils;

import android.content.Context;
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
	
	public static void setFont(Context context,TextView textView)
	{
		Typeface typeface = Typeface.createFromAsset(context.getAssets(), "STXINGKA.TTF");
		textView.setTypeface(typeface);
	}
}
