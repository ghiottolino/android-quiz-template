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
// TODO: implement images
public class Question  {

	private List<Answer> answers;
    private Integer categoryId;
    private String explanation;
    private String imageId;
    private boolean markedForReview = false;
    private Integer questionId;
    private final String questionText;
    
    /**
     * @param categoryId the category this question belongs to
     * @param questionId the unique question ID
     * @param questionText the actual question
     * @param imageId the id of the image for this question
     * @param explanation an explanation why which answer is correct or not
     */
    public Question(String questionText, List<Answer> answers) {
        this(null, questionText, null, null, null, answers);
    }
    
    
    /**
     * @param categoryId the category this question belongs to
     * @param questionId the unique question ID
     * @param questionText the actual question
     * @param imageId the id of the image for this question
     * @param explanation an explanation why which answer is correct or not
     */
    public Question(final Integer categoryId, final Integer questionId, final String questionText,
            final String imageId, final String explanation) {
        this(questionId, questionText, imageId, explanation, categoryId, new LinkedList<Answer>());
    }

    /**
     * @param questionId the unique question ID
     * @param questionText the actual question
     * @param imageId the id of the image for this question
     * @param explanation an explanation why which answer is correct or not
     * @param categoryId the category this question belongs to
     * @param answers an array of possible answers for this question
     */
    @SuppressWarnings("boxing")
    public Question(final Integer questionId, final String questionText, final String imageId,
            final String explanation, final Integer categoryId,
            final List<Answer> answers) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.imageId = imageId;
        this.explanation = explanation;
        this.categoryId = categoryId;
        this.answers = answers;
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
    public Answer[] getAnswersAsArray() {
        return this.answers.toArray(new Answer[this.answers.size()]);
    }

    /**
     * @return the category
     */
    public int getCategoryId() {
        return this.categoryId;
    }

    /**
     * @return a list of the answers and if they're correct or not
     */
    @SuppressWarnings("boxing")
    public Map<Integer, Boolean> getCorrectAnswers() {
        final Map<Integer, Boolean> ans = new HashMap<Integer, Boolean>(this.answers.size());
        for (final Answer a : this.answers) {
            ans.put(a.getAnswerId(), a.isAnswerCorrect());
        }
        return ans;
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
     * @param explanation the explanation to set
     */
    public final void setExplanation(final String explanation) {
        this.explanation = explanation;
    }

    /**
     * @param imageId the imageId to set
     */
    public final void setImageId(final String imageId) {
        this.imageId = imageId;
    }

    /**
     * @param markedForReview the markedForReview to set
     */
    public void setMarkedForReview(final boolean markedForReview) {
        this.markedForReview = markedForReview;
    }

}
