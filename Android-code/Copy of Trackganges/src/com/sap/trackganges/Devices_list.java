package com.sap.trackganges;

import android.net.Uri;
import android.os.Bundle;
import android.app.FragmentManager;
import android.app.ListActivity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class Devices_list extends ListActivity implements LoaderCallbacks<Cursor>, OnItemClickListener , Dialoginterface
{
	SimpleCursorAdapter sca;
	Uri retailer = Uri.parse("content://com.example.dataproviderwithreference.Simple_Provider/" + Database_Helper.Retailer_Table);
	Uri history = Uri.parse("content://com.example.dataproviderwithreference.Simple_Provider/" + Database_Helper.History_Table);
	Uri register = Uri.parse("content://com.example.dataproviderwithreference.Simple_Provider/" + Database_Helper.Register_Table);
	String interim;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_devices_list);
		ListView lv = this.getListView();
		getLoaderManager().initLoader(0, null, this);
		String from[] = {Database_Helper.T2Col2};
		int[] to = {R.id.DevID};
		sca = new SimpleCursorAdapter(getApplicationContext(), R.layout.device_row, null, from, to,0); 
		lv.setAdapter(sca);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.devices_list, menu);
		return true;
	}

	public void onListItemClick(ListView l, View v, int position, long id) 
	{
		
		TextView tv = (TextView) v.findViewById(R.id.DevID);
		String devid_extracted = tv.getText().toString();
		
		Intent viewhistory = new Intent(getApplicationContext(), Device_history.class);
		viewhistory.putExtra("deviceidentifier", devid_extracted);
		startActivity(viewhistory);
		
//		Toast.makeText(getApplicationContext(), "dont change to listitemclick", Toast.LENGTH_LONG).show();
		
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1)
	{
		String columns[] = { Database_Helper.T3KEY, Database_Helper.T3Col2};
		CursorLoader loader = new CursorLoader(getBaseContext(), register, columns, null, null, null);
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1)
	{
		sca.swapCursor(arg1);
		
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) 
	{
		switch(item.getItemId())
		{
		case R.id.register_device: showdialog();
		
		}
		return super.onMenuItemSelected(featureId, item);
	}
	
	public void showdialog()
	{
		FragmentManager manager = getFragmentManager();
		Dialoghelper mydialog = new Dialoghelper();
		mydialog.show(manager, "REGISTER_DIALOG");
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) 
	{
		sca.swapCursor(null);
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDialogMessage(Bundle bd) 
	{
		interim = bd.getString("key");
		//Store the interim value in the table "Device Register"
		ContentValues values = new ContentValues();
		values.put(Database_Helper.T3Col2, interim);
		getContentResolver().insert(Uri.parse("content://com.example.dataproviderwithreference.Simple_Provider/REGISTER"), values);
		Toast.makeText(getApplicationContext(), interim + "Registered", Toast.LENGTH_LONG).show();
		
	}

}
