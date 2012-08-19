/*
 * RoboQuiz     Copyright (C) 2011 Tobias C. Sutor
 * File:        Answer.java
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

/**
 * @author Tobias C. Sutor <tobias@sutor-it.com>
 */
public class Answer {

    private Integer answerId;
    private final String answerText;
    private final boolean answerCorrect;

    
    /**
     * @param answerText the actual text of the answer
     * @param answerCorrect whether this is an correct answer
     */
    public Answer(final String answerText, final boolean answerCorrect) {
        this(null, answerText,answerCorrect);
    }

    
    /**
     * @param answerId the unique ID of the answer
     * @param answerText the actual text of the answer
     * @param answerCorrect whether this is an correct answer
     */
    public Answer(final Integer answerId, final String answerText, final boolean answerCorrect) {
        this.answerId = answerId;
        this.answerText = answerText;
        this.answerCorrect = answerCorrect;
    }

    /**
     * @return the answerId
     */
    public int getAnswerId() {
        return this.answerId;
    }

    /**
     * @return the answerText
     */
    public String getAnswerText() {
        return this.answerText;
    }

    /**
     * @return the answerCorrect
     */
    public boolean isAnswerCorrect() {
        return this.answerCorrect;
    }


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (answerCorrect ? 1231 : 1237);
		result = prime * result
				+ ((answerText == null) ? 0 : answerText.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Answer other = (Answer) obj;
		if (answerCorrect != other.answerCorrect)
			return false;
		if (answerText == null) {
			if (other.answerText != null)
				return false;
		} else if (!answerText.equals(other.answerText))
			return false;
		return true;
	}
    

}
