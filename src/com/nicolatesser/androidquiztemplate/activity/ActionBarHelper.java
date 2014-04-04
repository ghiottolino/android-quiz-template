package com.nicolatesser.androidquiztemplate.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;


@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class ActionBarHelper {

	public static void setUpActionBar(Activity activity) {
	    // Make sure we're running on Honeycomb or higher to use ActionBar APIs
	    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
	        android.app.ActionBar actionBar = activity.getActionBar();
	        actionBar.setHomeButtonEnabled(true);
	    }
	}
}
