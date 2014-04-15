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

public class TriviaGame implements Game {

	protected Session session = new Session();

	protected int record = 0;

	protected QuestionDatabase questionDatabase;

	protected List<Question> questions;

	protected List<Question> answeredQuestions;

	protected Question currentQuestion;

	protected boolean exited = false;
	
	public TriviaGame(QuestionDatabase questionDatabase,
			List<String> selectedCategories) {

		if ((selectedCategories != null) && (selectedCategories.size() > 0)) {
			this.questions = questionDatabase
					.getFilteredQuestions(selectedCategories);
		} else {
			this.questions = questionDatabase.getQuestions();
		}

		this.answeredQuestions = new ArrayList<Question>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nicolatesser.androidquiztemplate.quiz.Game#reset()
	 */
	@Override
	public void reset() {
		session = new Session();
		this.answeredQuestions = new ArrayList<Question>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nicolatesser.androidquiztemplate.quiz.Game#getSession()
	 */
	@Override
	public Session getSession() {
		return session;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nicolatesser.androidquiztemplate.quiz.Game#setSession(com.nicolatesser
	 * .androidquiztemplate.quiz.Session)
	 */
	@Override
	public void setSession(Session session) {
		this.session = session;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nicolatesser.androidquiztemplate.quiz.Game#getQuestion()
	 */
	@Override
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nicolatesser.androidquiztemplate.quiz.Game#checkAnswer(com.nicolatesser
	 * .androidquiztemplate.quiz.Question,
	 * com.nicolatesser.androidquiztemplate.quiz.Answer)
	 */
	@Override
	public boolean checkAnswer(Question question, Answer answer,
			boolean firstAttempt) {

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nicolatesser.androidquiztemplate.quiz.Game#checkAnswers(com.nicolatesser
	 * .androidquiztemplate.quiz.Question, java.util.List)
	 */
	@Override
	public boolean checkAnswers(Question question, List<Answer> answers,
			boolean firstAttempt) {

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nicolatesser.androidquiztemplate.quiz.Game#isNewRecord()
	 */
	@Override
	public boolean isNewRecord() {
		if (session.getConsecutiveAttempts() > this.record) {
			this.record = session.getConsecutiveAttempts();
			return true;
		} else {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nicolatesser.androidquiztemplate.quiz.Game#getRecord()
	 */
	@Override
	public int getRecord() {
		return this.record;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nicolatesser.androidquiztemplate.quiz.Game#setRecord(int)
	 */
	@Override
	public void setRecord(int record) {
		this.record = record;
	}

	@Override
	public boolean isExited() {
		return exited;
	}

	@Override
	public void setExited(boolean exited) {
		this.exited=exited;
	}

}
