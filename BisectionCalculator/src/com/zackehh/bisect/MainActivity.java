package com.zackehh.bisect;

import android.os.Bundle;
import android.os.Handler;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
//import android.widget.Toast;
import static java.lang.Math.*;

import com.zackehh.parse.*;

/**
 * Calculate various results from Bisection. Simple utility for
 * checking results of own calculations, or just quick maths.
 * 
 * @author Isaac Whitfield
 * @version 13/04/2013
 */

public class MainActivity extends Activity {

	// Define variables for bisection
	double a, b, p, TOL;
	// Set the number of iterations to calculate to, if TOL = 0
	int M;
	// Set the gesture stuff
	private GestureDetector gestureDetector;
	private View.OnTouchListener gestureListener;
	// New intent for swiping
	private Intent swipe;
	// Control for the thread and result
	private Handler threadHandler = new Handler();
	private String setResult;
	// Set boolean for calculation choice
	private boolean rootToCalc = true;
	// Set other variables
	int width;

	// Suppressions due to old/new API support
	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		gestureDetector = new GestureDetector(getBaseContext(), new SwipeGestureDetector());
		gestureListener = new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				return gestureDetector.onTouchEvent(event);
			}
		};
		
		final EditText maxIterations = (EditText)findViewById(R.id.iterations);
		
		// Set the listeners for the touches on the screen 
		maxIterations.setOnTouchListener(gestureListener);
		((ScrollView)findViewById(R.id.scrollable)).setOnTouchListener(gestureListener);
		((EditText)findViewById(R.id.pointA)).setOnTouchListener(gestureListener);
		((EditText)findViewById(R.id.pointB)).setOnTouchListener(gestureListener);
		((EditText)findViewById(R.id.inputTolerance)).setOnTouchListener(gestureListener);
		((TextView)findViewById(R.id.Root)).setOnTouchListener(gestureListener);

		Button calculate = (Button)findViewById(R.id.calculate);
		calculate.setOnTouchListener(gestureListener);
		calculate.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				// Check there was an entered function to avoid a crash
				EditText myFunction = (EditText)findViewById(R.id.function);
				TextView result = (TextView)findViewById(R.id.Root);
				if(myFunction.getText().toString().matches("")){
					// If there isn't one, report it
					result.setText("Please enter a valid function!");
				} else {
					try {
						// Set the variables needed from the user input
						a = Double.parseDouble(((EditText)findViewById(R.id.pointA)).getText().toString());
						b = Double.parseDouble(((EditText)findViewById(R.id.pointB)).getText().toString());
						TOL = Double.parseDouble(((EditText)findViewById(R.id.inputTolerance)).getText().toString());
						// Round the max iterations to the nearest integer
						M = (int) round(Double.parseDouble(((EditText)findViewById(R.id.iterations)).getText().toString()));
					} catch (NumberFormatException e){
						// Catch empty input fields, or invalid inputs
						result.setText("Please provide the required information.");
						return;
					}

					// Calculate the root if the user chooses to
					if(rootToCalc){
						// New thread to stop the screen "freezing"
						new Thread(new Runnable() {
							@Override
							public void run() {
								bisection();
							}
						}).start();
						// If it's going to take a while, inform the user
						if(M > 500){
							result.setText("Please wait...");
						}
					} else {
						// Otherwise we calculate the iterations
						if(TOL != 0){
							result.setText("You will need " + (int) maxIterations() + " iterations.");
						} else {
							result.setText("Please provide a valid tolerance.");
						}
					}
				}
			}
		});

		// Get the display width
		Display display = getWindowManager().getDefaultDisplay();
		// For new API
		if (android.os.Build.VERSION.SDK_INT >= 13){
			Point size = new Point();
			display.getSize(size);
			width = (size.x) / 4;
		} else {
			// For old API
			width = (display.getWidth()) / 4;
		}

		// Set listeners for the two buttons
		final Button calcButtonRoot = (Button)findViewById(R.id.calcButtonRoot);
		final Button calcButtonMax = (Button)findViewById(R.id.calcButtonMax);
		calcButtonRoot.setOnTouchListener(gestureListener);
		calcButtonMax.setOnTouchListener(gestureListener);

		// Set the button width and the onClick()
		calcButtonRoot.setWidth(width);
		calcButtonRoot.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				// If it's not selected, select it
				if(!rootToCalc){
					rootToCalc = true;
					flipButton(calcButtonRoot, calcButtonMax);
					//showToast();
				}
			}
		});

		calcButtonMax.setWidth(width);
		calcButtonMax.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				if(rootToCalc){
					rootToCalc = false;
					flipButton(calcButtonMax, calcButtonRoot);
					//showToast();
				}
			}
		});

		// Start of setting up landscape view
		if(getResources().getConfiguration().orientation == 2){
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)calculate.getLayoutParams();
			params.setMargins(width, 0, width, 0); 
			calculate.setLayoutParams(params);
			calculate.setWidth(width);

			int views[] = {
					R.id.pointA, 
					R.id.pointB, 
					R.id.textA,
					R.id.textB,
					R.id.Tolerance,
					R.id.inputTolerance,
					R.id.maxIterations,
					R.id.iterations
			};

			// Set the width of all the views
			for(int i = 0; i < views.length; i++){
				TextView view = (TextView)findViewById(views[i]);
				view.setWidth(width);
			}
		}
		// End of setting up landscape view
	}

	/**
	 * Remaps the main page back button to exit the application, instead
	 * of going to the About/Usage page. Makes it seem a bit neater.
	 */
	public void onBackPressed() {
		Intent exit = new Intent(Intent.ACTION_MAIN);
		exit.addCategory(Intent.CATEGORY_HOME);
		exit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(exit);
	}

	/**
	 * Simple class to flip the selected calculation button. Not incredibly
	 * useful, but it makes for less code.
	 * 
	 * @param pressed the button being pressed
	 * @param released the button being released
	 */
	public void flipButton(Button pressed, Button released){
		pressed.setBackgroundResource(R.drawable.roundedhighlight);
		released.setBackgroundResource(R.drawable.rounded);
	}

	/**
	 * Shows a toast message when the user changes what will be
	 * calculated. Not sure if it's needed so not included currently.
	 */
	/*public void showToast(){
		int duration; CharSequence text;
		if(rootToCalc){
			text = "We will now calculate the root.";
		} else {
			text = "We will now calculate iterations.";
		}
		duration = Toast.LENGTH_SHORT;
		Toast.makeText(getApplicationContext(), text, duration).show();
	}*/

	/**
	 * Calls updateUI method to update the result from the thread.
	 */
	final Runnable updateRunnable = new Runnable() {
		public void run() {
			updateUI();
		}
	};

	/**
	 * Allows the updating of the result from the thread. Bit of a hacky
	 * method called from updateRunnable.
	 */
	private void updateUI(){
		TextView result = (TextView)findViewById(R.id.Root);
		result.setText(setResult);
	}

	/**
	 * Beginning of the creation of the swipe gestures used to access other
	 * menus. Done the old-school way for compatibility and I just generally
	 * dislike the Action Bar at the top of this app.
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (gestureDetector.onTouchEvent(event)) {
			return true;
		}
		return super.onTouchEvent(event);
	}

	// Left swipe control to go to About page
	private void onLeftSwipe() {
		swipe = new Intent().setClass(this, About.class);
		startActivity(swipe);
	}

	// Right swipe control to go to Usage page
	private void onRightSwipe() {
		swipe = new Intent().setClass(this, Usage.class);
		startActivity(swipe);
	}

	// Private class for gestures
	private class SwipeGestureDetector 
	extends SimpleOnGestureListener {
		// Swipe properties, you can change it to edit swipe requirements
		private static final int SWIPE_MIN_DISTANCE = 120;
		private static final int SWIPE_MAX_OFF_PATH = 200;
		private static final int SWIPE_THRESHOLD_VELOCITY = 200;

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2,
				float velocityX, float velocityY) {
			try {
				float diffAbs = Math.abs(e1.getY() - e2.getY());
				float diff = e1.getX() - e2.getX();

				if (diffAbs > SWIPE_MAX_OFF_PATH)
					return false;

				// Left swipe
				if (diff > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					MainActivity.this.onLeftSwipe();

				// Right swipe
				} else if (-diff > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					MainActivity.this.onRightSwipe();
				}
			} catch (Exception e) {
				Log.e("MainActivity", "Error on gestures");
			}
			return false;
		}
	}

	/**
	 * Class to calculate the bisection of an interval between a and b to the tolerance TOL, 
	 * taking into consideration a maximum number of iterations M. Also allows for 
	 * calculating the bisection to a given number of iterations without considering a 
	 * tolerance.
	 */
	public void bisection(){
		// Attempt at stopping infinite loops. Bit bleh, needs reworking sometime.
		if(TOL == 0 && M == 0){
			TOL = 0.000000000000001;
		}

		// Initialize counter for loop counts
		int i = 1;
	
		// If the user hasn't selected a maximum, go as needed
		if (M == 0){
			M = (int) maxIterations();
		}

		// If the equation seems solvable
		if(testEquation()){
			// Do the bisection
			bisection:
				while(i <= M){
					p = (a + b)/2;
					if(fOfX(p) == 0 || (b - a)/2 < TOL){
						setResult = "Root = " + String.valueOf(p) + " after " + i + " iterations.";
						break bisection;
					}
					if((fOfX(p) * fOfX(a)) > 0){
						a = p;
					} else {
						b = p;
					}
					if(i == M && (fOfX(p) != 0 || (b - a)/2 >= TOL) && TOL != 0){
						setResult = "The bisection cannot be calcluated to this tolerance within this amount of iterations.";
					} else if (i == M && TOL == 0){
						if(i == 1){
							setResult = "Root = " + String.valueOf(p) + " after " + i + " iteration.";
						} else {
							setResult = "Root = " + String.valueOf(p) + " after " + i + " iterations.";
						}
					}
					i++;
				}
		} else {
			setResult = "Please check your function has a root at 0.";
		}
		// Update the UI when the bisection is complete
		threadHandler.post(updateRunnable);
	}

	/**
	 * Basic test of the input function to check if there's a possibility for a root.
	 * 
	 * @return 
	 */
	public Boolean testEquation(){
		if(fOfX(a) < 0 && fOfX(b) > 0){
			return true;
		} else if (fOfX(a) > 0 && fOfX(b) < 0){
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Sets the function of X which needs to be solved.
	 * 
	 * @param x the value used in place of x
	 * @return x the value of x
	 */
	public double fOfX(double x){
		// Test function
		Calculable function;
		try {
			// Find the function from user input
			EditText myFunction = (EditText)findViewById(R.id.function);
			function = new ExpressionBuilder(myFunction.getText().toString()).withVariableNames("x").build();
			function.setVariable("x",x);
			x = function.calculate();
		} catch (UnknownFunctionException e) {
			e.printStackTrace();
		} catch (UnparsableExpressionException e){
			e.printStackTrace();
		}
		return x;
	}

	/**
	 * Calculates the maximum number of iterations needed to find the bisection within the tolerance.
	 * 
	 * @return t the maximum number of iterations
	 */
	public double maxIterations(){
		double t;
		t = ceil(((log((b-a)/TOL))/log(2)));
		return t;
	}
}
