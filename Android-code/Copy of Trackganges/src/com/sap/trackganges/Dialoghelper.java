package com.sap.trackganges;


import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Dialoghelper extends DialogFragment implements View.OnClickListener
{
	Dialoginterface communicate;
	View view;
	Button yes , no;
	EditText registered_id;
	String buffer,key;
		
	
	@Override
	public void onAttach(Activity activity) 
	{
		super.onAttach(activity);
		communicate = (Dialoginterface) activity;
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		view = inflater.inflate(R.layout.registerdialog, null);	
		yes = (Button) view.findViewById(R.id.registerbutton);
		no =  (Button) view.findViewById(R.id.cancel);
		yes.setOnClickListener(this);
		no.setOnClickListener(this);
		registered_id = (EditText) view.findViewById(R.id.devicetoregister);
		getDialog().setTitle("Register Device");
		return view ;
	}
	
	@Override
	public void onClick(View view)
	{
		if(view.getId()==R.id.registerbutton)
		{
			//extract The DeviceID and store it 
			buffer = registered_id.getText().toString();
			if(buffer.matches(""))
			{
				Toast.makeText(getActivity(), "Please enter a device ID", Toast.LENGTH_LONG).show();
			}
			else
			{
			Bundle bd = new Bundle();
			bd.putString("key", buffer);
			dismiss();
			communicate.onDialogMessage(bd);
			}			
		}
		else dismiss();		
	}

	

}
