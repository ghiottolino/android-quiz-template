package com.nicolatesser.androidquiztemplate.quiz;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

// TODO : use reflexion to add to a question objects like "TrackInfo" ?

public class Question {

	private List<Answer> answers;
	private List<Answer> correctAnswers;
	private int numberOfCorrectAnswers = 0;
	private List<String> categories;
	private String explanation;
	private String questionText;
	private String imageUri;
	private Map<String, String> questionAttributes = new HashMap<String, String>();
	
	//if for each quesiton there are some follow up, related, sub-questions, use this field.
	private List<Question> subQuestions;
	

	public Question(String questionText, List<Answer> answers,
			List<String> categories) {
		super();
		this.answers = answers;
		this.categories = categories;
		this.questionText = questionText;
		this.correctAnswers = new LinkedList<Answer>();
		for (final Answer a : this.answers) {
			if (a.isCorrect()) {
				this.correctAnswers.add(a);
				numberOfCorrectAnswers++;
			}
		}
	}

	public int getNumberOfAnswers() {
		return this.answers.size();
	}

	public List<Answer> getAnswers() {
		return this.answers;
	}

	public List<Answer> getCorrectAnswers() {
		return correctAnswers;
	}

	public Answer[] getAnswersAsArray() {
		return this.answers.toArray(new Answer[this.answers.size()]);
	}

	public final String getExplanation() {
		return this.explanation;
	}

	public String getQuestionText() {
		return this.questionText;
	}

	public final void setExplanation(final String explanation) {
		this.explanation = explanation;
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

	public boolean hasMultipleCorrectAnswers() {
		if (numberOfCorrectAnswers > 1) {
			return true;
		} else {
			return false;
		}
	}

	public int getNumberOfCorrectAnswers() {
		return numberOfCorrectAnswers;
	}

	public String getImageUri() {
		return imageUri;
	}

	public void setImageUri(String imageUri) {
		this.imageUri = imageUri;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public List<Question> getSubQuestions() {
		return subQuestions;
	}

	public void setSubQuestions(List<Question> subQuestions) {
		this.subQuestions = subQuestions;
	}
	
	

}
