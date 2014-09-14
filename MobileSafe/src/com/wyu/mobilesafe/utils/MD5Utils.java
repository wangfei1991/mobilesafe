package com.wyu.mobilesafe.utils;

import java.security.MessageDigest;

/**
 * MD5加密工具
 * @author wangfei
 *
 */
public class MD5Utils {
	private MD5Utils() {
		// TODO Auto-generated constructor stub
	}
	
	public static String md5Password(String password)
	{
		try{
			MessageDigest digest = MessageDigest.getInstance("md5");
			byte[] result = digest.digest(password.getBytes());
			StringBuffer buffer = new StringBuffer();
			for(byte b : result)
			{
				int number = b & 0xfff;		//标准的MD5,是与11111111与,而这里使用fff这种方式是算法的加盐
				String str = Integer.toHexString(number);
				if (str.length() == 1) {
					buffer.append("0");
				}
				buffer.append(str);
			}
			return buffer.toString();
		}catch(Exception e)
		{
			e.printStackTrace();
			return "";
		}
	}
	
}
