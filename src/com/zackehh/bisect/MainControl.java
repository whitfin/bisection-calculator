package com.zackehh.bisect;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Main control system for the fragments displayed in the app.
 * Sets up the main page allowing access to the different calculators
 * available inside the application.
 * 
 * @author Isaac Whitfield
 * @version 09/06/2013
 */
@SuppressLint("Registered")
public class MainControl extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_page);
		// Set the button to start the Bisection Calculator
		((Button)findViewById(R.id.bisectionButton)).setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				startActivity(new Intent(MainControl.this, CalculatorControl.class));
			}
		});
	}

	@Override
	public void onResume(){
		super.onResume();
		// Override transition for entering the activity
		overridePendingTransition(0, 0);
	}

	@Override
	public void onPause(){
		super.onPause();
		// Override transition for exiting the activity
		overridePendingTransition(0, 0);
	}
}