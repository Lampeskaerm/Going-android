package com.going.android;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.going.android.R;
import com.facebook.AppEventsLogger;
import com.going.android.adapter.NavDrawerListAdapter;
import com.going.android.model.NavDrawerItem;

public class MainActivity extends Activity {
	public static boolean isLoggedIn = false;
	public static String SHOWN_FRAGMENT_TAG = "shownFragment";
	public static String HEADER_FRAGMENT_TAG = "headerFragment";
	
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerListTop;
    private ListView mDrawerListBottom;
    private View mDrawerView;
    private ActionBarDrawerToggle mDrawerToggle;
 
    // nav drawer title
    private CharSequence mDrawerTitle;
 
    // used to store app title
    private CharSequence mTitle;
    private CharSequence mMenuTitle = "Menu";
 
    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
 
    private ArrayList<NavDrawerItem> navDrawerItems;
    private ArrayList<NavDrawerItem> navDrawerItemsBottom;
    private NavDrawerListAdapter adapter;
    private NavDrawerListAdapter adapterBottom;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);	
        
        mTitle = mDrawerTitle = getTitle();
 
        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
 
        // nav drawer icons from resources
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);
 
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerListTop = (ListView) findViewById(R.id.list_menucontentTop);
        mDrawerListBottom = (ListView) findViewById(R.id.list_menucontentBottom);
        mDrawerView =  (View) findViewById(R.id.list_slidermenu);
 
        navDrawerItems = new ArrayList<NavDrawerItem>();
        navDrawerItemsBottom = new ArrayList<NavDrawerItem>();
        
        // adding nav drawer items to array
        // Restaurants
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(0, -1)));
        // Bars
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(1, -1)));
        // Supermarkets
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(2, -1)));
        
        //Login
        navDrawerItemsBottom.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0,-1)));
        
        // Recycle the typed array
        navMenuIcons.recycle();
 
        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        adapterBottom = new NavDrawerListAdapter(getApplicationContext(),
        		navDrawerItemsBottom);
        mDrawerListTop.setAdapter(adapter);
        mDrawerListBottom.setAdapter(adapterBottom);
 
        // enabling action bar app icon and behaving it as toggle button
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
 
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ){
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mMenuTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }
 
            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        
        	
        /**
         * At startup
         */
        
        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(0);
        }
        mDrawerListTop.setOnItemClickListener(new SlideMenuClickListener());
        mDrawerListBottom.setOnItemClickListener(new LoginClickListener());
        
        
        /**************************
         * Setting initial header *
         **************************/
        Fragment headerFragment = new HeaderFragment();
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.frame_mainheadercontainer, headerFragment, HEADER_FRAGMENT_TAG).commit();
    }
 
    /**
     * Slide menu item click listener
     * */
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }
    
    /**
     * Login menu item click listener
     */
    
    private class LoginClickListener implements
    		ListView.OnItemClickListener {
    	@Override
    	public void onItemClick(AdapterView<?> parent, View view, int position, 
    			long id) {
    		
    		TextView loginText = (TextView) view.findViewById(R.id.title);
    		
    		if(isLoggedIn){
    			//Set User to null here
    			loginText.setText(getResources().getString(R.string.login));
    			isLoggedIn = false;
                mDrawerLayout.closeDrawer(mDrawerView);
    		} else {

        		Fragment loginFragment = new LoginFragment(view);
        		FragmentManager fm = getFragmentManager();
        		fm.beginTransaction().add(R.id.frame_content, loginFragment).addToBackStack("").commit();
        		
                mDrawerLayout.closeDrawer(mDrawerView);
    		}
    	}
    }
    
    /**
     * Diplaying fragment view for selected nav drawer list item
     * */
    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
        case 0:
            fragment = new RestaurantsFragment();
            SHOWN_FRAGMENT_TAG = "restaurants";
            break;
        case 1:
            fragment = new BarsFragment();
            SHOWN_FRAGMENT_TAG = "bars";
            break;
        case 2:
            fragment = new SupermarketsFragment();
            SHOWN_FRAGMENT_TAG = "supermarkets";
            break;
 
        default:
            break;
        }
 
        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_mainviewcontainer, fragment, SHOWN_FRAGMENT_TAG).commit();
            
            //Execute the transaction so the fragmentmanager is updated
            getFragmentManager().executePendingTransactions();
 
            // update selected item and title, then close the drawer
            mDrawerListTop.setItemChecked(position, true);
            mDrawerListTop.setSelection(position);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerView);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }
 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
 
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
        case R.id.action_settings:
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
 
    /***
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerView);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }
 
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mMenuTitle);
    }
 
    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */
 
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }
 
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    
    @Override
    protected void onResume() {
      super.onResume();

      // Logs 'install' and 'app activate' App Events.
      AppEventsLogger.activateApp(this);
    }
    
    @Override
    protected void onPause() {
      super.onPause();

      // Logs 'app deactivate' App Event.
      AppEventsLogger.deactivateApp(this);
    }
}