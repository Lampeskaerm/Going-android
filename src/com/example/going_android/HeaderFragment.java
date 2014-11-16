package com.example.going_android;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class HeaderFragment extends Fragment {
	
	public HeaderFragment (){
		
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState){
		
		View rootView = inflater.inflate(R.layout.fragment_header, container, false);
		
		TextView loginText =(TextView) rootView.findViewById(R.id.loginLabel);
		
        loginText.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		Fragment loginFragment = new LoginFragment();
        		FragmentManager fm = getFragmentManager();
        		fm.beginTransaction().add(R.id.frame_content, loginFragment).addToBackStack("").commit();
        	}
		});
		
		return rootView;
	}
}
