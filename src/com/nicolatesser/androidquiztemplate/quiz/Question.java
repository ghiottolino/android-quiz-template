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
public class Question implements Comparable<Question> {
    private boolean answered = false;
    private List<Answer> answers;
    private final int categoryId;
    private int correctAnswers;
    private String explanation;
    private String imageId;
    private boolean markedForReview = false;
    private final int questionId;
    private final String questionText;
    private Map<Integer, Boolean> userAnswers;

    /**
     * @param categoryId the category this question belongs to
     * @param questionId the unique question ID
     * @param questionText the actual question
     * @param imageId the id of the image for this question
     * @param explanation an explanation why which answer is correct or not
     */
    public Question(final int categoryId, final int questionId, final String questionText,
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
    public Question(final int questionId, final String questionText, final String imageId,
            final String explanation, final int categoryId,
            final LinkedList<Answer> answers) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.imageId = imageId;
        this.explanation = explanation;
        this.categoryId = categoryId;
        this.answers = answers;
        this.userAnswers = new HashMap<Integer, Boolean>(this.answers.size());
        for (final Answer a : this.answers) {
            this.userAnswers.put(a.getAnswerId(), Boolean.FALSE);
        }
    }

    /**
     * @param answer an answer
     */
    public void addAnswer(final Answer answer) {
        this.answers.add(answer);
        if (answer.isAnswerCorrect()) {
            this.correctAnswers++;
        }
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(final Question another) {
        int res = 0;
        if (this.isAnswered() && !another.isAnswered()) {
            res = -1;
        } else if (another.isAnswered() && !this.isAnswered()) {
            res = 1;
        }
        return res;
    }

    /**
     * @return the amount of all answers
     */
    public int getAmountAnswers() {
        return this.answers.size();
    }

    /**
     * @return the amount of correct answers
     */
    public int getAmountCorrectAnswers() {
        return this.correctAnswers;
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
     * @return the userAnswers
     */
    @SuppressWarnings("boxing")
    public Map<Integer, Boolean> getUserAnswers() {
        if ((null == this.userAnswers) || this.userAnswers.isEmpty()) {
            this.userAnswers = new HashMap<Integer, Boolean>(this.answers.size());
            for (final Answer a : this.answers) {
                this.userAnswers.put(a.getAnswerId(), false);
            }
        } else if (this.userAnswers.size() < this.answers.size()) {
            for (final Answer s : this.answers) {
                if (!this.userAnswers.containsKey(s.getAnswerId())) {
                    this.userAnswers.put(s.getAnswerId(), false);
                }
            }
        }
        return this.userAnswers;
    }

    /**
     * @return the answered
     */
    public boolean isAnswered() {
        this.answered = false;
        if (this.isAnsweredCorrect()) {
            this.answered = true;
        } else if (this.userAnswers.size() > 0) {
            for (final boolean b : this.userAnswers.values()) {
                if (b) {
                    this.answered = true;
                    break;
                }
            }

        }
        return this.answered;
    }

    /**
     * @param userAnswers the answers of the user
     * @return whether the amount of answered answers equals the amount of
     *         expected answers
     */
    public boolean isAnsweredAmountCorrect(final Map<Integer, Boolean> userAnswers) {
        int i = 0;
        for (final boolean a : userAnswers.values()) {
            if (a) {
                i++;
            }
        }
        return (i == this.getAmountCorrectAnswers());
    }

    /**
     * @return whether this question is answered correct
     */
    @SuppressWarnings("boxing")
    public boolean isAnsweredCorrect() {
        for (final Answer a : this.answers) {
            if ((null == this.userAnswers) || (!this.userAnswers.containsKey(a.getAnswerId()))
                    || (a.isAnswerCorrect() != this.userAnswers.get(a.getAnswerId()))) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return the markedForReview
     */
    public boolean isMarkedForReview() {
        return this.markedForReview;
    }

    /**
     * @param list a list of answers
     */
    public void setAnswers(final List<Answer> list) {
        this.answers = new LinkedList<Answer>(list);
        for (final Answer a : list) {
            if (a.isAnswerCorrect()) {
                this.correctAnswers++;
            }
        }
        this.isAnswered();
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

    /**
     * @param userAnswers set the answers of the user
     */
    public void setUserAnswers(final Map<Integer, Boolean> userAnswers) {
        this.userAnswers = new HashMap<Integer, Boolean>(userAnswers);
        this.isAnswered();
    }
}
