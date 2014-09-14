package com.wyu.mobilesafe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class SelectContactorActivity extends Activity {

	private ListView listView;
	private TextView phoneTV;
	private List<Map<String, String>> contactorData;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_contactor);
		
		/*****************************listview的设置×××××************************/
		listView = (ListView) findViewById(R.id.contactor_list);
		contactorData = getContactorData();
		listView.setAdapter(new SimpleAdapter(SelectContactorActivity.this,
											contactorData, 
											R.layout.contactor_list_item, 
											new String[]{"name","phone"} , 
											new int[]{R.id.name,R.id.phone}));
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {				
				//返回电话号码
				String phone = contactorData.get(position).get("phone");
				Intent intent = new Intent();
				intent.putExtra("phone",phone);
				setResult(RESULT_OK, intent);
				finish();
			}
		});
		/*********************************************************************/
		
	}

	public List<Map<String, String>> getContactorData() {
		// com.android.contacts/data
		// com.android.contacts/raw_contacts

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map;
		Uri dataUri = Uri.parse("content://com.android.contacts/data");
		Uri rawUri = Uri.parse("content://com.android.contacts/raw_contacts");
		ContentResolver resolver = getContentResolver();
		Cursor rawCursor = resolver.query(rawUri,
				new String[] { "contact_id" }, null, null, null);
		while (rawCursor.moveToNext()) {
			String[] selectionArgs = { rawCursor.getInt(0) + "" };
			Cursor dataCursor = resolver.query(dataUri, new String[] {
					"mimetype", "data1" }, "raw_contact_id=?", selectionArgs,
					null);
			map = new HashMap<String, String>();
			while (dataCursor.moveToNext()) {

				String mimetype = dataCursor.getString(0);
				if ("vnd.android.cursor.item/phone_v2".equals(mimetype)) {
					map.put("phone", dataCursor.getString(1));
				} else if ("vnd.android.cursor.item/name".equals(mimetype)){
					map.put("name", dataCursor.getString(1));
				}
			}
			list.add(map);
		}

		return list;
	}
}
