package com.wyu.mobilesafe.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.wyu.mobilesafe.domain.AppInfo;

public class PackageUtils {
	public static List<AppInfo> getAllPackage(Context context)
	{
		PackageManager packageManager = context.getPackageManager();
		List<AppInfo> list = new ArrayList<AppInfo>();
		AppInfo appInfo ;
		List<PackageInfo> installedPackages = 
							packageManager.getInstalledPackages(0);
		for (PackageInfo packageInfo : installedPackages) {
			appInfo = new AppInfo();
			String versionName = packageInfo.versionName;
			CharSequence name = packageInfo.applicationInfo.loadLabel(packageManager);
			Drawable icon = packageInfo.applicationInfo.loadIcon(packageManager);
			String packageName = packageInfo.packageName;
			int flags = packageInfo.applicationInfo.flags;
			if ((flags & packageInfo.applicationInfo.FLAG_SYSTEM) !=0) {
				appInfo.setUserApp(false);
			}else {
				appInfo.setUserApp(true);
			}
			appInfo.setName(name);
			appInfo.setVersion(versionName);
			appInfo.setIcon(icon);
			appInfo.setPackageName(packageName);
			list.add(appInfo);
		}
		return list;
	}
}
