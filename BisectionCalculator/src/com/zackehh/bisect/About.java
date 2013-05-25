package com.zackehh.bisect;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * About page for the calculator just detailing information
 * on the author and providing any useful links to the user.
 * 
 * @author Isaac Whitfield
 * @version 25/05/2013
 */
public class About extends Fragment {
	
	// Create a new instance of the fragment
	public static Fragment newInstance() {
		// Create a new usage page
		About about = new About();	
		// Return the new page
		return about;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		// Create a new ViewGroup for the fragment
		ViewGroup vAbout = (ViewGroup)inflater.inflate(R.layout.activity_about, null);		
		// Set the listeners and the hyperlinking
		((TextView)vAbout.findViewById(R.id.link)).setMovementMethod(LinkMovementMethod.getInstance());
		((TextView)vAbout.findViewById(R.id.link)).setText(Html.fromHtml(getResources().getString(R.string.link)));
		((TextView)vAbout.findViewById(R.id.report)).setMovementMethod(LinkMovementMethod.getInstance());
		((TextView)vAbout.findViewById(R.id.report)).setText(Html.fromHtml(getResources().getString(R.string.bug)));
		((TextView)vAbout.findViewById(R.id.about)).setMovementMethod(LinkMovementMethod.getInstance());
		((TextView)vAbout.findViewById(R.id.about)).setText(Html.fromHtml(getResources().getString(R.string.about)));
		// Return the fragment
		return vAbout;
	}

}
