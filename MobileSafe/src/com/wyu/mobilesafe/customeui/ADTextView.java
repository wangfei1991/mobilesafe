package com.wyu.mobilesafe.customeui;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.ViewDebug.ExportedProperty;
import android.widget.TextView;

/**
 * 用于传统的TextView默认的没有获取焦点，所以自定义一个控件使其获取焦点，从而实现相应功能
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
	 * 让textview处于焦点上，这样跑马灯就可以实现了
	 */
	@Override
	@ExportedProperty(category = "focus")
	public boolean isFocused() {
		// TODO Auto-generated method stub
		return true;
	}
	public void initView(Context context)
	{
		Typeface typeface = Typeface.createFromAsset(context.getAssets(), "STXINGKA.TTF");
		this.setTypeface(typeface);
	}
}
