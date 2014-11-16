package com.example.going_android;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class RestaurantsFragment extends Fragment {
	View rootView;
	
	public RestaurantsFragment() {
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
  
        rootView = inflater.inflate(R.layout.fragment_restaurants, container, false);
        Fragment current = this;
        
        /*
        AdView adView = (AdView)rootView.findViewById(R.id.adView);
        adView.setAdListener(new AdListener(){
        	public void onAdFailedToLoad(int errorcode){
        		Log.e("Ad", "Failed to load error: " + errorcode);
        	}
        });
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
        	    .addTestDevice("C01A77F26C445B81E9F6DA3D56BE8DC5").build();
        adView.loadAd(adRequest);*/
        
        return rootView;
    }
	
	@Override
	public void onResume(){
		super.onResume();
		System.out.println("ONRESUME!!!");
	}
}
