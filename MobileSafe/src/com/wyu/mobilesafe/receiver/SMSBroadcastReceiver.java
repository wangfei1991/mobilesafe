package com.wyu.mobilesafe.receiver;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.wyu.mobilesafe.R;

public class SMSBroadcastReceiver extends BroadcastReceiver {

	private SmsManager smsManager ;
	private SharedPreferences preferences;
	@Override
	public void onReceive(Context context, Intent intent) {
		preferences = context.getSharedPreferences("config",
				 Context.MODE_PRIVATE);
		String savePhone = preferences.getString("phone", "");
		DevicePolicyManager mDPM = 
				(DevicePolicyManager)context.getSystemService(Context.DEVICE_POLICY_SERVICE);
		if (intent != null) {
			Object[] pdus = (Object[]) intent.getExtras().get("pdus");
			SmsMessage messages = SmsMessage.createFromPdu((byte[])pdus[0]);
	        String msg = messages.getMessageBody();
	        String address = messages.getOriginatingAddress();
	        if (msg.toLowerCase().contains("#*alarm*") && address.contains(savePhone)) {
				MediaPlayer player = MediaPlayer.create(context, R.raw.ylzs);
				player.setVolume(1.0f, 1.0f);
				player.start();
				abortBroadcast();
			}else if (msg.toLowerCase().contains("#*location*#") && address.contains(savePhone)) {
				LocationManager manager = (LocationManager) 
						context.getSystemService(Context.LOCATION_SERVICE);
				Criteria criteria = new Criteria();
				criteria.setAccuracy(Criteria.ACCURACY_FINE);			
				String provider = manager.getBestProvider(criteria, true);
				manager.requestLocationUpdates(provider, 0, 0, locationListener);
				String location = preferences.getString("location", "loading");
				SmsManager smsManager = SmsManager.getDefault();
				smsManager.sendTextMessage(savePhone, null, location, null, null);
				abortBroadcast();
			}else if (msg.toLowerCase().contains("#*lockscreen*#") && address.contains(savePhone)) {
				ComponentName   mDeviceAdminSample = new ComponentName(context,AdminReceiver.class);
				if (mDPM.isAdminActive(mDeviceAdminSample)) {
					mDPM.lockNow();			
				}	
				abortBroadcast();
			}else if(msg.toLowerCase().contains("#*wipedata*#")&& address.contains(savePhone))
			{
				ComponentName   mDeviceAdminSample = new ComponentName(context,AdminReceiver.class);
				if (mDPM.isAdminActive(mDeviceAdminSample)) {
					mDPM.wipeData(0);		
				}
				abortBroadcast();
			}
		}
	}
	private  LocationListener locationListener = new LocationListener() {
		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}
		
		/*
		 * 位置变化时调用
		 */
		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			double latitude = location.getLatitude();			//经度
			double longitude = location.getLongitude();			//纬度															
			double accurary = location.getAccuracy();			//精度
			Editor edit = preferences.edit();
			edit.putString("location", "j: "+latitude+"\n w: "+longitude+"\n a: "+accurary);
			edit.commit();
		}
	};
}
