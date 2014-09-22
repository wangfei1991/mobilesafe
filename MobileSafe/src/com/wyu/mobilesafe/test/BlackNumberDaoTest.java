package com.wyu.mobilesafe.test;

import com.wyu.mobilesafe.dao.BlackNumberDao;

import android.test.AndroidTestCase;

public class BlackNumberDaoTest extends AndroidTestCase{
	public void addTest() throws Exception
	{
		BlackNumberDao dao = new BlackNumberDao(getContext());
		dao.add("wangfei", "110", "1");
	}	
	
	public void findByNumber() throws Exception
	{
		BlackNumberDao dao = new BlackNumberDao(getContext());
		boolean result = dao.findByNumber("110");
		assertEquals(true, result);
	}
	public void update()throws Exception
	{
		BlackNumberDao dao = new BlackNumberDao(getContext());
		dao.update("110", "3");
	}
	public void delete()throws Exception
	{
		BlackNumberDao dao = new BlackNumberDao(getContext());
		dao.delete("110");
	}
	
}
