package com.zackehh.bisect;

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
public class MainControl extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// Set the button to start the Bisection Calculator
		Button calculate = (Button)findViewById(R.id.bisectionButton);
		calculate.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				startActivity(new Intent(MainControl.this, CalculatorControl.class));
			}
		});
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