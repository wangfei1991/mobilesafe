package com.wyu.mobilesafe.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

/**
 * ������������Ĺ�����
 * 
 * @author wangfei
 *
 */
public class FontTools {	
	
	private FontTools() {	}			//Ϊ�˲��ñ�����
	
	public static void setFont(Context context,TextView textView)
	{
		Typeface typeface = Typeface.createFromAsset(context.getAssets(), "STXINGKA.TTF");
		textView.setTypeface(typeface);
	}
}
