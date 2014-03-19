package com.nicolatesser.androidquiztemplate.quiz;

import java.util.List;

/**
 * A ordered Group of questions. This questions should/could be presented sequentially to the user.
 */
public class QuestionGroup {
	
	private List<Question> questions;

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
	
	

}
