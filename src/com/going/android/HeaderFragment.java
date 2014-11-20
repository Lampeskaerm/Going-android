package com.going.android;

import com.going.android.R;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HeaderFragment extends Fragment {
	
	public HeaderFragment (){
		
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState){
		
		View rootView = inflater.inflate(R.layout.fragment_header, container, false);
		
		
		return rootView;
	}
}
