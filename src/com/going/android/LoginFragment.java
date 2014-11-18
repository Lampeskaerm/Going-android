package com.going.android;

import com.going.android.R;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginFragment extends Fragment {
	private View clickedView;
	public LoginFragment(View view) {
		clickedView = view;
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
  
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        final LoginFragment current = this;
        
        Button cancelBtn =(Button) rootView.findViewById(R.id.btnCancel);
        Button loginBtn = (Button) rootView.findViewById(R.id.btnLogin);
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
				getActivity().getFragmentManager().popBackStack();
				TextView loginText = (TextView) clickedView.findViewById(R.id.title);
				loginText.setText(R.string.logout);
			}
		});
        
        return rootView;
    }
}
