package com.sap.trackganges;

import com.sap.trackganges.R;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class Radio_dialog extends DialogFragment implements View.OnClickListener , OnCheckedChangeListener
{
	//To go to the next activity, create an interface which can communicate with your main activity class,
	int flag;
	Communicator communicator;
	RadioGroup rg;
	@Override
	public void onAttach(Activity activity) 
	{
		super.onAttach(activity);
		communicator = (Communicator) activity;
	}
	
	
	View view;
	Button show;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
	{	
		getDialog().setTitle("Please Choose your View");
		view = inflater.inflate(R.layout.radio_dialog, null);	
		show = (Button) view.findViewById(R.id.Show);
		rg = (RadioGroup) view.findViewById(R.id.radio_group1);
		rg.setOnCheckedChangeListener(this);
		show.setOnClickListener(this);
		return view ;
	}
	
		
	

	@Override
	public void onClick(View view) 
	{
		if((view.getId() == R.id.Show)&& flag==1)
		{
			
			dismiss();
			communicator.onRetailerSelect();
					
		}
		else
		{
			dismiss();
			communicator.onDdeviceSelect();
		}
			
//		Toast.makeText(getActivity(), "Coming Up Shortly!!", Toast.LENGTH_LONG).show();
		
	}


	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) 
	{
		switch(checkedId)
		{
		
		case R.id.retailer_check : 	flag=1;break;
		case R.id.device_check :    flag=0;break;
		
		}
		
	}


}
