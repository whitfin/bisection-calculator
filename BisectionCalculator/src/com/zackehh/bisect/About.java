package com.zackehh.bisect;

import android.app.Activity;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import static android.text.Html.*;

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
		// Return the new page
		return new About();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		// Create a new ViewGroup for the fragment
		ViewGroup vAbout = (ViewGroup)inflater.inflate(R.layout.about, null);		
		// Create array of all elements with links
		int views[] = {
				R.id.link,
				R.id.report,
				R.id.about,
				R.string.link,
				R.string.bug,
				R.string.about
		};
		// Set all the links to direct to their URL
		for(int i = 0; i < 3; i++){
			TextView view = (TextView)vAbout.findViewById(views[i]);
			view.setMovementMethod(LinkMovementMethod.getInstance());
			view.setText(fromHtml(getResources().getString(views[i + 3])));
		}
		// Put the version number beside the author string
		try {
			TextView vAuthor = (TextView)vAbout.findViewById(R.id.author);
			vAuthor.setText("v" + getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName
							+ " " +
							fromHtml(getResources().getString(R.string.author)));
		} catch (NameNotFoundException e) { } 
		// Return the fragment
		return vAbout;
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if(isVisibleToUser){ 
			((InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
		}
	}
}
