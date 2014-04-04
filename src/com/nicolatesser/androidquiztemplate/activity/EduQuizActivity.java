package com.nicolatesser.androidquiztemplate.activity;

import java.util.List;

import android.os.Bundle;
import android.view.Menu;

import com.nicolatesser.androidquiztemplate.R;
import com.nicolatesser.androidquiztemplate.quiz.Answer;
import com.nicolatesser.androidquiztemplate.quiz.Game;
import com.nicolatesser.androidquiztemplate.quiz.GameHolder;

public class EduQuizActivity extends QuizActivity {

	
	boolean firstAttempt = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quiz);
		ActionBarHelper.setUpActionBar(this);

	}
	
	@Override
	public void displayNextQuestion() {
		super.displayNextQuestion();
		firstAttempt=true;
	}
	
	@Override
	public boolean checkAnswers(List<Answer> answers) {
		Game game = GameHolder.getInstance();
		boolean checkAnswer = game
				.checkAnswers(question, answers, firstAttempt);
		firstAttempt=false;
		
		if (checkAnswer) {
			showFeedback(checkAnswer, "Correct");
			displayNextQuestion();
		} else {
			showFeedback(checkAnswer, "Wrong");
		}
		return checkAnswer;
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}

}
