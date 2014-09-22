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
 * 操作黑名单数据库
 * @author wangfei
 *
 */
public class BlackNumberDao {
	private BlackNumberOpenHelper helper;
	public BlackNumberDao(Context context) {
		helper = new BlackNumberOpenHelper(context);
	}
	/**
	 * 用于添加记录
	 * @param 姓名
	 * @param 电话号码
	 * @param 模式中1代表短信拦截，2代表电话拦截，3代表全部拦截
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
	 * 用于删除一条黑名单数据记录
	 * @param 电话号码
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
	 * 更新号码的模式
	 * @param 号码
	 * @param 模式
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
	 * 获取整个数据库的数据
	 * @return	数据集合
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
				map.put("mode", "短信拦截");
				break;
			case 2:
				map.put("mode", "电话拦截");
				break;
			case 3:
				map.put("mode", "短信  电话拦截");
				break;
			}			
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 根据号码查找数据是否存在
	 * @param 号码
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
