package com.wyu.mobilesafe;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.wyu.mobilesafe.utils.FontTools;
import com.wyu.mobilesafe.utils.StringTools;

/**
 * splash界面的作用 1、用来展现产品的Logo 2、应用程序初始化的操作； 3、检查应用程序的版本；
 * 
 * @author wangfei
 * 
 */
public class SplashActivity extends Activity {

	/************************** 用于测试 ****************************************************/
	private static final String TAG = "MainActivity";
	private static final boolean DEBUG = true;
	/***********************************************************************************/

	/******************** 检测更新时的标示，用于标示测试结果 ****************************************/
	protected static final int ENTER_HOME = 1;
	protected static final int SHOW_DIALG_TO_UPDATE = 2;
	protected static final int URL_ERROR = 3;
	protected static final int NETWORK_ERROR = 4;
	protected static final int JSON_ERROR = 5;
	/***********************************************************************************/

	private TextView textView;
	private View splash_layout;

	/******************************** 用于保存更新信息 *****************************************/
	private String description;
	private String apkUrl;

	/***********************************************************************************/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		/************************************ 获取布局中的控件 ********************************/
		TextView splash_version = (TextView) findViewById(R.id.splash_version);
		TextView splash_tv = (TextView) findViewById(R.id.splash_tv);
		textView = (TextView) findViewById(R.id.progressing_tv);
		splash_layout = findViewById(R.id.splash_layout);
		/*******************************************************************************/

		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				copyDataBase();
			}
		}).start();
	

		splash_version.setText("V" + getVersionName()); // 设置版本信息
		FontTools.setFont(this, splash_tv); // 设置字体
		/*********************** 根据设置中心的"是否更新"的状态来进行判断是否更新检测 *******************/
		SharedPreferences pref = getSharedPreferences("config", MODE_PRIVATE);
		boolean update = pref.getBoolean(SettingActivity.update, true);
		if (update) {
			checkUpdate();
		} else {
			myHandler.postDelayed(new Runnable() {
				public void run() {
					// TODO Auto-generated method stub
					enterHome();
				}
			}, 1000);
		}
		/******************************************************************************/

	}

	private void copyDataBase() {
		InputStream inputStream = null;
		FileOutputStream outputStream = null;
		try {
			File file = new File(getFilesDir(), "address.db");
			if (!file.exists()) {

				inputStream = getAssets().open("address.db");

				outputStream = new FileOutputStream(file);
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, len);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if (outputStream != null) {
					outputStream.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 用于接收并处理checkUpdate()返回的信息
	 */
	private Handler myHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case ENTER_HOME:
				enterHome();
				break;

			case SHOW_DIALG_TO_UPDATE:
				showDialogToUpdate();
				break;

			case URL_ERROR:
				Toast.makeText(getApplicationContext(), "网络地址异常",
						Toast.LENGTH_SHORT).show();
				enterHome();
				break;

			case NETWORK_ERROR:
				Toast.makeText(getApplicationContext(), " 网络异常",
						Toast.LENGTH_SHORT).show();
				enterHome();
				break;

			case JSON_ERROR:
				enterHome(); // json异常，用户不懂就忽略
				break;

			default:
				break;
			}
		}
	};

	/**
	 * 有关展示更新对话框
	 */
	private void showDialogToUpdate() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("更新");
		builder.setMessage(description);

		/******************* 用于设置更新事件取消的处理方式 **************************************/
		// builder.setCancelable(false); //强制更新方式
		// 设置回调函数来监听事件
		builder.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				// TODO Auto-generated method stub
				enterHome();
			}
		});

		/****************************************************************************/
		builder.setPositiveButton("立即更新", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				boolean avalibale = Environment.MEDIA_MOUNTED
						.equals(Environment.getExternalStorageState());
				if (avalibale) {
					/****************************************************************/
					File extFile = Environment.getExternalStorageDirectory();
					File file = new File(extFile, "mobliesafe.apk");
					FinalHttp finalHttp = new FinalHttp();
					finalHttp.download(apkUrl, file.getAbsolutePath(),
							new AjaxCallBack<File>() {

								@Override
								public void onFailure(Throwable t, String strMsg) {
									// TODO Auto-generated method stub
									Toast.makeText(getApplicationContext(),
											"下载失败", Toast.LENGTH_SHORT).show();
								}

								@Override
								public void onLoading(long count, long current) {

									textView.setVisibility(View.VISIBLE);
									textView.setText((current * 100 / count)
											+ "");
								}

								@Override
								public void onSuccess(File t) {
									/******** 设置信息查看app下的packagerInstall的配置文件 *************/
									Intent intent = new Intent(
											"android.intent.action.VIEW");
									intent.addCategory("android.intent.category.DEFAULT");
									intent.setDataAndType(Uri.fromFile(t),
											"application/vnd.android.package-archive");
									startActivity(intent);
								}

							});
				}
			}

		});

		builder.setNegativeButton("下次再说", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				enterHome();
			}
		});
		builder.show();
	}

	/**
	 * 用于进入主界面
	 */
	private void enterHome() {
		// TODO Auto-generated method stub
		startActivity(new Intent(getApplicationContext(), HomeActivity.class));

		finish();
	}

	/**
	 * 用于获取版本信息
	 * 
	 * @return 版本号
	 */
	private String getVersionName() {
		PackageManager manager = getPackageManager();
		try {
			PackageInfo packageInfo = manager.getPackageInfo(getPackageName(),
					PackageManager.GET_ACTIVITIES);
			String version = packageInfo.versionName;
			return version;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 用于检测更新
	 */
	private void checkUpdate() {
		new Thread() {

			/***************************** 因为是耗时操作开启子线程 ******************************/
			HttpURLConnection connection = null;

			@Override
			public void run() {
				// TODO Auto-generated method stub
				long startTime = System.currentTimeMillis();
				Message msg = new Message();
				/**************************** 网络连接，检测更新 *****************************/
				try {

					URL url = new URL(
							"http://10.10.111.234/news/updateinfo.html");
					connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setReadTimeout(1000);
					connection.setConnectTimeout(1000);

					int code = connection.getResponseCode();
					if (code == 200) {
						InputStream inputStream = connection.getInputStream();
						String resultStr = StringTools
								.getStringFromStream(inputStream);
						if (resultStr != null) {
							// Log.i(TAG, resultStr);
							JSONObject jsonObject = new JSONObject(resultStr);
							String version = (String) jsonObject.get("version");
							description = (String) jsonObject
									.get("description");
							apkUrl = (String) jsonObject.get("apkurl");
							if (!version.equals(getVersionName())) {
								msg.what = SHOW_DIALG_TO_UPDATE;
							} else {
								msg.what = ENTER_HOME;
							}
						} else {
							msg.what = URL_ERROR;
						}
					}
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					msg.what = URL_ERROR;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					msg.what = NETWORK_ERROR;
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					msg.what = JSON_ERROR;
				} finally {
					if (connection != null) {
						connection.disconnect();
					}
					myHandler.sendMessage(msg);
					long endTime = System.currentTimeMillis();
					long durTime = endTime - startTime;
					if (durTime < 4000) {
						SystemClock.sleep(durTime);
					}

				}
				/********************************************************************/
			}
		}.start();
		/****************************************************************************/
	}
}
