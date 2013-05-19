package com.zackehh.bisect;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class Usage extends Activity {

	private GestureDetector gestureDetector;
	private View.OnTouchListener gestureListener;
	private Intent swipe;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usage);
		gestureDetector = new GestureDetector(getBaseContext(), new SwipeGestureDetector());
		gestureListener = new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				return gestureDetector.onTouchEvent(event);
			}
		};
		
		// Set listeners
		((RelativeLayout)findViewById(R.id.wrapper)).setOnTouchListener(gestureListener);
		((ScrollView)findViewById(R.id.usageScroller)).setOnTouchListener(gestureListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.about, menu);
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (gestureDetector.onTouchEvent(event)) {
			return true;
		}
		return super.onTouchEvent(event);
	}

	// Left swipe control because we only go left
	private void onLeftSwipe() {
		swipe = new Intent().setClass(this, MainActivity.class);
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
				if (diff > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					Usage.this.onLeftSwipe();
				}
			} catch (Exception e) {
				Log.e("About", "Error on gestures");
			}
			return false;
		}
	}
}
