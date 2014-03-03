package com.zackehh.bisect;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Adapter to show the fragments for the calculator within
 * the main CalculatorControl activity.
 * 
 * @author Isaac Whitfield
 * @version 25/05/2013
 */
public class CalculatorControlAdapter extends FragmentPagerAdapter {
	
	// The number of pages in the app
	private int pages;
	
	// Create a new adapter using the Fragment Manager
	public CalculatorControlAdapter(FragmentManager fManager, int pNum) {
		super(fManager);	
		pages = pNum;
	}
	
	// Control positioning of fragments within application
	public Fragment getItem(int position) {
		// Create a new fragment
		Fragment fragment = new Fragment();
		// Depending on the position, set the fragment
		switch(position){
			// First page is the usage page
			case 0:
				fragment = Usage.newInstance();	
				break;
			// Second page is the main calculator
			case 1:
				fragment = Calculation.newInstance();
				break;
			// Third page is the about page
			case 2:
				fragment = About.newInstance();	
				break;
			}
		// Return the fragment
		return fragment;
	}
	
	// Retrieve the number of fragments
	public int getCount() {
		// Return the number of pages
		return pages;
	}

}