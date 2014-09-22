package com.wyu.mobilesafe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wyu.mobilesafe.dao.BlackNumberDao;
import com.wyu.mobilesafe.domain.BlackNumberInfo;
import com.wyu.mobilesafe.utils.FontTools;

import android.R.integer;
import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;
import android.renderscript.Font;

/**
 * ������ʾ����ӣ�ɾ��������
 * @author wangfei
 *
 */
public class BlackNumberActivity extends Activity 
									implements OnClickListener {

	private ListView blackShow_TV;
	private Button addBlack_BT;
	private TextView blackNumberTitle;
	
	private BlackNumberDao blackNumberDao;
	private List<Map<String, String>> data;
	private MyAdapter myAdapter;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_black_number);
		
		/*************��ȡ���ݿ�������ݣ����ݿ�Ƚ�С���Կ��������߳����************/
		blackNumberDao = new BlackNumberDao(BlackNumberActivity.this);
		data = blackNumberDao.find();
		/******************************************************************/
		
		blackShow_TV     = (ListView) findViewById(R.id.blackShow_LV);
		addBlack_BT      = (Button) findViewById(R.id.addBlack_BT);
		blackNumberTitle = 
						(TextView) findViewById(R.id.blackNumberTitle);
		
		myAdapter = new MyAdapter();
		blackShow_TV.setAdapter(myAdapter);		
		addBlack_BT.setOnClickListener(this);
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		/*********************************��������***************************/
		FontTools.setFont(BlackNumberActivity.this, addBlack_BT);
		FontTools.setFont(BlackNumberActivity.this, blackNumberTitle);
		/******************************************************************/
	}
	
	/**
	 * ������ݿ��button�ĵ������
	 */
	@Override
	public void onClick(View v) {
		AlertDialog.Builder builder = 
					new AlertDialog.Builder(BlackNumberActivity.this);
		/***********************dialogʹ�ò����ļ���Ϊ��ͼ********************/
		View view = View.inflate(BlackNumberActivity.this,
									 R.layout.dialog_add_black, null);
		final EditText inputBlack_ET = 
					 (EditText) view.findViewById(R.id.inputBlack_ET);
		final CheckBox blackPhoneMOde_CB = 
					 (CheckBox) view.findViewById(R.id.blackPhoneMOde_CB);
		final CheckBox blackSMSMOde_CB = 
					 (CheckBox) view.findViewById(R.id.blackSMSMOde_CB);
		builder.setView(view);
		/*****************************************************************/
		
		builder.setNegativeButton("ȡ��", 
								new DialogInterface.OnClickListener(){			
			@Override
			public void onClick(DialogInterface dialog, int which) {
								
			}
		});
		
		/*******��ȡcheckbox״̬������ĵĺںţ���ӵ����ݿ⣬��������listview*****/
		builder.setPositiveButton("ȷ��", 
								new DialogInterface.OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				int mode = 0;
				String mode1="" ;
				if (blackSMSMOde_CB.isChecked() && 
							!blackPhoneMOde_CB.isChecked()) 
				{
					mode = 1;
					mode1 = "��������";
					
				}
				else if(!blackSMSMOde_CB.isChecked() && 
						blackPhoneMOde_CB.isChecked())
				{
					mode=2;
					mode1="�绰����";
					
				}
				else if (blackSMSMOde_CB.isChecked() && 
							blackPhoneMOde_CB.isChecked()) 
				{
					mode = 3;
					mode1="����  �绰����";
					
				}
				else if(!blackSMSMOde_CB.isChecked() && 
						!blackPhoneMOde_CB.isChecked())
				{
					mode1="";
					Toast.makeText(BlackNumberActivity.this, 
												"��ѡ�����η�ʽ", 1).show();
					return;
				}
				String phone =  inputBlack_ET.getText().toString().trim();
				if (TextUtils.isEmpty(phone)) {
					Toast.makeText(BlackNumberActivity.this, 
													"������ں�", 1).show();

					return ;
				}
				
				blackNumberDao.add("", phone, String.valueOf(mode));
				Map<String,String> map = new HashMap<String, String>();
				map.put("number", phone);
				map.put("mode", mode1);
				data.add(map);
				myAdapter.notifyDataSetChanged();
			}
		});
		/******************************************************************/
		
		builder.show();
	}
	
	private class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return data.size();
		}

		@Override
		public View getView(final int position, View convertView, 
													ViewGroup parent) {
			// TODO Auto-generated method stub
			View view ;
			ViewHolder holder;
			if (convertView != null) {
				view = convertView;
				holder =(ViewHolder) view.getTag();
			}else {
				view = View.inflate(BlackNumberActivity.this,
									R.layout.black_number_item, null);
				/***********������ͼʱ�����ڴ��ַ�洢��holder�У����ٱ���*********/
				holder = new ViewHolder();
				holder.blackNumber_TV = 
						(TextView) view.findViewById(R.id.blackNumber_TV);
				holder.blackMode_TV = 
						(TextView) view.findViewById(R.id.blackMode_TV);
				FontTools.setFont(BlackNumberActivity.this, 
													holder.blackMode_TV);
				FontTools.setFont(BlackNumberActivity.this, 
													holder.blackNumber_TV);
				holder.deleteBlack_IV = 
						(ImageView) view.findViewById(R.id.deleteBlack_IV);
				/***********************************************************/
				view.setTag(holder);									//��ͼ����holder
			}
			
			/*********************ɾ�����������������ݿ⣬listview**************/		
			holder.deleteBlack_IV.setOnClickListener(new OnClickListener(){
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					AlertDialog.Builder builder =new AlertDialog.Builder(
												BlackNumberActivity.this);
					builder.setMessage("ȷ��Ҫ�Ƴ�������");
					builder.setPositiveButton("ȷ��", 
								  new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
															 int which) {
							// TODO Auto-generated method stub
							blackNumberDao.delete(
									data.get(position).get("number"));
							data.remove(position);
							myAdapter.notifyDataSetChanged();
						}
					});
					builder.setNegativeButton("ȡ��", null);
					builder.show();
				}
			});
			/**************************************************************/
			
			holder.blackNumber_TV.setText(data.get(position).get("number"));
			holder.blackMode_TV.setText(data.get(position).get("mode"));
			
			return view;
		}
		
		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}	
	} 
	/***********************���ڴ洢listview��item�Ŀؼ�********************/
	private class ViewHolder{
		public TextView blackNumber_TV;
		public TextView blackMode_TV;
		public ImageView deleteBlack_IV;
	}
	/********************************************************************/
}
