package com.wyu.mobilesafe;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;

/**
 * 这个类是处理手势，抽取出来可以减少代码重复
 * @author wangfei
 *
 */
public abstract class BaseSetupGuideActivity extends Activity {
	
	private GestureDetector detector;
	
	/****************************子类实现方法来处理滑动的事件**************************/
	public abstract void showNext();
	public abstract void showPre();
	/************************************************************************/
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		detector = new GestureDetector(this, new SimpleOnGestureListener(){

			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float velocityX, float velocityY) {
				if (Math.abs((e1.getRawY() - e2.getRawY())) > 200) {
					return true;
				}
				if (e1.getRawX() - e2.getRawX() >100) {
					showNext();
					return true;
				}
				if (e2.getRawX() - e1.getRawX() > 100) {
					showPre();
					return true;
				}
				return super.onFling(e1, e2, velocityX, velocityY);
			}
			
		});
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		detector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}
	
}
