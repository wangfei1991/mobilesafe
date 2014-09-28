package com.wyu.mobilesafe.utils;

import android.os.StatFs;

public class getSpaceSize {
	public static long getSize(String path)
	{
		StatFs statFs = new StatFs(path);
		long availableBlocks = statFs.getAvailableBlocks();
		long blockSize = statFs.getBlockSize();
		return availableBlocks*blockSize;
	}
}
