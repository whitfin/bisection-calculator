package com.zackehh.bisect;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import static android.text.Html.*;
import static com.zackehh.util.Base64.*;

/**
 * About page for the calculator just detailing information
 * on the author and providing any useful links to the user.
 * 
 * @author Isaac Whitfield
 * @version 25/05/2013
 */
public class About extends Fragment {
	
	// Contact 
	private String address = "WkVkb2JHTXlPVEZpUnpsdFpXMUdhbUV5Vm05UlIyUjBXVmRzYzB4dFRuWmlVVDA5";
	
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
			if(i != 1) view.setMovementMethod(LinkMovementMethod.getInstance());
			view.setText(fromHtml(getResources().getString(views[i + 3])));
			address = new String(decode(address, 0));
		}
		// Put the version number beside the author string
		try {
			TextView vAuthor = (TextView)vAbout.findViewById(R.id.author);
			vAuthor.setText("v" + getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName
							+ " " +
							fromHtml(getResources().getString(R.string.author)));
		} catch (NameNotFoundException e) { } 
		((TextView)vAbout.findViewById(R.id.report)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View viewIn) {
            	Intent email = new Intent(Intent.ACTION_SEND);
			    email.putExtra(Intent.EXTRA_EMAIL, new String[]{ address });        
			    email.putExtra(Intent.EXTRA_SUBJECT, "Bisection Calculator Bug Report");
			    email.setType("message/rfc822");
			    startActivity(Intent.createChooser(email, "Select an email client:"));
            }
        });
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
