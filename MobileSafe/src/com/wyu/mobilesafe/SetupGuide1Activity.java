package com.wyu.mobilesafe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Ïòµ¼¼ò½é
 * @author wangfei
 *
 */

public class SetupGuide1Activity extends BaseSetupGuideActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup_guide1);
		
	}
	public void next(View view)
	{
		showNext();
	}
	@Override
	public void showNext() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(SetupGuide1Activity.this, SetupGuide2Activity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.guide_next_in_anim, R.anim.guide_next_out_anim);
	}
	@Override
	public void showPre() {
		// TODO Auto-generated method stub
		
	}
}
