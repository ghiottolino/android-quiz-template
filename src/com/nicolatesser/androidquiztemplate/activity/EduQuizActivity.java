package com.nicolatesser.androidquiztemplate.activity;

import com.nicolatesser.androidquiztemplate.R;
import com.nicolatesser.androidquiztemplate.R.layout;
import com.nicolatesser.androidquiztemplate.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class EduQuizActivity extends QuizActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quiz);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.one_player_quiz, menu);
		return true;
	}

}
