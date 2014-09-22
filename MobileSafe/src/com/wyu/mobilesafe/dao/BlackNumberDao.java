package com.wyu.mobilesafe.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wyu.mobilesafe.db.BlackNumberOpenHelper;
import com.wyu.mobilesafe.domain.BlackNumberInfo;
/**
 * �������������ݿ�
 * @author wangfei
 *
 */
public class BlackNumberDao {
	private BlackNumberOpenHelper helper;
	public BlackNumberDao(Context context) {
		helper = new BlackNumberOpenHelper(context);
	}
	/**
	 * ������Ӽ�¼
	 * @param ����
	 * @param �绰����
	 * @param ģʽ��1����������أ�2����绰���أ�3����ȫ������
	 */
	public void add(String name,String number,String mode)
	{
		SQLiteDatabase database = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("name", name);
		values.put("number",number);
		values.put("mode", mode);
		database.insert("blacknum", null, values);
		database.close();
	}
	
	/**
	 * ����ɾ��һ�����������ݼ�¼
	 * @param �绰����
	 * @return
	 */
	public int delete(String number)
	{
		SQLiteDatabase database = helper.getWritableDatabase();
		int count = database.delete("blacknum", "number=?", 
											new String[]{number});
		database.close();
		return count;
	}
	
	/**
	 * ���º����ģʽ
	 * @param ����
	 * @param ģʽ
	 * @return
	 */
	public int update(String number,String mode)
	{
		SQLiteDatabase database = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("mode",mode);
		int count = database.update("blacknum", values, "number=?", new String[]{number});
		database.close();
		return count;
	}
	
	/**
	 * ��ȡ�������ݿ������
	 * @return	���ݼ���
	 */
	public List<Map<String, String>> find()
	{
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		SQLiteDatabase database = helper.getReadableDatabase();
		Cursor cursor = database.query("blacknum", 
				new String[]{"name","number","mode"},null, null, null, null, null);
		while(cursor.moveToNext())
		{
			map = new HashMap<String, String>();
			map.put("name", cursor.getString(0));
			map.put("number", cursor.getString(1));
			int mode = Integer.parseInt(cursor.getString(2));
			switch (mode) {
			case 1:
				map.put("mode", "��������");
				break;
			case 2:
				map.put("mode", "�绰����");
				break;
			case 3:
				map.put("mode", "����  �绰����");
				break;
			}			
			list.add(map);
		}
		return list;
	}
	
	/**
	 * ���ݺ�����������Ƿ����
	 * @param ����
	 * @return
	 */
	public boolean findByNumber(String number)
	{
		boolean result = false;
		SQLiteDatabase database = helper.getReadableDatabase();
		Cursor cursor = database.query("blacknum",new String[]{"_id"}, 
				"number=?",new String[]{ number}, null, null, null);
		if(cursor.moveToFirst()) {
			result = true;			
		}
		database.close();
		return result;
	}
	
	public String findMode(String number)
	{
		String result = "";
		SQLiteDatabase database = helper.getReadableDatabase();
		Cursor cursor = database.query("blacknum",new String[]{"mode"}, 
				"number=?",new String[]{ number}, null, null, null);
		if (cursor.moveToFirst()) {
			result = cursor.getString(0);			
		}
		database.close();
		return result;
	}
}
