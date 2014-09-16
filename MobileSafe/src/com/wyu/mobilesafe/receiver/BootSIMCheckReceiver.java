package com.wyu.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

public class BootSIMCheckReceiver extends BroadcastReceiver {

	private static final String TAG = "BootSIMCheckReceiver";
	private TelephonyManager manager;
	private SharedPreferences preferences;

	@Override
	public void onReceive(Context context, Intent intent) {
		preferences = context.getSharedPreferences("config",
				Context.MODE_PRIVATE);
		String saveSim = preferences.getString("sim", "");
		if (saveSim.isEmpty()) {
			return;
		}
		manager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String sim = manager.getSimSerialNumber();
		if (!sim.equals(saveSim)) {
			String savePhone = preferences.getString("phone", "");
			SmsManager smsManager = SmsManager.getDefault();
			smsManager.sendTextMessage(savePhone, null, "SIM卡更改了", null, null);
			Log.e(TAG, "SIM卡更改了");
		}
		Log.e(TAG,"SIM:"+sim);
		Log.e(TAG, "SIM卡");
	}

}
