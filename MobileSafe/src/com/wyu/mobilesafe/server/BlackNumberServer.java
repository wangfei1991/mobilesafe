package com.wyu.mobilesafe.server;

import java.lang.reflect.Method;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.internal.telephony.ITelephony;
import com.wyu.mobilesafe.dao.BlackNumberDao;

public class BlackNumberServer extends Service{

	private BlackNumberDao dao;
	private BlockSMSPhoneReceiver receiver;
	private TelephonyManager manager;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		
		dao = new BlackNumberDao(this);
		receiver = new BlockSMSPhoneReceiver();
		IntentFilter filter = 
				new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
		filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);		
		registerReceiver(receiver, filter);
		
		manager = 
			 (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		manager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
		
		super.onCreate();
		
	}
	
	class BlockSMSPhoneReceiver extends BroadcastReceiver
	{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			
			 Object[] objects = (Object[]) intent.getExtras().get("pdus");
			 
			 for (Object object : objects) 
			 {
				 SmsMessage message = 
						 		SmsMessage.createFromPdu((byte[]) object);
				 String address = message.getOriginatingAddress();
				 String mode = dao.findMode(address);
				 if("1".equals(mode)||"3".equals(mode))
				 {
					 abortBroadcast();
				 }
			 }
		}
		
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(receiver);
		manager.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);
	}
	
	private PhoneStateListener phoneStateListener = new PhoneStateListener(){

		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			// TODO Auto-generated method stub
			Log.e("h即将中断电话", "nihaonihao");
			switch (state) {
			case TelephonyManager.CALL_STATE_RINGING:
				String mode = dao.findMode(incomingNumber);
				if ("2".equals(mode) || "3".equals(mode)) {
					//挂断电话
					Log.e("中断中断电话", "hhahhahfh");
					try {
						Class clazz = BlackNumberServer.class
									.getClassLoader()
									.loadClass("android.os.ServiceManager");
						Method method = 
								clazz.getMethod("getService", String.class);
						IBinder iBinder = (IBinder) method.invoke(null, TELEPHONY_SERVICE);
						ITelephony.Stub.asInterface(iBinder).endCall();
					

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}else{
					
				}
				break;

			default:
				break;
			}	
		}
	};
	
}
