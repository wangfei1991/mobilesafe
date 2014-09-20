package com.wyu.mobilesafe;

import com.wyu.mobilesafe.dao.AddressQueryUtils;
import com.wyu.mobilesafe.utils.FontTools;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AdvancedUtilsActivity extends Activity {

	private View queryContainer;
	private TextView showAddress_TV;
	private TextView queryAddress;
	private TextView advancedUtils;
	private Button queryButton;
	private EditText requestPhone_ET;
	private int count = 0;
	private Vibrator vibrator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_advanced_utils);
		advancedUtils = (TextView) findViewById(R.id.advancedUtils_TV);
		queryContainer = findViewById(R.id.queryContainer);
		showAddress_TV = (TextView) findViewById(R.id.showAddress_TV);
		queryAddress = (TextView) findViewById(R.id.queryAddress_TV);
		requestPhone_ET = (EditText) findViewById(R.id.requestPhone_ET);
		queryButton = (Button) findViewById(R.id.query_BT);
		vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		
		requestPhone_ET.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start,
											int before,	int count) {
				final String phone = s.toString();
				if (s != null && s.length() >= 7) {
					new Thread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							String queryAddress = AddressQueryUtils
									.queryAddress(phone);
							Message message = new Message();
							message.obj = queryAddress;
							myHandler.sendMessage(message);
						}
					}).start();
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, 
												int count, int after){
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		FontTools.setFont(AdvancedUtilsActivity.this, showAddress_TV);
		FontTools.setFont(AdvancedUtilsActivity.this, queryAddress);
		FontTools.setFont(AdvancedUtilsActivity.this, advancedUtils);
		FontTools.setFont(AdvancedUtilsActivity.this, queryButton);
	}

	/************************* 判断是否隐藏查询窗口 **************************/
	public void queryAddress(View view) {
		showAddress_TV.setVisibility(View.GONE);
		if ((count % 2) == 0) {
			queryContainer.setVisibility(View.VISIBLE);
			count++;
		} else {
			queryContainer.setVisibility(View.GONE);
			count++;
		}
	}

	/********************************************************************/

	/************************** 查询号码归属地 ****************************/
	public void query(View view) {
		// Toast.makeText(AdvancedUtilsActivity.this, "hahaha",
		// Toast.LENGTH_LONG).show();
		final String phone = requestPhone_ET.getText().toString();
		if (TextUtils.isEmpty(phone)) {
			Animation loadAnimation = AnimationUtils.loadAnimation(
								AdvancedUtilsActivity.this, R.anim.shake);
			requestPhone_ET.startAnimation(loadAnimation);
			long[] pattern = {0,1000,2000,1000};
			vibrator.vibrate(pattern, -1);
			return ;
		} else if (phone.matches("^1[3458]\\d{9}$")) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					String queryAddress = 
							AddressQueryUtils.queryAddress(phone);
					Message message = new Message();
					message.obj = queryAddress;
					myHandler.sendMessage(message);
				}
			}).start();
			return;
		}
		Toast.makeText(AdvancedUtilsActivity.this, 
										"手机号码格式不符合", 0).show();
	}

	/******************************************************************/

	/****************** 数据库操作是耗时操作，所以异步获取address *************/
	private Handler myHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String queryAddress = (String) msg.obj;
			showAddress_TV.setVisibility(View.VISIBLE);
			showAddress_TV.setText(queryAddress);
		}
	};
	/******************************************************************/
}
