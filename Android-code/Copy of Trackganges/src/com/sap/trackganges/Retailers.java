package com.sap.trackganges;

import android.app.Activity;
import android.app.ListActivity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class Retailers extends Activity implements LoaderCallbacks<Cursor>
{
	ArrayAdapter<String> adapter = null; // adapter will be used to map our list
											// to the autocompleteview
	Uri RETAIL = Uri.parse("content://com.example.dataproviderwithreference.Simple_Provider/" + Database_Helper.Retailer_Table);
	
	SimpleCursorAdapter sca;
	EditText search;
	
	TextWatcher filterTextWatcher;

	// @Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_retailers);
		ImageView edit = (ImageView) findViewById(R.id.write);

		
		
		// Initialize the list view
				ListView lv = (ListView) findViewById(R.id.retail_list);
				
				lv.setTextFilterEnabled(true);
				getLoaderManager().initLoader(0, null, this);
				String from[] = { Database_Helper.Col1, Database_Helper.Col12 };
				int[] to = { R.id.name_field, R.id.devid_field };
				sca = new SimpleCursorAdapter(this, R.layout.listrow, null, from, to, 0);		
				lv.setAdapter(sca);

				
		// Start filtering process		
		search = (EditText) findViewById(R.id.search_bar);
		search.addTextChangedListener(new TextWatcher()
		{

			@Override
			public void afterTextChanged(Editable s) 
			{
				ListView lv = (ListView) findViewById(R.id.retail_list);
				SimpleCursorAdapter filteradapter = (SimpleCursorAdapter)lv.getAdapter();
				filteradapter.getFilter().filter(s.toString());
				
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) 
			{
				
				
			}
			
		});
		
		sca.setFilterQueryProvider(new FilterQueryProvider() 
		{

			@Override
			public Cursor runQuery(CharSequence constraint)
			{
				return getRetailerFilteredList(constraint);
			}
			
		});
		
		
		
		lv.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) 
			{
				TextView tv = (TextView) findViewById(R.id.name_field);
				String tv_extracted = tv.getText().toString();
				
				Intent update_or_view = new Intent(getApplicationContext(), Update_Info.class);
				update_or_view.putExtra("pass1", tv_extracted);
				startActivity(update_or_view);
				
			}
		});		
		
		
		edit.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v)
			{
				create_entry();

			}
		});
	}
	
	public Cursor getRetailerFilteredList(CharSequence constraint)
	{
		SQLiteQueryBuilder querybuilder = new SQLiteQueryBuilder();
		querybuilder.setTables(Database_Helper.Register_Table);
		String from[] = { Database_Helper.KEY1,Database_Helper.Col1, Database_Helper.Col12 };
		
		if(constraint == null || constraint.length() == 0)
		{
			return getContentResolver().query(RETAIL, from, null , null, null);
		}
		else
		{
			String value = "%"+constraint.toString()+"%";
			String selection = "RET_NAME LIKE " + "'" + value + "'" ;
			return getContentResolver().query(RETAIL, from, selection , null, null);
		}
		
	}

	public void create_entry() 
	{
		Intent i = new Intent(Retailers.this, Addretailer.class);
		startActivity(i);
	}

	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) 
	{
		String columns[] = { Database_Helper.KEY1, Database_Helper.Col1, Database_Helper.Col12 };
		CursorLoader loader = new CursorLoader(getBaseContext(), RETAIL, columns, null, null, null);
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) 
	{
		sca.swapCursor(arg1);
		// TODO Auto-generated method stub
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0)
	{
		sca.swapCursor(null);
	}


	
}
