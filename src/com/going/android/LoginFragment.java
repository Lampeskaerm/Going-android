package com.going.android;

import java.util.Arrays;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

public class LoginFragment extends Fragment {
	private View clickedView;
	private UiLifecycleHelper uiHelper;
	private static final String TAG = "LoginFragment";
	private Fragment current;
	public static GraphUser user;
	
	private Session.StatusCallback callback = new Session.StatusCallback() {
	    @Override
	    public void call(Session session, SessionState state, Exception exception) {
	        onSessionStateChange(session, state, exception);
	    }
	};
	
	public LoginFragment(View view) {
		clickedView = view;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    uiHelper = new UiLifecycleHelper(getActivity(), callback);
	    uiHelper.onCreate(savedInstanceState);
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
  
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        current = this;
        
        /*Button cancelBtn =(Button) rootView.findViewById(R.id.btnCancel);
        Button loginBtn = (Button) rootView.findViewById(R.id.btnLogin);
        final EditText email = (EditText) rootView.findViewById(R.id.email);
        final EditText password = (EditText) rootView.findViewById(R.id.password);
        
        cancelBtn.setOnClickListener(new OnClickListener() {
    		
        	@Override
        	public void onClick(View v) {
        		Log.e("kage", "cookie");
        		closeFragment(current);
        	}
		});
        
        loginBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i("Login info", "Email: " + email.getText().toString() + "\n Password: " + password.getText().toString());
				TextView loginText = (TextView) clickedView.findViewById(R.id.title);
				loginText.setText(R.string.logout);
				closeFragment(current);
			}
		});*/
        
        
        //Facebook authentication
        LoginButton authButton = (LoginButton) rootView.findViewById(R.id.authButton);
        authButton.setFragment(this);
        authButton.setReadPermissions(Arrays.asList("user_likes", "user_status"));
        authButton.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
			
			@Override
			public void onUserInfoFetched(GraphUser user) {
				LoginFragment.user = user;
				if(user == null)
					Log.d("User", "Null");
				else
					Log.d("User", user.getName());
			}
		});

        
        return rootView;
    }
	
	@Override
	public void onResume() {
	    super.onResume();
	    uiHelper.onResume();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onPause() {
	    super.onPause();
	    uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
	    super.onDestroy();
	    uiHelper.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    uiHelper.onSaveInstanceState(outState);
	}
	
	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
	    if (state.isOpened()) {
	    	closeFragment(current);
	        Log.i(TAG, "Logged in...");
	    } else if (state.isClosed()) {
	    	closeFragment(current);
	        Log.i(TAG, "Logged out...");
	    }
	}
	
	private void closeFragment(Fragment current){
		TextView loginText = (TextView) clickedView.findViewById(R.id.title);
		
		if(MainActivity.isLoggedIn){
			MainActivity.isLoggedIn = false;
			loginText.setText(R.string.login);
		} else {
			MainActivity.isLoggedIn = true;
			loginText.setText(R.string.logout);
		}
		
		getActivity().getSupportFragmentManager().beginTransaction().remove(current).commit();
		getActivity().getSupportFragmentManager().popBackStack();
	}
}
