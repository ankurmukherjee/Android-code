package com.sap.trackganges;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class Device_history extends ListActivity implements  OnItemClickListener ,LoaderCallbacks<Cursor>
{
	SimpleCursorAdapter sca;
	String selection;
	Uri history = Uri.parse("content://com.example.dataproviderwithreference.Simple_Provider/" + Database_Helper.History_Table);
	String data2;
	Cursor c;
	TextView device;
	ListView historylist;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
    {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_device_history);
		historylist = this.getListView();
		Intent intent = getIntent();
		data2 = intent.getStringExtra("deviceidentifier");
		device = (TextView) findViewById(R.id.DEVID);
		device.setText(data2);
		getLoaderManager().initLoader(0,null,this);
		
		String from[] = {Database_Helper.T2Col3,Database_Helper.T2Col4};
		int to[] = {R.id.location,R.id.date};
		
//		String columns[] = { Database_Helper.T2KEY, Database_Helper.T2Col3, Database_Helper.T2Col4 };
//		selection = "DEV_ID = " + "'" + data2 + "'" ;
				
//		c = getContentResolver().query(history, columns, selection, null, null);
		
		sca = new SimpleCursorAdapter(this,R.layout.history_row,null,from,to,0);
		historylist.setAdapter(sca);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.device_details, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) 
	{
		// TODO Auto-generated method stub
		
	}

	
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1)
	{
		String columns[] = { Database_Helper.T2KEY, Database_Helper.T2Col3, Database_Helper.T2Col4 };
		selection = "DEV_ID = " + "'" + data2 + "'" ;
		CursorLoader loader = new CursorLoader(getBaseContext(), history , columns, selection, null, null);
		return loader;
	}

	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1)
	{
		sca.swapCursor(arg1);
		
	}

	public void onLoaderReset(Loader<Cursor> arg0) 
	{
		sca.swapCursor(null);
		
	}

}
