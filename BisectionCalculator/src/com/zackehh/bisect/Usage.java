package com.zackehh.bisect;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Usage page for the calculator, contains a simple view
 * with information on general usage of the calculator.
 * 
 * @author Isaac Whitfield
 * @version 25/05/2013
 */
public class Usage extends Fragment {
	
	// Create a new instance of the fragment
	public static Fragment newInstance() {
		// Return the new page
		return new Usage();
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Return the fragment ViewGroup
		return (ViewGroup)inflater.inflate(R.layout.usage, null);		
	}
}
