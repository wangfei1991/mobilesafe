package com.wyu.mobilesafe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class SelectContactorActivity extends Activity {

	private ListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_contactor);
		listView = (ListView) findViewById(R.id.contactor_list);
		getContactorData();
	}
	
	public List<Map<String,String>> getContactorData()
	{
//		com.android.contacts/data
//		com.android.contacts/raw_contacts
		
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		Map<String, String> map ;
		Uri dataUri = Uri.parse("content://com.android.contacts/data");
		Uri rawUri = Uri.parse("content://com.android.contacts/raw_contacts");
		ContentResolver resolver = getContentResolver();
		Cursor rawCursor = resolver.query(rawUri, new String[]{"contact_id"}, null, null, null);
		while (rawCursor.moveToNext()) {
			String[] selectionArgs ={ rawCursor.getInt(0)+""};
			Cursor dataCursor = resolver.query(dataUri, new String[]{"mimetype","data1"}, "raw_contact_id=?", selectionArgs, null);
			while(dataCursor.moveToNext())
			{
				System.out.println(dataCursor.toString());
			}
		}
		
		return null;
	}
}
