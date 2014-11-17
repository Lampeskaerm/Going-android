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
		
		
		return rootView;
	}
}
