package com.sap.trackganges;

import com.sap.trackganges.Retailers;

import android.os.Bundle;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements Communicator
{
	String password = "a";

	EditText pass_text; // 

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
		Button go = (Button) findViewById(R.id.Go_button);
		pass_text = (EditText) findViewById(R.id.Sap_identity);

		go.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View view)
			{
				if (pass_text.getText().toString().equals(password)) 
				{
					show_dialog();
				}
				else 
				{
					pass_text.setText("");
					invalid_pass();
				}

			}

		});

	}

	private void invalid_pass()
	{
		Toast.makeText(this, "Invalid Password!", Toast.LENGTH_LONG).show();

	}

	public void show_dialog()
	{
		FragmentManager manager = getFragmentManager();
		Radio_dialog dialog = new Radio_dialog();
		dialog.show(manager, "Dialog of choice");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	
	@Override
	public void onRetailerSelect()
	{
		Intent i = new Intent(this, Retailers.class);
		startActivity(i);
		
	}

	@Override
	public void onDdeviceSelect() 
	{
		Intent i = new Intent(this, Devices_list.class);
		startActivity(i);
		
	}

	

}
