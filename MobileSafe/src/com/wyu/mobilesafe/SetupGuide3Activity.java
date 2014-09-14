package com.wyu.mobilesafe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SetupGuide3Activity extends BaseSetupGuideActivity{

	private SharedPreferences preferences;
	private EditText safePhoneTV;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup_guide3);
		
		/**********************����EditText�ñ���İ�ȫ����***************/
		safePhoneTV = (EditText) findViewById(R.id.safePhoneET);
		preferences = getSharedPreferences("config",
										  Context.MODE_PRIVATE);
		String safePhone = preferences.getString("phone", "").trim();
		safePhoneTV.setText(safePhone);
		safePhoneTV.setSelection(safePhone.length());
		/*************************************************************/
	}

	/*****************��onPause()���ύEditText�İ�ȫ����****************/
	protected void onPause() {
		super.onPause();
		
		Editor edit = preferences.edit();
		edit.putString("phone", 
						   safePhoneTV.getText().toString().trim());
		edit.commit();
	}
	/****************************************************************/	
	
	public void selectConta(View view)
	{
		Intent intent = new Intent(SetupGuide3Activity.this, 
								SelectContactorActivity.class);
		startActivityForResult(intent,0);
	}
	
	/***********************��ȡ���ص����ݲ�����EditText*****************/
	@Override
	protected void onActivityResult(int requestCode, 
								  int resultCode, Intent data) 
	{
		if (data != null) {
			String safePhone = (String) data.getExtras().get("phone");
			safePhone = safePhone.replace("-", "");		
			safePhoneTV.setText(safePhone);
			safePhoneTV.setSelection(safePhone.length());
		}
	}
	/***************************************************************/
	
	public void next(View view)
	{
		showNext();
	}
	public void pre(View view)
	{
		showPre();
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(R.anim.guide_pre_in_anim,
										R.anim.guide_pre_out_anim);
	}
	
	@Override
	public void showNext() {
		// TODO Auto-generated method stub
		String safePhone = safePhoneTV.getText().toString().trim();
		if(TextUtils.isEmpty(safePhone))								//��ȫ����Ϊ�գ���ʾ��
		{
			Toast.makeText(SetupGuide3Activity.this, "��ȫ����Ϊ��", 
										Toast.LENGTH_SHORT).show();
			return ;
		}
		Intent intent = new Intent(SetupGuide3Activity.this, 
										SetupGuide4Activity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.guide_next_in_anim, 
									   R.anim.guide_next_out_anim);
	}
	
	@Override
	public void showPre() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(SetupGuide3Activity.this, 
										SetupGuide2Activity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.guide_pre_in_anim,
										R.anim.guide_pre_out_anim);
	}
}
