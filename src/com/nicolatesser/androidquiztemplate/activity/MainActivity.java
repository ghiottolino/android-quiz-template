package com.nicolatesser.androidquiztemplate.activity;

import java.util.LinkedList;
import java.util.List;

import com.nicolatesser.androidquiztemplate.R;
import com.nicolatesser.androidquiztemplate.R.layout;
import com.nicolatesser.androidquiztemplate.R.menu;
import com.nicolatesser.androidquiztemplate.quiz.Question;
import com.nicolatesser.androidquiztemplate.quiz.QuestionDatabase;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

/**
 * Show a splash screen, load the question database.
 *
 */
public class MainActivity extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ActionBarHelper.setUpActionBar(this);

		//prepare questions database
		QuestionDatabase qd = QuestionDatabase.getInstance();
		List<Question> questions = new LinkedList<Question>();
		qd.prepare(questions);
		//move to next activitiy
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
