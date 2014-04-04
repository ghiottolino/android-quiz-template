package com.nicolatesser.androidquiztemplate.quiz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

public class EduGame extends Game {

	private List<Question> recentlyWronglyAnsweredQuestions;

	private static final Integer RECENTLY_WRONGLY_ANSWERED_QUESTIONS_SIZE = 10;

	private Random rg = new Random();

	public static EduGame instance = null;

	public static EduGame getInstance() {
		if (instance == null) {
			throw new IllegalAccessError();
		}
		return instance;
	}

	public static void setInstance(EduGame game) {
		instance = game;
	}

	public EduGame(QuestionDatabase questionDatabase) {
		super(questionDatabase);
		this.recentlyWronglyAnsweredQuestions = new LinkedList<Question>();

	}

	public Question getQuestion() {
		Question question = null;

		// if there are more than 10 questions in the
		// recentlyWronglyAnsweredQuestions list
		// then retrieves one of that question
		if (shouldChooseFromWrongAnsweredQuestions()) {
			question = getRandomQuestionFromQuestionList(this.recentlyWronglyAnsweredQuestions);
			recentlyWronglyAnsweredQuestions.remove(question);
		} else {
			List<Question> allQuestions = new ArrayList<Question>();
			allQuestions.addAll(questions);
			question = getRandomQuestionFromQuestionList(allQuestions);

		}
		this.currentQuestion = question;
		return question;
	}

	/**
	 * Decide if return a question of the wrongly answered questions according
	 * to the size of the recentlyWronglyAnsweredQuestions list
	 * 
	 * @return
	 */
	private boolean shouldChooseFromWrongAnsweredQuestions() {
		if ((recentlyWronglyAnsweredQuestions == null)
				|| (recentlyWronglyAnsweredQuestions.size() == 0)) {
			return false;
		}

		if (recentlyWronglyAnsweredQuestions.size() > RECENTLY_WRONGLY_ANSWERED_QUESTIONS_SIZE) {
			return true;
		}

		int random = rg.nextInt(RECENTLY_WRONGLY_ANSWERED_QUESTIONS_SIZE);

		if (random < recentlyWronglyAnsweredQuestions.size()) {
			return true;
		} else {
			return false;
		}
	}


	/**
	 * Check the answer for a given question
	 * 
	 * @param question
	 * @param answer
	 * @return
	 */
	public boolean checkAnswer(Question question, Answer answer, boolean firstAttempt) {

		boolean correct = question.getCorrectAnswers().contains(answer);

		
		this.session.setTotalAttempts(this.session.getTotalAttempts() + 1);

		if (correct && firstAttempt) {
			//after ther first attempt, do not count it as correct answer for the statistics
			this.session
					.setCorrectAttempts(this.session.getCorrectAttempts() + 1);
			this.session.setConsecutiveAttempts(this.session
					.getConsecutiveAttempts() + 1);
			updateRecord();
		} else {
			this.session.setConsecutiveAttempts(0);
			if (!recentlyWronglyAnsweredQuestions.contains(question)) {
				recentlyWronglyAnsweredQuestions.add(question);
			}
		}
		return correct;
	}

	public boolean checkAnswers(Question question, List<Answer> answers, boolean firstAttempt) {

		boolean correct = question.getCorrectAnswers().containsAll(answers);

		this.session.setTotalAttempts(this.session.getTotalAttempts() + 1);

		if (correct && firstAttempt) {
			//after ther first attempt, do not count it as correct answer for the statistics

			this.session
					.setCorrectAttempts(this.session.getCorrectAttempts() + 1);
			this.session.setConsecutiveAttempts(this.session
					.getConsecutiveAttempts() + 1);
			updateRecord();
		} else {
			this.session.setConsecutiveAttempts(0);
			if (!recentlyWronglyAnsweredQuestions.contains(question)) {
				recentlyWronglyAnsweredQuestions.add(question);
			}
		}
		return correct;
	}



}
