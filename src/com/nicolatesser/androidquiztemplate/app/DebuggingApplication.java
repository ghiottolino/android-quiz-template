package com.nicolatesser.androidquiztemplate.app;

import android.app.Application;

public class DebuggingApplication extends Application{ 

    @Override
    public void onCreate() {
        super.onCreate();
		Thread.setDefaultUncaughtExceptionHandler(new DebuggingUncaughtExceptionHandler(
				getApplicationContext(), Thread
						.getDefaultUncaughtExceptionHandler()));
	}
    
}
