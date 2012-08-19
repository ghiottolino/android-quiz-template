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

import com.nicolatesser.androidquiztemplate.quiz.Answer;
import com.nicolatesser.androidquiztemplate.quiz.Game;
import com.nicolatesser.androidquiztemplate.quiz.Question;
import com.nicolatesser.androidquiztemplate.quiz.Session;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.opengl.Visibility;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

public class QuizActivity extends ActionBarActivity {

	public static final String RECORD_PREF_KEY = "RECORD";

	public static final String PREFS_NAME = "ANDROID_QUIZ_TEMPLATE";

	private TextView questionTextView;
	private TextView errorTextView;
	private TextView totalResultTextView;
	private TextView consecutiveResultTextView;
	private TextView recordTextView;
	private ListView mListView;
	private ListAdapter adapter;
	private Game game;
	private Question question;

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
		errorTextView.setVisibility(View.GONE);
		mListView = (ListView) findViewById(R.id.list);

		initGame();
		this.question = this.game.getQuestion();
		displayQuestion(question);
		showFeedback(game.getSession());
	}

	public void displayQuestion(final Question question) {
		questionTextView.setText(question.getQuestionText());
		OnClickListener listener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				Button button = (Button) v;
				Answer answer = new Answer(button.getText().toString(), true);
				boolean checkAnswer = checkAnswer(answer);
				if (!checkAnswer) {
					button.setEnabled(false);
				}

			}
		};
		adapter = new AnswerAdapter(this, R.id.answer, question.getAnswers(),
				listener);
		mListView.setAdapter(adapter);
	}

	public boolean checkAnswer(Answer answer) {
		boolean checkAnswer = game.checkAnswer(question, answer);
		if (checkAnswer) {
			if (game.isNewRecord()) {
				int currentRecord = game.getSession().getConsecutiveAttempts();
				saveFieldInPreferences(RECORD_PREF_KEY, currentRecord);
				showTextToClipboardNotification("Congratulations, you set a new record: "
						+ currentRecord);
			}
			question = game.getQuestion();
			displayQuestion(question);
			errorTextView.setVisibility(View.GONE);
		} else {
			errorTextView.setVisibility(View.VISIBLE);
		}
		showFeedback(this.game.getSession());
		return checkAnswer;
	}

	public void showFeedback(Session session) {

		totalResultTextView.setText("total: "
				+ session.getCorrectAttempts().toString() + "/"
				+ session.getTotalAttempts().toString());
		consecutiveResultTextView.setText("consecutive: "
				+ session.getConsecutiveAttempts().toString());
		recordTextView.setText("record: " + this.game.getRecord());
	}
	
	public void reset() {
		saveFieldInPreferences(RECORD_PREF_KEY, 0);
		Session newSession = new Session();
		game.setRecord(0);
		game.setSession(newSession);
		this.question = this.game.getQuestion();
		displayQuestion(question);
		showFeedback(game.getSession());	
	}
		
	public void initGame() {
		List<Question> questions = new Vector<Question>();
		List<Answer> answers1 = new Vector<Answer>();
		List<Answer> answers2 = new Vector<Answer>();
		List<Answer> answers3 = new Vector<Answer>();
		List<Answer> answers4 = new Vector<Answer>();

		Answer answer1false = new Answer("answer 1", false);
		Answer answer2false = new Answer("answer 2", false);
		Answer answer3false = new Answer("answer 3", false);
		Answer answer4false = new Answer("answer 4", false);
		Answer answer1true = new Answer("answer 1", true);
		Answer answer2true = new Answer("answer 2", true);
		Answer answer3true = new Answer("answer 3", true);
		Answer answer4true = new Answer("answer 4", true);

		answers1.add(answer1true);
		answers1.add(answer2false);
		answers1.add(answer3false);

		answers2.add(answer1false);
		answers2.add(answer2true);
		answers2.add(answer3false);
		answers2.add(answer4false);

		answers3.add(answer1false);
		answers3.add(answer2true);
		answers3.add(answer3true);

		answers4.add(answer1false);
		answers4.add(answer2true);
		answers4.add(answer3false);
		answers4.add(answer4true);

		Question question1 = new Question("question 1", answers1);
		Question question2 = new Question("question 2", answers2);
		Question question3 = new Question("question 3", answers3);
		Question question4 = new Question("question 4", answers4);

		questions.add(question1);
		questions.add(question2);
		questions.add(question3);
		questions.add(question4);

		Integer record = getFieldInPreferences(RECORD_PREF_KEY);
		this.game = new Game(questions, record);
	}

	protected Integer getFieldInPreferences(String fieldName) {

		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		int record = settings.getInt(fieldName, 0);
		return record;
	}

	protected void saveFieldInPreferences(String fieldName, Integer n) {

		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(fieldName, n);

		// Commit the edits!
		boolean commit = editor.commit();
	}

	protected void showTextToClipboardNotification(String text) {
		Context context = getApplicationContext();
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();

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

		case R.id.menu_menu:
			this.openOptionsMenu();
			Toast.makeText(this, "Tapped menu", Toast.LENGTH_SHORT).show();
			break;

		case R.id.menu_reset:
			reset();
			break;
		}

		return super.onOptionsItemSelected(item);
	}

}