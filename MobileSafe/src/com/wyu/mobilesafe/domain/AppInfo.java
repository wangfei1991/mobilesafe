package com.wyu.mobilesafe.domain;

import android.graphics.drawable.Drawable;


public class AppInfo {
	private Drawable icon;
	private CharSequence name;
	private String version;
	private boolean userApp;
	private String packageName;
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public boolean isUserApp() {
		return userApp;
	}
	public void setUserApp(boolean userApp) {
		this.userApp = userApp;
	}
	public Drawable getIcon() {
		return icon;
	}
	public void setIcon(Drawable icon) {
		this.icon = icon;
	}
	public CharSequence getName() {
		return name;
	}
	public void setName(CharSequence name2) {
		this.name = name2;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
}
