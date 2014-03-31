package com.nicolatesser.androidquiztemplate.activity;

import com.nicolatesser.androidquiztemplate.quiz.Game;
import com.nicolatesser.androidquiztemplate.quiz.Session;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

public class ResetActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//serialize session
		Game.getInstance().reset();
		SessionUtils.setSession(this, new Session());	
		finish();
	}
	
	
}
