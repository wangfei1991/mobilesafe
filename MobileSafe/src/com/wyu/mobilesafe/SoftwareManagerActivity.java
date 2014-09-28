package com.wyu.mobilesafe;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.Formatter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wyu.mobilesafe.domain.AppInfo;
import com.wyu.mobilesafe.utils.PackageUtils;
import com.wyu.mobilesafe.utils.getSpaceSize;

public class SoftwareManagerActivity extends Activity {

	// Result code identifiers
	public static final int REQUEST_UNINSTALL = 1;
	private MyAdapter myAdapter;
	private ListView software_Listview;
	private TextView romSpace_TV;
	private TextView externalSpace_TV;
	private List<AppInfo> allPackage;
	private List<AppInfo> userApp;
	private ProgressBar progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_software);

		software_Listview = (ListView) findViewById(R.id.software_Listview);
		romSpace_TV = (TextView) findViewById(R.id.romSpace_TV);
		externalSpace_TV = (TextView) findViewById(R.id.externalSpace_TV);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		

		long external = getSpaceSize.getSize(Environment
				.getExternalStorageDirectory().getAbsolutePath());
		long internal = getSpaceSize.getSize(Environment.getDataDirectory()
				.getAbsolutePath());
		romSpace_TV.setText("内部可用空间："
				+ Formatter.formatShortFileSize(SoftwareManagerActivity.this,
						external));
		externalSpace_TV.setText("外部可用空间"
				+ Formatter.formatShortFileSize(SoftwareManagerActivity.this,
						internal));
		progressBar.setVisibility(View.VISIBLE);
		fillData();

		/*****************************启动应用*******************************/
		software_Listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				PackageManager manager = getPackageManager();
				Intent intent = manager.getLaunchIntentForPackage(userApp
						.get(position).getPackageName());
				if (intent != null) {
					startActivity(intent);
				}
			}
		});
		/******************************************************************/
		
		/*****************************应用分享*******************************/
		software_Listview.setOnItemLongClickListener(new OnItemLongClickListener() {
			
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent sendIntent = new Intent();  
				sendIntent.setAction(Intent.ACTION_SEND);  
				sendIntent.putExtra(Intent.EXTRA_TEXT, "很不错");  
				sendIntent.setType("text/plain");  
				startActivity(sendIntent); 
				return true;
			}
		});
		/*****************************************************************/

	}

	

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return userApp.size();
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			View view;
			AppInfoHolder holder;
			if (convertView != null) {
				view = convertView;
				holder = (AppInfoHolder) view.getTag();
			} else {
				view = View.inflate(SoftwareManagerActivity.this,
						R.layout.apps_item_view, null);
				holder = new AppInfoHolder();
				holder.app_icon = (ImageView) view.findViewById(R.id.app_icon);
				holder.app_name = (TextView) view.findViewById(R.id.app_name);
				holder.app_version = (TextView) view
						.findViewById(R.id.app_version);
				holder.deleteApp_IV = (ImageView) view
						.findViewById(R.id.deleteApp_IV);
				view.setTag(holder);
			}

			final AppInfo appInfo= userApp.get(position);
			final String app_name = (String) appInfo.getName();
			final String app_version = appInfo.getVersion();
			final String packageName = userApp.get(position).getPackageName();
			final Drawable icon = appInfo.getIcon();
			holder.app_name.setText(app_name);
			holder.app_version.setText(app_version);
			holder.app_icon.setImageDrawable(icon);
			/*****************************卸载应用*******************************/
			holder.deleteApp_IV.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					/*
					 * <action android:name="android.intent.action.DELETE" />
					 * <action
					 * android:name="android.intent.action.UNINSTALL_PACKAGE" />
					 * <category android:name="android.intent.category.DEFAULT"
					 * /> <data android:scheme="package" />
					 */
					Uri packageURI = Uri.parse("package:" + packageName);
					Intent uninstallIntent = 
							new Intent(Intent.ACTION_UNINSTALL_PACKAGE,
							packageURI);
					startActivityForResult(uninstallIntent, REQUEST_UNINSTALL);
				}
			});
			/*****************************************************************/
			return view;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		class AppInfoHolder {
			ImageView app_icon;
			TextView app_name;
			TextView app_version;
			ImageView deleteApp_IV;
		}
	}

	@Override
	public void onActivityResult(int requestCode, 
								int resultCode, Intent data) {

		
		if ((requestCode == REQUEST_UNINSTALL)) {	
			fillData();
		}
	}
	/*****************************用于加载数据*******************************/
	private void fillData() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				allPackage = PackageUtils
						.getAllPackage(SoftwareManagerActivity.this);
				userApp = new ArrayList<AppInfo>();
				for (AppInfo appInfo : allPackage) {
					if (appInfo.isUserApp()) {
						userApp.add(appInfo);
					}
				}
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						
						if (myAdapter != null) {
							myAdapter.notifyDataSetChanged();
							return ;
						}
						myAdapter = new MyAdapter();
						software_Listview.setAdapter(myAdapter);
						progressBar.setVisibility(View.GONE);
					}
				});

			}
		}).start();
	}

}
