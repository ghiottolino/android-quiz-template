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

public class Game {

	protected Session session = new Session();
	
	protected int record = 0;

	protected QuestionDatabase questionDatabase;

	protected List<Question> questions;

	protected List<Question> answeredQuestions;

	protected Question currentQuestion;

	public static Game instance = null;

	public static Game getInstance() {
		if (instance == null) {
			throw new IllegalAccessError();
		}
		return instance;
	}

	public static void setInstance(Game game) {
		instance = game;
	}

	public Game(QuestionDatabase questionDatabase) {
		this.questions = questionDatabase.getQuestions();
		this.answeredQuestions = new ArrayList<Question>();
	}
	
	public void reset() {
		session = new Session();
		this.answeredQuestions = new ArrayList<Question>();
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}


	public Question getQuestion() {

		List<Question> interserction = new ArrayList<Question>();
		interserction.addAll(questions);
		interserction.removeAll(answeredQuestions);
		Question question = getRandomQuestionFromQuestionList(interserction);
		this.currentQuestion = question;
		return question;
	}

	/**
	 * Return a random question from the list
	 * 
	 * @return
	 */
	protected Question getRandomQuestionFromQuestionList(
			List<Question> questions) {
		Collections.shuffle(questions);
		if (questions.size() > 0) {
			Question question = questions.get(0);
			return question;
		} else {
			return null;
		}
	}

	/**
	 * Check the answer for a given question
	 * 
	 * @param question
	 * @param answer
	 * @return
	 */
	public boolean checkAnswer(Question question, Answer answer) {

		boolean correct = question.getCorrectAnswers().contains(answer);

		if (!answeredQuestions.contains(question)) {
			answeredQuestions.add(question);
		}
		this.session.setTotalAttempts(this.session.getTotalAttempts() + 1);

		if (correct) {
			this.session
					.setCorrectAttempts(this.session.getCorrectAttempts() + 1);
			this.session.setConsecutiveAttempts(this.session
					.getConsecutiveAttempts() + 1);
			updateRecord();
		} else {
			this.session.setConsecutiveAttempts(0);

		}
		return correct;
	}

	public boolean checkAnswers(Question question, List<Answer> answers) {

		boolean correct = question.getCorrectAnswers().containsAll(answers);

		if (!answeredQuestions.contains(question)) {
			answeredQuestions.add(question);
		}
		this.session.setTotalAttempts(this.session.getTotalAttempts() + 1);

		if (correct) {
			this.session
					.setCorrectAttempts(this.session.getCorrectAttempts() + 1);
			this.session.setConsecutiveAttempts(this.session
					.getConsecutiveAttempts() + 1);
			updateRecord();
		} else {
			this.session.setConsecutiveAttempts(0);

		}
		return correct;
	}
	
	public void updateRecord() {
		if (session.getConsecutiveAttempts() > this.record) {
			this.record = session.getConsecutiveAttempts();
		} 
	}

	public boolean isNewRecord() {
		if (session.getConsecutiveAttempts() > this.record) {
			this.record = session.getConsecutiveAttempts();
			return true;
		} else {
			return false;
		}
	}

	public int getRecord() {
		return this.record;
	}

	public void setRecord(int record) {
		this.record = record;
	}

}
