package com.nicolatesser.androidquiztemplate.quiz;

import java.util.List;

public interface Game {

	public abstract void reset();

	public abstract Session getSession();

	public abstract void setSession(Session session);

	public abstract Question getQuestion();

	/**
	 * Check the answer for a given question
	 * 
	 * @param question
	 * @param answer
	 * @return
	 */
	public abstract boolean checkAnswer(Question question, Answer answer,
			boolean firstAttempt);

	public abstract boolean checkAnswers(Question question,
			List<Answer> answer, boolean firstAttempt);

	public abstract boolean isNewRecord();

	public abstract int getRecord();

	public abstract void setRecord(int record);
	
	public abstract boolean isExited();
	
	public abstract void setExited(boolean exited);
	

}