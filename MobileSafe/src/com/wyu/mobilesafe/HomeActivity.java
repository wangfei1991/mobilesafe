package com.wyu.mobilesafe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wyu.mobilesafe.utils.FontTools;
import com.wyu.mobilesafe.utils.MD5Utils;

/**
 * 应用的主界面
 * 
 * @author wangfei
 *
 */
public class HomeActivity extends Activity {
	
	/*****************************GridView显示的Icon和文字*******************************/
	private String[] strings = 
			{
				"手机防盗","通讯卫士","软件管理",
				"进程管理","流量管理","手机杀毒",
				"缓存清理","高级工具","设置中心"
			};
	private int[] icons = 
			{
				R.drawable.phone_antitheft,R.drawable.contacts,
				R.drawable.softmanager,R.drawable.process_manager,
				R.drawable.nettraffic,R.drawable.clean_malware,
				R.drawable.cache_clean,R.drawable.superutils,
				R.drawable.setting
			};
	/*******************************************************************************/
	
	private TextView home_title;
	private TextView ad_TextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		
		home_title = (TextView) findViewById(R.id.home_title);
		ad_TextView = (TextView) findViewById(R.id.ad_TV);
		GridView gridView = (GridView) findViewById(R.id.home_gridView);
		
		/********************设置GridView的Adapter和相关的点击事件***********************/
		MyListAdapter myListAdapter = new MyListAdapter();		
		gridView.setAdapter(myListAdapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = null;
				switch (position) {
				case 8:
					 intent = new Intent(HomeActivity.this, 
														SettingActivity.class);
					startActivity(intent);
					break;
				case 7:
					intent = new Intent(HomeActivity.this, 
							AdvancedUtilsActivity.class);
					startActivity(intent);

					break;
				case 0:
					showDialogToProtect();
					break;
				
				default:
					break;
				}
			}
			
		});
		
		/*************************************************************************/
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		FontTools.setFont(this, home_title);
		FontTools.setFont(HomeActivity.this, ad_TextView);
		Log.e("TAG", "HomeActivity onResume");
	}
	
	/**
	 * 用于手机防盗的密码设置或着进入
	 */
	protected void showDialogToProtect() {
		SharedPreferences preferences = 
						getSharedPreferences("config", Context.MODE_PRIVATE);
		String password = preferences.getString("password", "");
		if (password.isEmpty()) {										//密码空，就去设置
			showSetupDialog();
		}else {
			showEnterDialog();											//设置密码，就进入
		}
	}


	private void showEnterDialog() {
		
		/************************************获取************************************/
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		View view = View.inflate(HomeActivity.this, 
											R.layout.dialog_thief_enter, null);
		final EditText password_et = 
								(EditText) view.findViewById(R.id.passwrod_et);
		Button ok = (Button) view.findViewById(R.id.ok);
		Button cancel = (Button) view.findViewById(R.id.cancel);
		/***************************************************************************/
		
		builder.setView(view);
		final AlertDialog dialog = builder.show();
		
		/********************************设置监听事件***********************************/
		
		ok.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				String password = password_et.getText().toString().trim();
				String md5Password = MD5Utils.md5Password(password);
				SharedPreferences preferences = 
							getSharedPreferences("config", Context.MODE_PRIVATE);
				String savedPassword = preferences.getString("password", "");
				if (!savedPassword.equals(md5Password)) {
					Toast.makeText(HomeActivity.this, 
							"密码错误", Toast.LENGTH_SHORT).show();
				}else{
					Intent intent = new Intent(HomeActivity.this, LostFindActivity.class);
					startActivity(intent);
					dialog.dismiss();
				}
			}
		});
		cancel.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {		
				dialog.dismiss();
			}
		});		
		
		/***************************************************************************/
		
	}

	
	/**
	 *显示dialog用于设置密码
	 */
	private void showSetupDialog() {
		// TODO Auto-generated method stub
		/*****************************获取********************************************/
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		View view = View.inflate(HomeActivity.this, 
											R.layout.dialog_thief_setup, null);
		final EditText password_et = 
								(EditText) view.findViewById(R.id.passwrod_et);
		final EditText password_confirm = 
								(EditText) view.findViewById(R.id.password_confirm);
		Button ok = (Button) view.findViewById(R.id.ok);
		Button cancel = (Button) view.findViewById(R.id.cancel);
		/***************************************************************************/
		
		builder.setView(view);
		final AlertDialog dialog = builder.show();
		
		/********************************设置监听事件***********************************/
		
		ok.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				String passwordConfirm = 
									password_confirm.getText().toString().trim();
				String password = password_et.getText().toString().trim();
				if (TextUtils.isEmpty(password)||TextUtils.isEmpty(passwordConfirm)) 
				{
					Toast.makeText(HomeActivity.this, 
										"密码不能为空", Toast.LENGTH_SHORT).show();
					return ;
				}else if (password.equals(passwordConfirm)) {	
					//1.保存数据
					SharedPreferences preferences = 
							getSharedPreferences("config", Context.MODE_PRIVATE);
					Editor edit = preferences.edit();
					String md5Password = MD5Utils.md5Password(password);
					edit.putString("password",md5Password);
					edit.commit();
					//2.进入手机防盗的Activity
					Intent intent = new Intent(HomeActivity.this,LostFindActivity.class);
					startActivity(intent);
					//3.销毁dialog
					dialog.dismiss();
				}else {
					Toast.makeText(HomeActivity.this, 
							"密码不一致", Toast.LENGTH_SHORT).show();
				}
			}
		});
		cancel.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {		
				dialog.dismiss();
			}
		});		
		
		/***************************************************************************/
		
	}


	/*************************自定义的ListAdapter类************************************/
	private class MyListAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return strings.length;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View itemView;
			if (convertView != null) {
				itemView = convertView;
			}else {
				LayoutInflater inflater = getLayoutInflater();
				itemView = inflater.inflate(R.layout.home_gridview_item, null);				
			}
			
			/********************获取布局中相关的控件，并设置******************************/
			ImageView imageView = (ImageView) itemView.
											findViewById(R.id.home_item_image);
			TextView textView = (TextView) itemView.
											findViewById(R.id.home_item_textview);
			
			
			imageView.setImageResource(icons[position]);
			textView.setText(strings[position]);
			FontTools.setFont(HomeActivity.this, textView);
			/********************************************************************/
			
			return itemView;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
		
	}
	
}
