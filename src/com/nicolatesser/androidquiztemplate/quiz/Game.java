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

	private Session session = new Session();

	private QuestionDatabase questionDatabase;

	private List<Question> questions;

	private List<Question> answeredQuestions;

	private List<Question> recentlyWronglyAnsweredQuestions;
	
	private Question currentQuestion;

	private Random rg = new Random();

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
		this.recentlyWronglyAnsweredQuestions = new ArrayList<Question>();
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public Question getQuestion() {
		Question question = getRandomQuestionFromQuestionList(questions);
		return question;
	}

	/**
	 * Return a random question from the recentlyWronglyAnsweredQuestions list
	 * 
	 * @return
	 */
	private Question getRandomQuestionFromQuestionList(List<Question> questions) {
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
		} else {
			this.session.setConsecutiveAttempts(0);
			if (!recentlyWronglyAnsweredQuestions.contains(question)) {
				recentlyWronglyAnsweredQuestions.add(question);
			}
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
		} else {
			this.session.setConsecutiveAttempts(0);
			if (!recentlyWronglyAnsweredQuestions.contains(question)) {
				recentlyWronglyAnsweredQuestions.add(question);
			}
		}
		return correct;
	}

}
