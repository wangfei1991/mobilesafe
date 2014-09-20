package com.wyu.mobilesafe.server;

import com.wyu.mobilesafe.dao.AddressQueryUtils;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class AddressListenerServer extends Service {

	private TelephonyManager manager;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		manager.listen(phoneStateListener, TelephonyManager.CALL_STATE_RINGING);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		manager.listen(phoneStateListener, 0);
		super.onDestroy();
	}

	private PhoneStateListener phoneStateListener = new PhoneStateListener() {
		public void onCallStateChanged(int state, String incomingNumber) {
			if (state == TelephonyManager.CALL_STATE_RINGING) {
				String queryAddress = AddressQueryUtils
						.queryAddress(incomingNumber);
				Toast.makeText(getBaseContext(), queryAddress, 1).show();
			}
		}
	};

}
