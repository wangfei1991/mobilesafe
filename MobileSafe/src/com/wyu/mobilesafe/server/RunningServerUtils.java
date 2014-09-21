package com.wyu.mobilesafe.server;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;

public class RunningServerUtils {
	private RunningServerUtils(){}
	
	/*************************用于判断某个服务是否正在运行************************/
	public static boolean isRunningServer(Context context,String serverName)
	{
		ActivityManager manager = (ActivityManager) 
						context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningServiceInfo> runningServices = 
										   manager.getRunningServices(100);
		for(RunningServiceInfo info : runningServices)
		{
			String server = info.service.getClassName();
			if (server.equals(serverName)) {
				return true;
			}
		}
		return false;
	}
	/***********************************************************************/
}
