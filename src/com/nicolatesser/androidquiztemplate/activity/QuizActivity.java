package com.nicolatesser.androidquiztemplate.activity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import com.nicolatesser.androidquiztemplate.R;
import com.nicolatesser.androidquiztemplate.R.layout;
import com.nicolatesser.androidquiztemplate.R.menu;
import com.nicolatesser.androidquiztemplate.quiz.Answer;
import com.nicolatesser.androidquiztemplate.quiz.Game;
import com.nicolatesser.androidquiztemplate.quiz.Question;
import com.nicolatesser.androidquiztemplate.quiz.QuestionDatabase;
import com.nicolatesser.androidquiztemplate.quiz.Session;

import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class QuizActivity extends Activity {

	public static final String RECORD_PREF_KEY = "RECORD";

	public static final String SESSION_PREF_KEY = "SESSION";

	protected TextView questionTextView;
	protected RelativeLayout lastQuestionNeutralView;
	protected RelativeLayout lastQuestionOkView;
	protected RelativeLayout lastQuestionWrongView;
	protected TextView consecutiveTextView;
	protected TextView recordTextView;
	
	
	protected ListView mListView;
	protected ListAdapter adapter;
	protected Question question;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quiz);
		
		initViews();
	}

	@Override
	protected void onResume() {
		super.onResume();
		loadSession();
		loadRecord();
		if (this.question == null) {
			displayNextQuestion();
		}

	}


	@Override
	public void onPause() {
		super.onPause();
		Session session = Game.getInstance().getSession();
		saveStringFieldInPreferences(SESSION_PREF_KEY, session.toString());
		int record = Game.getInstance().getRecord();
		saveIntFieldInPreferences(RECORD_PREF_KEY, record);
	}
	

	public void initViews() {
		questionTextView = (TextView) findViewById(R.id.question);
		lastQuestionNeutralView = (RelativeLayout) findViewById(R.id.lastQuestionNeutral);
		lastQuestionOkView = (RelativeLayout) findViewById(R.id.lastQuestionOk);
		lastQuestionWrongView = (RelativeLayout) findViewById(R.id.lastQuestionWrong);
		lastQuestionNeutralView.setVisibility(View.VISIBLE);
		lastQuestionOkView.setVisibility(View.GONE);
		lastQuestionWrongView.setVisibility(View.GONE);
		
		consecutiveTextView= (TextView) findViewById(R.id.consecutiveQuestionsText);
		recordTextView= (TextView) findViewById(R.id.recordText);
		
		mListView = (ListView) findViewById(R.id.list);

	}

	public void displayNextQuestion() {
		this.question = Game.getInstance().getQuestion();

		if (this.question == null) {
			endQuiz();
		} else {

			Handler handler = new Handler();
			handler.postDelayed(new Runnable() {
				public void run() {
					displayQuestion(question);
					showFeedback(Game.getInstance().getSession(), Game.getInstance().getRecord());
				}
			}, 500);

		}

	}

	public void endQuiz() {
		Intent statIntent = new Intent(this, EndOfGameActivity.class);
		startActivity(statIntent);
	}

	public void displayQuestion(final Question question) {

		String questionText = question.getQuestionText();
		if (question.hasMultipleCorrectAnswers()) {
			questionText += "(" + question.getNumberOfCorrectAnswers() + ")";
		}

		questionTextView.setText(questionText);
		OnClickListener listener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				final Button button = (Button) v;
				if (!question.hasMultipleCorrectAnswers()) {
					Answer answer = new Answer(button.getText().toString(),
							true);
					boolean checkAnswer = checkAnswers(Arrays.asList(answer));
					if (!checkAnswer) {
						button.setEnabled(false);
						button.setTextColor(getResources()
								.getColor(R.color.red));
						Handler handler = new Handler();
						handler.postDelayed(new Runnable() {
							public void run() {
								button.setSelected(true);
							}
						}, 1000);
					} else {
						button.setTextColor(getResources().getColor(
								R.color.green));
					}
				}

				else {
					if (button.isSelected()) {
						button.setSelected(false);
					} else {
						button.setSelected(true);
					}

					ListView listView = (ListView) v.getParent().getParent();
					int childCount = listView.getChildCount();
					List<Answer> answers = new Vector<Answer>();
					List<Button> answersButtons = new Vector<Button>();
					for (int i = 0; i < childCount; i++) {
						View linearLayoutView = listView.getChildAt(i);
						LinearLayout linearLayout = (LinearLayout) linearLayoutView;

						Button otherButton = (Button) linearLayout
								.getChildAt(0);

						if (otherButton.isSelected()) {
							Answer answer = new Answer(otherButton.getText()
									.toString(), true);
							answers.add(answer);
							answersButtons.add(otherButton);
						}
					}
					if (question.getNumberOfCorrectAnswers() == answers.size()) {
						boolean checkAnswer = checkAnswers(answers);
						if (!checkAnswer) {
							for (final Button answerButton : answersButtons) {
								answerButton.setEnabled(false);
								answerButton.setSelected(false);
								answerButton.setTextColor(getResources()
										.getColor(R.color.red));

								Handler handler = new Handler();
								handler.postDelayed(new Runnable() {
									public void run() {
										answerButton.setEnabled(true);
									}
								}, 1000);
							}
						} else {
							for (final Button answerButton : answersButtons) {
								answerButton.setTextColor(getResources()
										.getColor(R.color.green));
							}
						}
					}
				}

			}
		};
		// unsort answers
		List<Answer> answers = question.getAnswers();
		Collections.shuffle(answers);
		adapter = new AnswerAdapter(this, R.id.answer, answers, listener);
		mListView.setAdapter(adapter);
	}

	public boolean checkAnswers(List<Answer> answers) {
		boolean checkAnswer = Game.getInstance()
				.checkAnswers(question, answers);
		if (checkAnswer) {
			showFeedback(checkAnswer, "Correct");
			displayNextQuestion();
		} else {
			showFeedback(checkAnswer, "Wrong");
			displayNextQuestion();
		}
		return checkAnswer;
	}

	public void showFeedback(boolean b, String string) {
		if (b) {
			
			lastQuestionNeutralView.setVisibility(View.GONE);
			lastQuestionOkView.setVisibility(View.VISIBLE);
			lastQuestionWrongView.setVisibility(View.GONE);
			
			
		}

		else {
			
			lastQuestionNeutralView.setVisibility(View.GONE);
			lastQuestionOkView.setVisibility(View.GONE);
			lastQuestionWrongView.setVisibility(View.VISIBLE);


		}
	}

	public void showFeedback(Session session, int record) {
		consecutiveTextView.setText(Integer.toString(session.getConsecutiveAttempts()));
		consecutiveTextView.setText(Integer.toString(record));
		
	}

	
	protected void loadRecord()
	{
		int record = getIntFieldInPreferences(RECORD_PREF_KEY);
		Game.getInstance().setRecord(record);
	}
	
	protected void loadSession()
	{
		String serializedSession = getStringFieldInPreferences(SESSION_PREF_KEY);
		Game.getInstance().setSession(new Session(serializedSession));
	}
	
	protected Integer getIntFieldInPreferences(String fieldName) {
		String settingPrefixName = getApplicationContext().getPackageName();
		SharedPreferences settings = getSharedPreferences(settingPrefixName, 0);
		int field = 0;
		try{
			field = settings.getInt(fieldName, 0);
		}
		catch(ClassCastException e) {
			//do nothing
		}
		
		
		return field;
	}
	

	protected String getStringFieldInPreferences(String fieldName) {
		String settingPrefixName = getApplicationContext().getPackageName();
		SharedPreferences settings = getSharedPreferences(settingPrefixName, 0);
		String field = "";
		try{
			field = settings.getString(fieldName, "");
		}
		catch(ClassCastException e) {
			//do nothing
		}
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.one_player_quiz, menu);
		return true;
	}

}
