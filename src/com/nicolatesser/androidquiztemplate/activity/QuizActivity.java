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

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class QuizActivity extends Activity {

	public static final String SESSION_PREF_KEY = "SESSION";

	protected TextView questionTextView;
	protected TextView feedbacktTextView;
	protected ListView mListView;
	protected ListAdapter adapter;
	protected Question question;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quiz);
		initViews();
	}
	
	
	public void initViews() {
		questionTextView = (TextView) findViewById(R.id.question);
		feedbacktTextView = (TextView) findViewById(R.id.feedback);
		mListView = (ListView) findViewById(R.id.list);

	}

	public void displayNextQuestion() {
		this.question = Game.getInstance().getQuestion();
		displayQuestion(question);
		showFeedback(Game.getInstance().getSession());
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
						button.setTextColor(getResources().getColor(R.color.red));
						Handler handler = new Handler();
						handler.postDelayed(new Runnable() {
							public void run() {
								button.setSelected(true);
							}
						}, 1000);
					}
					else {
						button.setTextColor(getResources().getColor(R.color.green));
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
								answerButton.setTextColor(getResources().getColor(R.color.red));
								
								Handler handler = new Handler();
								handler.postDelayed(new Runnable() {
									public void run() {
										answerButton.setEnabled(true);
									}
								}, 1000);
							}
						}
						else {
							for (final Button answerButton : answersButtons) {
								answerButton.setTextColor(getResources().getColor(R.color.green));
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
			showFeedback(checkAnswer,"Correct");
			displayNextQuestion();
		} else {
			showFeedback(checkAnswer,"Wrong");
			displayNextQuestion();		}
		return checkAnswer;
	}

	public void showFeedback(boolean b, String string) {
		if (b) {
			feedbacktTextView.setText("Correct");		
			feedbacktTextView.setTextColor(getResources().getColor(R.color.green));
		}
		
		else {
			feedbacktTextView.setText("Wrong");	
			feedbacktTextView.setTextColor(getResources().getColor(R.color.red));

		}	
	}

	public void showFeedback(Session session) {

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.one_player_quiz, menu);
		return true;
	}

}
