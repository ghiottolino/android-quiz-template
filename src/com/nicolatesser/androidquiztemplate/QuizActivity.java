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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import com.nicolatesser.androidquiztemplate.actionbarcompat.ActionBarActivity;

import com.nicolatesser.androidquiztemplate.quiz.Answer;
import com.nicolatesser.androidquiztemplate.quiz.Game;
import com.nicolatesser.androidquiztemplate.quiz.Question;
import com.nicolatesser.androidquiztemplate.quiz.Session;
import com.nicolatesser.androidquiztemplate.quiz.Settings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

public class QuizActivity extends ActionBarActivity {

	public static final String RECORD_PREF_KEY = "RECORD";

	public static final String SESSION_PREF_KEY = "SESSION";
	
	public static final String SETTINGS_PREF_KEY = "SETTINGS";
	
	private TextView questionTextView;
	private TextView errorTextView;
	private TextView totalResultTextView;
	private TextView consecutiveResultTextView;
	private TextView recordTextView;
	private ListView mListView;
	private ListAdapter adapter;
	protected Question question;
	protected ImageView questionOptionButton;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quiz);

		// TODO : make a reset intent
		
		questionTextView = (TextView) findViewById(R.id.question);
		totalResultTextView = (TextView) findViewById(R.id.totalResult);
		consecutiveResultTextView = (TextView) findViewById(R.id.consecutiveResult);
		recordTextView = (TextView) findViewById(R.id.record);
		errorTextView = (TextView) findViewById(R.id.error);
		errorTextView.setVisibility(View.GONE);
		mListView = (ListView) findViewById(R.id.list);
		questionOptionButton = (ImageView) findViewById(R.id.question_option);
		
		// TODO : load session
		initGame();
		displayNextQuestion();
	}

	@Override
	public void onResume() {
		// TODO : load session
		super.onResume();
		loadSession();
		loadSettings();
	}

	@Override
	public void onPause() {
		// TODO : save session
		super.onPause();
		Session session = Game.getInstance().getSession();
		saveStringFieldInPreferences(SESSION_PREF_KEY, session.toString());
	}
	
	public void setHomeActivity(Activity activity)
	{
		// TODO : set the home activity for the action bar
	}

	public void displayNextQuestion()
	{
		this.question = Game.getInstance().getQuestion();
		displayQuestion(question);
		showFeedback(Game.getInstance().getSession());
		errorTextView.setVisibility(View.INVISIBLE);
	}
	
	public void displayQuestion(final Question question) {
		
		String questionText=question.getQuestionText();
		if (question.hasMultipleCorrectAnswers())
		{
			questionText+= "("+question.getNumberOfCorrectAnswers()+")";
		}
		
		questionTextView.setText(questionText);
		OnClickListener listener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				final Button button = (Button) v;
				if (!question.hasMultipleCorrectAnswers())
				{
					Answer answer = new Answer(button.getText().toString(), true);
					boolean checkAnswer = checkAnswers(Arrays.asList(answer));
					if (!checkAnswer) {
						button.setEnabled(false);
						Handler handler = new Handler();
						handler.postDelayed(new Runnable() {
							public void run() {
								button.setSelected(true);
							}
						}, 1000);
					}
				}
				
				else
				{
					if (button.isSelected())
					{
						button.setSelected(false);	
					}
					else
					{
						button.setSelected(true);
					}
									
					ListView listView = (ListView)v.getParent().getParent();
					int childCount = listView.getChildCount();
					List<Answer> answers = new Vector<Answer>();
					List<Button> answersButtons = new Vector<Button>();
					for (int i=0;i<childCount;i++)
					{
						View linearLayoutView = listView.getChildAt(i);
						LinearLayout linearLayout = (LinearLayout) linearLayoutView;
						
						Button otherButton = (Button)linearLayout.getChildAt(0);
						
						if (otherButton.isSelected())
						{
							Answer answer = new Answer(otherButton.getText().toString(), true);					
							answers.add(answer);
							answersButtons.add(otherButton);
						}
					}
					if (question.getNumberOfCorrectAnswers()==answers.size())
					{
						boolean checkAnswer = checkAnswers(answers);
						if (!checkAnswer)
						{
							for (final Button answerButton : answersButtons)
							{
								answerButton.setEnabled(false);
								answerButton.setSelected(false);
								
								Handler handler = new Handler();
								handler.postDelayed(new Runnable() {
									public void run() {
										answerButton.setEnabled(true);
									}
								}, 1000);
							}
							
//							
//							for (int i=0;i<childCount;i++)
//							{
//								View linearLayoutView = listView.getChildAt(i);
//								LinearLayout linearLayout = (LinearLayout) linearLayoutView;
//								Button otherButton = (Button)linearLayout.getChildAt(0);
//								otherButton.setSelected(false);
//							}
						}
					}
				}
				
				
			}
		};
		//unsort answers
		List<Answer> answers = question.getAnswers();
		Collections.shuffle(answers);
		adapter = new AnswerAdapter(this, R.id.answer, answers,
				listener);
		mListView.setAdapter(adapter);
	}

	public boolean checkAnswers(List<Answer> answers) {
		boolean checkAnswer = Game.getInstance().checkAnswers(question, answers);
		if (checkAnswer) {
			if (Game.getInstance().isNewRecord()) {
				int currentRecord = Game.getInstance().getSession().getConsecutiveAttempts();
				saveIntFieldInPreferences(RECORD_PREF_KEY, currentRecord);
				showTextToClipboardNotification("Congratulations, you set a new record: "
						+ currentRecord);
			}
			displayNextQuestion();
		} else {
			errorTextView.setVisibility(View.VISIBLE);
		}
		showFeedback(Game.getInstance().getSession());
		return checkAnswer;
	}

	public void showFeedback(Session session) {

		totalResultTextView.setText("total: "
				+ session.getCorrectAttempts().toString() + "/"
				+ session.getTotalAttempts().toString());
		consecutiveResultTextView.setText("consecutive: "
				+ session.getConsecutiveAttempts().toString());
		recordTextView.setText("record: " + Game.getInstance().getRecord());
	}

	public void reset() {
		saveIntFieldInPreferences(RECORD_PREF_KEY, 0);
		Session newSession = new Session();
		Game.getInstance().setRecord(0);
		Game.getInstance().setSession(newSession);
		displayNextQuestion();
	}

	public void initGame() {
		Integer record = getIntFieldInPreferences(RECORD_PREF_KEY);
		Game.getInstance().setRecord(record);
		loadSession();
		loadSettings();
	}
	
	protected void loadSession()
	{
		String serializedSession = getStringFieldInPreferences(SESSION_PREF_KEY);
		Game.getInstance().setSession(new Session(serializedSession));
	}
	
	protected void loadSettings()
	{
		String serializedSettings = getStringFieldInPreferences(SETTINGS_PREF_KEY);
		Game.getInstance().setSettings(new Settings(serializedSettings));
	}
	
	

	protected Integer getIntFieldInPreferences(String fieldName) {
		String settingPrefixName = getApplicationContext().getPackageName();
		SharedPreferences settings = getSharedPreferences(settingPrefixName, 0);
		int field = settings.getInt(fieldName, 0);
		return field;
	}

	protected String getStringFieldInPreferences(String fieldName) {
		String settingPrefixName = getApplicationContext().getPackageName();
		SharedPreferences settings = getSharedPreferences(settingPrefixName, 0);
		String field = settings.getString(fieldName, "");
		return field;
	}

	protected void saveIntFieldInPreferences(String fieldName, Integer n) {

		String settingPrefixName = getApplicationContext().getPackageName();
		SharedPreferences settings = getSharedPreferences(settingPrefixName, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(fieldName, n);

		// Commit the edits!
		boolean commit = editor.commit();
	}

	protected void saveStringFieldInPreferences(String fieldName, String value) {

		String settingPrefixName = getApplicationContext().getPackageName();
		SharedPreferences settings = getSharedPreferences(settingPrefixName, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(fieldName, value);

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
		int itemId = item.getItemId();

		// TODO : menu in a helper class
		
		if (itemId == android.R.id.home) {
			// do nothing
		} 
		else if (itemId == R.id.menu_other_apps) {

			Intent goToMarket; 
			goToMarket = new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub:\"Nicola Tesser\"")); 
			startActivity(goToMarket); 
		}
		else if (itemId == R.id.menu_reset) {
			reset();
		}
		else if (itemId == R.id.menu_menu) {
			this.openOptionsMenu();
		}
		return super.onOptionsItemSelected(item);
	}

}