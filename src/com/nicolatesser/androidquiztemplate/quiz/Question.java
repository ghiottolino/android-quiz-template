/*
 * RoboQuiz     Copyright (C) 2011 Tobias C. Sutor
 * File:        Question.java
 * Author:      Tobias C. Sutor
 * E-Mail:      tobias@sutor-it.com
 * Web:         https://sourceforge.net/projects/roboquiz/
 * Created on:  May 27, 2011
 * Version:     $Rev$
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program;
 * if not, see <http://www.gnu.org/licenses/>.
 */

package com.nicolatesser.androidquiztemplate.quiz;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Tobias C. Sutor <tobias@sutor-it.com>
 */
public class Question {

	private List<Answer> answers;
	private List<Answer> correctAnswers;
	private List<String> categories;
	private String explanation;
	private String imageId;
	private boolean markedForReview = false;
	private Integer questionId;
	private final String questionText;
	private Map<String,String> questionAttributes = new HashMap<String, String>();

	/**
	 * @param categoryId
	 *            the category this question belongs to
	 * @param questionId
	 *            the unique question ID
	 * @param questionText
	 *            the actual question
	 * @param imageId
	 *            the id of the image for this question
	 * @param explanation		correctAnswers = new LinkedList<Answer>();
		for (final Answer a : this.answers) {
			if (a.isAnswerCorrect()) {
				correctAnswers.add(a);

			}
		}
	 *            an explanation why which answer is correct or not
	 */
	public Question(String questionText, List<Answer> answers) {
		this(null, questionText, null, null, null, answers);
	}
	
	public Question(String questionText, List<Answer> answers, List<String> categories) {
		this(null, questionText, null, null, categories, answers);
	}

	/**
	 * @param categoryId
	 *            the category this question belongs to
	 * @param questionId
	 *            the unique question ID
	 * @param questionText
	 *            the actual question
	 * @param imageId
	 *            the id of the image for this question
	 * @param explanation
	 *            an explanation why which answer is correct or not
	 */
	public Question(List<String> categories, final Integer questionId,
			final String questionText, final String imageId,
			final String explanation) {
		this(questionId, questionText, imageId, explanation, categories,
				new LinkedList<Answer>());
	}

	/**
	 * @param questionId
	 *            the unique question ID
	 * @param questionText
	 *            the actual question
	 * @param imageId
	 *            the id of the image for this question
	 * @param explanation
	 *            an explanation why which answer is correct or not
	 * @param categoryId
	 *            the category this question belongs to
	 * @param answers
	 *            an array of possible answers for this question
	 */
	@SuppressWarnings("boxing")
	public Question(final Integer questionId, final String questionText,
			final String imageId, final String explanation,
			final List<String> categories, final List<Answer> answers) {
		this.questionId = questionId;
		this.questionText = questionText;
		this.imageId = imageId;
		this.explanation = explanation;
		this.categories = categories;
		this.answers = answers;
		this.correctAnswers = new LinkedList<Answer>();
		for (final Answer a : this.answers) {
			if (a.isAnswerCorrect()) {
				this.correctAnswers.add(a);
			}
		}
	}

	/**
	 * @return the amount of all answers
	 */
	public int getAmountAnswers() {
		return this.answers.size();
	}

	/**
	 * @return the answers
	 */
	public List<Answer> getAnswers() {
		return this.answers;
	}

	/**
	 * @return the answers
	 */
	public List<Answer> getCorrectAnswers() {
		return correctAnswers;
	}

	/**
	 * @return the answers
	 */
	public Answer[] getAnswersAsArray() {
		return this.answers.toArray(new Answer[this.answers.size()]);
	}



	/**
	 * @return the explanation
	 */
	public final String getExplanation() {
		return this.explanation;
	}

	/**
	 * @return the imageId
	 */
	public final String getImageId() {
		return this.imageId;
	}

	/**
	 * @return the questionId
	 */
	public int getQuestionId() {
		return this.questionId;
	}

	/**
	 * @return the questionText
	 */
	public String getQuestionText() {
		return this.questionText;
	}

	/**
	 * @return the markedForReview
	 */
	public boolean isMarkedForReview() {
		return this.markedForReview;
	}

	/**
	 * @param explanation
	 *            the explanation to set
	 */
	public final void setExplanation(final String explanation) {
		this.explanation = explanation;
	}

	/**
	 * @param imageId
	 *            the imageId to set
	 */
	public final void setImageId(final String imageId) {
		this.imageId = imageId;
	}

	/**
	 * @param markedForReview
	 *            the markedForReview to set
	 */
	public void setMarkedForReview(final boolean markedForReview) {
		this.markedForReview = markedForReview;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public Map<String, String> getQuestionAttributes() {
		return questionAttributes;
	}

	public void setQuestionAttributes(Map<String, String> questionAttributes) {
		this.questionAttributes = questionAttributes;
	}
	
	public boolean hasMultipleCorrectAnswers()
	{
		if (getNumberOfCorrectAnswers()>1)
		{
			return true;
		}
		else 
		{
			return false;
		}
	}
	
	public int getNumberOfCorrectAnswers()
	{
		int numberOfCorrectAnswers=0;
		for (Answer answer: answers)
		{
			if (answer.isAnswerCorrect())
			{
				numberOfCorrectAnswers++;
			}
		}
		return numberOfCorrectAnswers;
	}

	
	
}
