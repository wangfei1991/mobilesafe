package com.wyu.mobilesafe.server;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.wyu.mobilesafe.customeui.MyToast;
import com.wyu.mobilesafe.dao.AddressQueryUtils;

public class AddressListenerServer extends Service {

	protected static final String TAG = "AddressListenerServer";
	private TelephonyManager manager;
	private OutCallReceiver receiver;
	private MyToast comingToast;
	private MyToast outToast;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		
		manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		manager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
		receiver = new OutCallReceiver();
		IntentFilter filter = new IntentFilter(
				"android.intent.action.NEW_OUTGOING_CALL");
		registerReceiver(receiver, filter);
		
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
		unregisterReceiver(receiver);
		super.onDestroy();
	}
	
	class OutCallReceiver extends BroadcastReceiver {
		private String addressPhone;
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String phone = getResultData();
			if (!TextUtils.isEmpty(phone)) {
				addressPhone = AddressQueryUtils.queryAddress(phone);
				outToast = new MyToast(context, addressPhone);
				outToast.show();
			}
		}
	}

	private PhoneStateListener phoneStateListener = new PhoneStateListener() {
		public void onCallStateChanged(int state, String incomingNumber) {
			super.onCallStateChanged(state, incomingNumber);
			
			
			switch (state) {
			case TelephonyManager.CALL_STATE_RINGING:
				String queryAddress = AddressQueryUtils
						.queryAddress(incomingNumber);
				comingToast = new MyToast(getApplicationContext(), queryAddress);
				comingToast.show();
				break;
			case TelephonyManager.CALL_STATE_IDLE:
				if(comingToast != null){
					comingToast.dismiss();
				}
				comingToast = null;
				if (outToast != null) {
					outToast.dismiss();
				}
				outToast = null;
				break;
				
			default:
				break;
			}

		}
	};

	

}
