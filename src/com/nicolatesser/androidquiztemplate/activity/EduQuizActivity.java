package com.nicolatesser.androidquiztemplate.activity;

import java.util.List;

import com.nicolatesser.androidquiztemplate.R;
import com.nicolatesser.androidquiztemplate.R.layout;
import com.nicolatesser.androidquiztemplate.R.menu;
import com.nicolatesser.androidquiztemplate.quiz.Answer;
import com.nicolatesser.androidquiztemplate.quiz.Game;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class EduQuizActivity extends QuizActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quiz);
	}
	

	public boolean checkAnswers(List<Answer> answers) {
		boolean checkAnswer = Game.getInstance()
				.checkAnswers(question, answers);
		if (checkAnswer) {
			showFeedback(checkAnswer, "Correct");
			displayNextQuestion();
		} else {
			showFeedback(checkAnswer, "Wrong");
		}
		return checkAnswer;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.one_player_quiz, menu);
		return true;
	}

}
