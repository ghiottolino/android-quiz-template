package com.nicolatesser.androidquiztemplate.activity;

import android.app.Activity;
import android.os.Bundle;

import com.nicolatesser.androidquiztemplate.quiz.GameHolder;
import com.nicolatesser.androidquiztemplate.quiz.Session;

public class ResetActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBarHelper.setUpActionBar(this);

		//serialize session
		GameHolder.getInstance().reset();
		SessionUtils.setSession(this, new Session());	
		finish();
	}
	
	
}
