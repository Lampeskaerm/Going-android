package com.example.going_android;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class LoginFragment extends Fragment {
	public LoginFragment() {
		
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
  
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        final LoginFragment current = this;
        
        Button cancelBtn =(Button) rootView.findViewById(R.id.button1);
        Button loginBtn = (Button) rootView.findViewById(R.id.button2);
        final EditText email = (EditText) rootView.findViewById(R.id.email);
        final EditText password = (EditText) rootView.findViewById(R.id.password);
        
        cancelBtn.setOnClickListener(new OnClickListener() {
    		
        	@Override
        	public void onClick(View v) {
        		Log.e("kage", "cookie");
        		getActivity().getFragmentManager().beginTransaction().remove(current).commit();
        	}
		});
        
        loginBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.i("Login info", "Email: " + email.getText().toString() + "\n Password: " + password.getText().toString());
				MainActivity.isLoggedIn = true;
				getActivity().getFragmentManager().beginTransaction().remove(current).commit();
				getActivity().getFragmentManager().beginTransaction().replace(com.example.going_android.R.id.frame_container, getActivity().getFragmentManager().findFragmentByTag(MainActivity.SHOWN_FRAGMENT_TAG));
			}
		});
        
        return rootView;
    }
}
