package com.wyu.mobilesafe.customeui;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.wyu.mobilesafe.R;

public class MyToast {
	private  WindowManager mWM;
	private  WindowManager.LayoutParams params;
	private  View v;
	private int[] backresource = {R.drawable.call_locate_blue,
								  R.drawable.call_locate_orange,
								  R.drawable.call_locate_gary,
								  R.drawable.call_locate_green};
	
	public MyToast(Context context, String contextString){
		
		SharedPreferences preferences = 
				context.getSharedPreferences("config", Context.MODE_PRIVATE);
		int which = preferences.getInt("pattern", 0);
		
		mWM = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		v = View.inflate(context, R.layout.transient_notification, null);
		if (which == 0) {	
		}
		else {
			v.setBackgroundResource(backresource[which-1]);			
		}

		TextView tv = (TextView) v.findViewById(R.id.phoneAddress);
		tv.setText(contextString);

		params        = new WindowManager.LayoutParams();
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		params.width  = WindowManager.LayoutParams.WRAP_CONTENT;
		params.flags  = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
						| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
						| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
		params.format = PixelFormat.TRANSLUCENT;
		params.type   = WindowManager.LayoutParams.TYPE_TOAST;
	}
	
	public  void show() {
		mWM.addView(v, params);
	}

	public void dismiss() {
		mWM.removeView(v);
	}

}