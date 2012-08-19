/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.nicolatesser.androidquiztemplate;

import java.util.List;
import java.util.Vector;

import com.nicolatesser.androidquiztemplate.actionbarcompat.ActionBarActivity;
import com.nicolatesser.androidquiztemplate.fragments.ArticleFragment;
import com.nicolatesser.androidquiztemplate.fragments.HeadlinesFragment;
import com.nicolatesser.androidquiztemplate.quiz.Answer;
import com.nicolatesser.androidquiztemplate.quiz.Game;
import com.nicolatesser.androidquiztemplate.quiz.Question;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends ActionBarActivity {
	
	
	private TextView questionTextView;
	private TextView errorTextView;
	private TextView totalResultTextView;
	private TextView consecutiveResultTextView;
	private TextView recordTextView;
	private ListView mListView;
	private ListAdapter adapter;
	private Game game;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quiz);

		questionTextView = (TextView) findViewById(R.id.question);
		totalResultTextView = (TextView) findViewById(R.id.totalResult);
		consecutiveResultTextView = (TextView) findViewById(R.id.consecutiveResult);
		recordTextView = (TextView) findViewById(R.id.record);
		errorTextView = (TextView) findViewById(R.id.error);
		errorTextView.setVisibility(0);
		errorTextView.setText("");
		mListView = (ListView) findViewById(R.id.list);
		
		
		initGame();
		Question question = this.game.getQuestion();
		displayQuestion(question);
		

	}
	
	public void displayQuestion(Question question)
	{
		questionTextView.setText(question.getQuestionText());
		adapter = new AnswerAdapter(this, R.id.answer, question.getAnswers());
		mListView.setAdapter(adapter);
	}

	public void initGame() {
		List<Question> questions = new Vector<Question>();
		List<Answer> answers = new Vector<Answer>();

		Answer answer1 = new Answer("answer 1", false);
		Answer answer2 = new Answer("answer 2", true);
		Answer answer3 = new Answer("answer 3", false);
		Answer answer4 = new Answer("answer 4", false);
		answers.add(answer1);
		answers.add(answer2);
		answers.add(answer3);
		answers.add(answer4);

		Question question1 = new Question("question 1", answers);
		Question question2 = new Question("question 2", answers);

		questions.add(question1);
		questions.add(question2);

		this.game = new Game(questions);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.main, menu);

		// Calling super after populating the menu is necessary here to ensure
		// that the
		// action bar helpers have a chance to handle this event.
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Toast.makeText(this, "Tapped home", Toast.LENGTH_SHORT).show();
			Intent myIntent = new Intent(this, QuizActivity.class);
			startActivityForResult(myIntent, 0);
			break;

		case R.id.menu_refresh:
			Toast.makeText(this, "Fake refreshing...", Toast.LENGTH_SHORT)
					.show();
			getActionBarHelper().setRefreshActionItemState(true);
			getWindow().getDecorView().postDelayed(new Runnable() {
				@Override
				public void run() {
					getActionBarHelper().setRefreshActionItemState(false);
				}
			}, 1000);
			break;

		case R.id.menu_search:
			Toast.makeText(this, "Tapped search", Toast.LENGTH_SHORT).show();
			break;

		case R.id.menu_share:
			Toast.makeText(this, "Tapped share", Toast.LENGTH_SHORT).show();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}