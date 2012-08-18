/*
 * RoboQuiz     Copyright (C) 2011 Tobias C. Sutor
 * File:        Result.java
 * Author:      Tobias C. Sutor
 * E-Mail:      tobias@sutor-it.com
 * Web:         https://sourceforge.net/projects/roboquiz/
 * Created on:  Jun 22, 2011
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
public class Result implements Comparable<Result> {
    private int categoryId;
    private String categoryTitle;
    private int result;
    private long startTime;

    /**
     * @param categoryId the unique ID of the category
     * @param result achieved result/score
     * @param startTime time-stamp of the result
     */
    public Result(final int categoryId, final int result, final long startTime) {
        this(categoryId, "", result, startTime);
    }

    /**
     * @param categoryId the unique ID of the category
     * @param categoryTitle title of the category
     * @param result achieved result/score
     * @param startTime time-stamp of the result
     */
    public Result(final int categoryId, final String categoryTitle, final int result,
            final long startTime) {
        this.categoryId = categoryId;
        this.categoryTitle = categoryTitle;
        this.result = result;
        this.startTime = startTime;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(final Result another) {
        return (int) Math.signum(this.startTime - another.getStartTime());
    }

    /**
     * @return the categoryId
     */
    public final int getCategoryId() {
        return this.categoryId;
    }

    /**
     * @return the categoryTitle
     */
    public final String getCategoryTitle() {
        return this.categoryTitle;
    }

    /**
     * @return the result
     */
    public final int getResult() {
        return this.result;
    }

    /**
     * @return the startTime
     */
    public final long getStartTime() {
        return this.startTime;
    }

    /**
     * @param categoryId the categoryId to set
     */
    public final void setCategoryId(final int categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * @param categoryTitle the categoryTitle to set
     */
    public final void setCategoryTitle(final String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    /**
     * @param result the result to set
     */
    public final void setResult(final int result) {
        this.result = result;
    }

    /**
     * @param startTime the startTime to set
     */
    public final void setStartTime(final long startTime) {
        this.startTime = startTime;
    }

}
