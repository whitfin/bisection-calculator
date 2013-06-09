package com.zackehh.bisect;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import static java.lang.Math.*;

/**
 * Main control system for the fragments displayed in the app.
 * Sets up the fragments in the application.
 * 
 * @author Isaac Whitfield
 * @version 25/05/2013
 */
public class CalculatorControl extends FragmentActivity {
	
	// Set the number of pages in the app
    private static final int NUM_PAGES = 3;
    // Create a new ViewPager
    private ViewPager mPager;
    // Create a new PagerAdapter
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        // Create a new ViewPager
        mPager = (ViewPager)findViewById(R.id.pager);
        // Create a new control adapter and pass of the number of pages
        mPagerAdapter = new CalculatorControlAdapter(getSupportFragmentManager(), NUM_PAGES);
        // Set the adapter for the ViewPager
        mPager.setAdapter(mPagerAdapter);
        // Always start on the middle page, or as close as possible
        mPager.setCurrentItem((int) ceil(NUM_PAGES/2));
    }
    
    @Override
	public void onResume(){
		super.onResume();
		// Override transition for entering the activity
		this.overridePendingTransition(0, 0);
	}
	
	@Override
	public void onPause(){
		super.onPause();
		// Override transition for exiting the activity
		this.overridePendingTransition(0, 0);
	}
}
