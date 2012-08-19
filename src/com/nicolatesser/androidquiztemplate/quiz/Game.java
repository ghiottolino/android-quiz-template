package com.nicolatesser.androidquiztemplate.quiz;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Game {
	
	private int record;

	private Session session;
	
	private List<Question> questions;

	private List<Question> recentlyWronglyAnsweredQuestions;

	private static final Integer RECENTLY_WRONGLY_ANSWERED_QUESTIONS_SIZE = 10;
	
	private Random rg = new Random();

	
	public Game(List<Question> questions, Integer record)
	{
		// TODO: load question, etc...
		this.questions=questions;
		this.recentlyWronglyAnsweredQuestions = new LinkedList<Question>();
		this.session=new Session();
		if (record==null)
		{
			this.record=0;
		}
		else
		{
			this.record=record;
		}
	}
	
	
	public List<Question> getQuestions() {
		return questions;
	}



	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
	
	

	public Session getSession() {
		return session;
	}


	public void setSession(Session session) {
		this.session = session;
	}


	public Question getQuestion()
	{
		Question question;
		// if there are more than 10 questions in the recentlyWronglyAnsweredQuestions list
		// then retrieves one of that question
		if (shouldChooseFromWrongAnsweredQuestions()) {
			question = getRandomQuestionFromQuestionList(this.recentlyWronglyAnsweredQuestions);
			recentlyWronglyAnsweredQuestions.remove(question);
		} else {
			question = getRandomQuestionFromQuestionList(this.questions);
		}
		return question;
	}
	
	/**
	 * Return a random question from  the recentlyWronglyAnsweredQuestions list
	 * @return
	 */
	private Question getRandomQuestionFromQuestionList(List<Question> questions) {
		Collections.shuffle(questions);
		// TODO : make more robust, check for null....
		Question question = questions.get(0);
		return question;
	}

	/**
	 * Decide if return a question of the wrongly answered questions according to the size of the recentlyWronglyAnsweredQuestions list
	 * @return
	 */
	private boolean shouldChooseFromWrongAnsweredQuestions() {
		if  ((recentlyWronglyAnsweredQuestions==null)||(recentlyWronglyAnsweredQuestions.size()==0))
		{
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
	 * @param question
	 * @param answer
	 * @return
	 */
	public boolean checkAnswer(Question question, Answer answer) {
		
		boolean correct = question.getAnswers().contains(answer);
		this.session.setTotalAttempts(this.session.getTotalAttempts()+1);

		if (correct) {
			this.session.setCorrectAttempts(this.session.getCorrectAttempts()+1);
			this.session.setConsecutiveAttempts(this.session.getConsecutiveAttempts()+1);
		} else {
			this.session.setConsecutiveAttempts(0);
			if (!recentlyWronglyAnsweredQuestions.contains(question))
			{
				recentlyWronglyAnsweredQuestions.add(question);
			}
		}
		return correct;
	}
	
	public boolean isNewRecord()
	{
		if (session.getConsecutiveAttempts()>this.record)
		{
			this.record=session.getConsecutiveAttempts();
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public int getRecord()
	{
		return this.record;
	}

	

	
	
}
