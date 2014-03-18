package com.sap.trackganges;

import com.sap.trackganges.R;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class Addretailer extends Activity 
{
	Spinner sp;
	static String selected;
	String store_add,store_devid,store_contact,store_fmcg;
	String store_name,store_date,store_city,store_street,store_lat,store_long;
	Button locate,save ;
	TextView xaxis,yaxis;
	EditText devid , ret_name;
	double nlat;
	double nlng;
	String latitude,longitude;
	LocationManager nlocManager;
	LocationListener nlocListener;
	String pan_status = "NS" ,addr_status = "NS",shopdoc_status = "NS";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addretailer);
		sp = (Spinner) findViewById(R.id.edit_ODM);
		ArrayAdapter<CharSequence> ar = ArrayAdapter.createFromResource(this, R.array.device_odm , android.R.layout.simple_list_item_1);
		
		ar.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		sp.setAdapter(ar);
		
		save = (Button) findViewById(R.id.save);
		devid = (EditText) findViewById(R.id.editdevid);
		ret_name = (EditText)findViewById(R.id.edit_store_name);
		locate = (Button)findViewById(R.id.locate);
		
		locate.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View arg0) 
			{
				getlocation();
				
			}
		});
		
		sp.setOnItemSelectedListener(new OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3)
			{	selected = (String) sp.getSelectedItem(); }

			@Override
			public void onNothingSelected(AdapterView<?> arg0) 
			{
				// TODO Auto-generated method stub
				
			}
			
		});
		
				
		save.setOnClickListener(new View.OnClickListener()
		{			
			@Override
			public void onClick(View arg0)
			{
//				Boolean b1 = devid.getText().toString().matches("");
//				Boolean b2 = ret_name.getText().toString().matches("");
//							
//				if( b1||b2 )
//				{
//					Toast.makeText(getApplicationContext(), "RETAILER NAME and DEVID field cannot be empty", Toast.LENGTH_LONG).show();
//					
//				}
//				else
				savetoDB();
			}
		});
		
	}
	
	public void getlocation()
	{
		ContentResolver contentResolver = getBaseContext().getContentResolver();
		boolean networkWifiStatus = Settings.Secure.isLocationProviderEnabled(contentResolver, LocationManager.NETWORK_PROVIDER);
		
		if(!networkWifiStatus)
		{
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(Addretailer.this);
			
			alertDialog.setTitle("Make your location accessible ...");
			alertDialog.setMessage("Your Location is not accessible to us.To show location you have to enable it.");
			alertDialog.setIcon(R.drawable.warning);

			alertDialog.setNegativeButton("Enable", new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int which) {
				startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0);
			}
			});

			alertDialog.setPositiveButton("Cancel", new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog,int which)
				{
				Toast.makeText(getApplicationContext(), "Remember to show location you have to eanable it !", Toast.LENGTH_SHORT).show();
				dialog.cancel();
				}
			});

			alertDialog.show();
		}
		//IF GPS and Network location is accessible
		else 
		{
			nlocManager   = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
			nlocListener = new MyLocationListenerNetWork();
			nlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
					1000 * 1,  // 1 Sec        
			        0,         // 0 meter   
			        nlocListener);
			

	
		}
	}

		
	
	
	public void savetoDB()
	{
		EditText storename = (EditText)findViewById(R.id.edit_store_name);
		store_name = storename.getText().toString();
		
		EditText storecontact = (EditText)findViewById(R.id.edit_store_contact);
		store_contact = storecontact.getText().toString();
		
		// selected variable initialized and defined
		
		EditText date = (EditText)findViewById(R.id.edit_date);
		store_date = date.getText().toString();
		
		CheckBox pan_var = (CheckBox) findViewById(R.id.edit_pan);
		if(pan_var.isChecked())
		pan_status = "Submitted";
		
		CheckBox add_var = (CheckBox) findViewById(R.id.edit_addr_proof);
		if(add_var.isChecked())
		addr_status = "Submitted";	
		

		CheckBox shop_var = (CheckBox) findViewById(R.id.edit_shop_proof);
		if(shop_var.isChecked())
		shopdoc_status = "Submitted";	
		
		EditText fmcg = (EditText)findViewById(R.id.edit_fmcg);
		store_fmcg = fmcg.getText().toString();
		
		EditText address = (EditText)findViewById(R.id.edit_area);
		store_add = address.getText().toString();
		
		EditText street = (EditText)findViewById(R.id.edit_street);
		store_street = street.getText().toString();
		
		EditText city = (EditText)findViewById(R.id.edit_city);
		store_city = city.getText().toString();
		
		EditText devid = (EditText)findViewById(R.id.edit_devid);
		store_devid = devid.getText().toString();
		
		TextView xaxis = (TextView)findViewById(R.id.xaxis);
		store_lat = xaxis.getText().toString();
		
		TextView yaxis = (TextView)findViewById(R.id.yaxis);
		store_long = yaxis.getText().toString();
	
		//Make a ContentValues object for RETAILER table to wrap all this data and fire it to the URI with insert method
		
		ContentValues cv = new ContentValues();
		
		cv.put(Database_Helper.Col1, store_name);
		cv.put(Database_Helper.Col2, store_contact);
		cv.put(Database_Helper.Col3, selected);
		cv.put(Database_Helper.Col4, store_date);
		cv.put(Database_Helper.Col5, pan_status);
		cv.put(Database_Helper.Col6, addr_status);
		cv.put(Database_Helper.Col7, shopdoc_status);
		cv.put(Database_Helper.Col8, store_fmcg);
		cv.put(Database_Helper.Col11, store_add);
		cv.put(Database_Helper.Col9, store_city);
		cv.put(Database_Helper.Col10, store_street);
		cv.put(Database_Helper.Col12, store_devid);
		cv.put(Database_Helper.Col13, store_lat);
		cv.put(Database_Helper.Col14, store_long);
		
		ContentValues cv2 = new ContentValues();
		cv2.put(Database_Helper.T2Col2, store_devid);
		cv2.put(Database_Helper.T2Col3, store_name);
		cv2.put(Database_Helper.T2Col4, store_date);
			
		//Fire to URI
		getContentResolver().insert(Uri.parse("content://com.example.dataproviderwithreference.Simple_Provider/RETAILER"), cv);
		getContentResolver().insert(Uri.parse("content://com.example.dataproviderwithreference.Simple_Provider/HISTORY"), cv2);
		Toast.makeText(getBaseContext(), "Inserted into Both", Toast.LENGTH_LONG).show();
		
		
	}

	public class MyLocationListenerNetWork implements LocationListener	
	{
		@Override
		public void onLocationChanged(Location loc)
		{
			nlat = loc.getLatitude();
			nlng = loc.getLongitude();
			
			latitude = String.valueOf(nlat);
			longitude = String.valueOf(nlng);
			xaxis = (TextView)findViewById(R.id.xaxis);
			yaxis = (TextView)findViewById(R.id.yaxis);
			xaxis.setText(latitude);
			yaxis.setText(longitude);
			
			Log.d("LAT & LNG Network:", nlat + " " + nlng);
		}

		@Override
		public void onProviderDisabled(String provider)
		{
			Log.d("LOG", "Network is OFF!");
		}
		@Override
		public void onProviderEnabled(String provider)
		{
			Log.d("LOG", "Thanks for enabling Network !");
		}
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras)
		{
		}
	}

}
