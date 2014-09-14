package com.wyu.mobilesafe.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 
 * @author wangfei
 *
 */
public class StringTools {
	private StringTools() {	}		//���캯��˽�л��������ⲿ��������
	
	/**
	 * 
	 * @param inputStream
	 * @return	��ȡinputStream�ĵ����ݣ��������ַ���
	 */
	public static String getStringFromStream(InputStream inputStream) 
	{
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int size = 0;
		try {
			while((size=inputStream.read(buffer)) != -1)
			{
				outputStream.write(buffer, 0, size);
			}
			String str = outputStream.toString();
			outputStream.close();
			return str;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
