package com.sap.trackganges;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.InputType;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Update_Info extends Activity 
{
	Uri RETAIL = Uri.parse("content://com.example.dataproviderwithreference.Simple_Provider/" + Database_Helper.Retailer_Table);
	Uri HISTORY = Uri.parse("content://com.example.dataproviderwithreference.Simple_Provider/" + Database_Helper.History_Table);
	public static SQLiteDatabase db;
	public Database_Helper dbobj;
	String data1 , current;
	LinearLayout l;
	Calendar c = Calendar.getInstance();
	SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
    String formatdate = df.format(c.getTime());
	Button update;
	Spinner sp;
	
	int toast;
	String pan_status = "NS" ,addr_status = "NS",shopdoc_status = "NS";
	EditText et1,et2,et3,et4,et5,et6,et7,et8,et9;
	CheckBox pan,add,shop;
	ImageButton editphone,editdevid,editfmcg,editstore,editdate,editodm , editaddress ;
	String displayname , displaycontact , displayfmcg , displaydevid ,displayarea , displaydate , displayodm , displaypan ,displayshopproof, displayaddressproof;
	String selection,displaystreet,displaycity;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update__info);
		Intent i = getIntent();
		data1 = i.getStringExtra("pass1");
		Cursor result = queryfromdb();

		if(result.moveToFirst())
		{			
			displayname = result.getString(result.getColumnIndex(Database_Helper.Col1));
			displaycontact = result.getString(result.getColumnIndex(Database_Helper.Col2));
			displayfmcg = result.getString(result.getColumnIndex(Database_Helper.Col8));
			displayarea = result.getString(result.getColumnIndex(Database_Helper.Col11));
			displaydevid = result.getString(result.getColumnIndex(Database_Helper.Col12));
			displaydate = result.getString(result.getColumnIndex(Database_Helper.Col4));
			displayodm = result.getString(result.getColumnIndex(Database_Helper.Col3));
			displaypan = result.getString(result.getColumnIndex(Database_Helper.Col5));
			displayshopproof = result.getString(result.getColumnIndex(Database_Helper.Col7));
			displayaddressproof = result.getString(result.getColumnIndex(Database_Helper.Col6));
			displaystreet = result.getString(result.getColumnIndex(Database_Helper.Col10));
			displaycity = result.getString(result.getColumnIndex(Database_Helper.Col9));
		}
		
		result.close();
		
		// Set the Textviews
		et1 = (EditText) findViewById(R.id.edit_store_name) ; et1.setText(displayname);
		et2 = (EditText) findViewById(R.id.edit_store_contact) ; et2.setText(displaycontact);
		et3 = (EditText) findViewById(R.id.edit_fmcg); et3.setText(displayfmcg);
		et4 = (EditText) findViewById(R.id.edit_area); et4.setText(displayarea);
		et5 = (EditText) findViewById(R.id.edit_devid); et5.setText(displaydevid);
		et6 = (EditText) findViewById(R.id.edit_date); et6.setText(displaydate);
		et7 = (EditText) findViewById(R.id.edit_ODM); et7.setText(displayodm);
		et8 =(EditText) findViewById(R.id.edit_street);et8.setText(displaystreet);
		et9 =(EditText) findViewById(R.id.edit_city);et9.setText(displaycity);
		
		pan = (CheckBox) findViewById(R.id.edit_pan);
		if(displaypan.matches("Submitted"))
			pan.setChecked(true);
		
				
		add = (CheckBox) findViewById(R.id.edit_addr_proof);
		if(displayaddressproof.matches("Submitted"))
			add.setChecked(true);
		
		shop = (CheckBox) findViewById(R.id.edit_shop_proof);
		if(displayshopproof.matches("Submitted"))
			shop.setChecked(true);
		
		initiate_edit();
		
		
	}

	public void initiate_edit()
	{
		editphone = (ImageButton) findViewById(R.id.editphone);
//		editstore = (ImageButton) findViewById(R.id.editstore);
		editfmcg = (ImageButton) findViewById(R.id.editfmcg);
		editaddress = (ImageButton) findViewById(R.id.editaddress);
		editdate = (ImageButton) findViewById(R.id.editdate);
		editodm = (ImageButton) findViewById(R.id.editodm);
		editdevid = (ImageButton) findViewById(R.id.editdevid);
		
		
		editphone.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View arg0) 
			{
				et2.setEnabled(true);
				et2.setInputType(InputType.TYPE_CLASS_TEXT);
				et2.setFocusable(true);
				
			}
		});
		
		
		editdevid.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View arg0) 
			{
				et5.setEnabled(true);
				et5.setInputType(InputType.TYPE_CLASS_TEXT);
				et5.setFocusable(true);
				
			}
		});
		editfmcg.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View arg0) 
			{
				et3.setEnabled(true);
			    et3.setInputType(InputType.TYPE_CLASS_TEXT);
			    et3.setFocusable(true);
				
			}
		});
		editaddress.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View arg0) 
			{
				et4.setEnabled(true);
			    et4.setInputType(InputType.TYPE_CLASS_TEXT);
			    et4.setFocusable(true);
				
			}
		});
		editdate.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View arg0) 
			{
				et6.setEnabled(true);
			    et6.setInputType(InputType.TYPE_CLASS_TEXT);
			    et6.setFocusable(true);
				
			}
		});
		
		update = (Button) findViewById(R.id.update);
		
		update.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View arg0) 
			{
				current = et5.getText().toString();
				
				if(current.equals(displaydevid))			
				{
					update_retailer();
					
					update_history();
					
				}				
				else
//New DEVID is either an empty string or a new DEVID					
				{
					if(current.isEmpty())
					{
						update_retailer();
						insert_old_into_history();
					}
					else
					{
					update_retailer();
					insert_old_into_history();
					insert_new_into_history();
					}
					
					
//					update_retailer();
//					insert_old_into_history(); //insert the existing devid into history table with sap labs info
//					if(!current.isEmpty())
//					{
//						update_retailer();
//						insert_old_into_history();
//						insert_new_into_history(); //insert the new device info only if a new device is entered in the field
//					}
				}
				
				
				
				
			}
		});
		
		
	}
	
	private void insert_old_into_history()
	{
		if(!displaydevid.isEmpty())
		{
		ContentValues cv = new ContentValues();
		cv.put(Database_Helper.T2Col2,displaydevid); //this has to be the Existing device id, not the new one
		cv.put(Database_Helper.T2Col3,"SAP Labs");
		cv.put(Database_Helper.T2Col4, formatdate);									
		getContentResolver().insert(HISTORY	, cv);
		}
	}
	private void insert_new_into_history()
	{
		//insert only if new DEVID is not null
		if(!current.isEmpty())
		{
		ContentValues cv2 = new ContentValues();
		cv2.put(Database_Helper.T2Col2,et5.getText().toString());
		cv2.put(Database_Helper.T2Col3,displayname);
		cv2.put(Database_Helper.T2Col4,et6.getText().toString());
		getContentResolver().insert(HISTORY	, cv2);
		}
	}
	
	private void update_retailer()
	{
		String where =  "RET_NAME = '" + data1+"'";
		ContentValues cv = new ContentValues();
		cv.put(Database_Helper.Col1, et1.getText().toString());
		cv.put(Database_Helper.Col2, et2.getText().toString());
		cv.put(Database_Helper.Col3, et7.getText().toString());
		cv.put(Database_Helper.Col4, et6.getText().toString());
		
		
		if(pan.isChecked())
		pan_status = "Submitted";
		
		
		if(add.isChecked())
		addr_status = "Submitted";	
		

		
		if(shop.isChecked())
		shopdoc_status = "Submitted";	
		
		cv.put(Database_Helper.Col5, pan_status);
		cv.put(Database_Helper.Col6, addr_status);
		cv.put(Database_Helper.Col7, shopdoc_status);
		cv.put(Database_Helper.Col8, et3.getText().toString());
		cv.put(Database_Helper.Col9, et4.getText().toString());
		cv.put(Database_Helper.Col10,et5.getText().toString());
		getContentResolver().update(RETAIL, cv, where, null); // The update method returns the number of rows updated
		//Update the history table too
		
	}
	
	private void update_history()
	{
		String where_for_history = "DEV_ID = '" + et5.getText().toString()+"' AND LOCATION = '" + displayname + "'" ;
		ContentValues cv2 = new ContentValues();
		cv2.put(Database_Helper.T2Col4, et6.getText().toString());
		getContentResolver().update(HISTORY, cv2, where_for_history, null);
	}
	
	
	private Cursor queryfromdb()
	{
		
		String[] projection = {Database_Helper.Col1 , Database_Helper.Col2, Database_Helper.Col3, Database_Helper.Col4,Database_Helper.Col5,
							   Database_Helper.Col6,Database_Helper.Col7,Database_Helper.Col8,Database_Helper.Col9,Database_Helper.Col10,Database_Helper.Col11,Database_Helper.Col12};

		selection = "RET_NAME = " + "'" + data1 + "'" ;
		Cursor c = getContentResolver().query(RETAIL, projection, selection , null, null);
		return c;
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.update__info, menu);
		return true;
	}

}
