/*
 * RoboQuiz     Copyright (C) 2011 Tobias C. Sutor
 * File:        Category.java
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
public class Category implements Comparable<Category> {
    private final String categoryDescription;
    private final int categoryId;
    private final String categoryTitle;
    private final String parentCategoryTitle;
    private final int position;

    /**
     * @param categoryId the unique of this category
     * @param categoryTitle the title of the category
     * @param parentCategoryTitle the title of the parent category
     * @param categoryDescription the description of this category
     * @param position the position (used to display the categories in a special
     *            order)
     */
    public Category(final int categoryId, final String categoryTitle,
            final String parentCategoryTitle,
            final String categoryDescription, final int position) {
        this.categoryId = categoryId;
        this.categoryTitle = categoryTitle;
        this.parentCategoryTitle = parentCategoryTitle;
        this.categoryDescription = categoryDescription;
        this.position = position;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(final Category another) {
        if (this.position - another.getPosition() == 0) {
            return this.categoryTitle.compareTo(another.getCategoryTitle());
        }
        return (int) Math.signum(this.position - another.getPosition());
    }

    /**
     * @return the categoryDescription
     */
    public String getCategoryDescription() {
        return this.categoryDescription;
    }

    /**
     * @return the categoryId
     */
    public int getCategoryId() {
        return this.categoryId;
    }

    /**
     * @return the categoryTitle
     */
    public String getCategoryTitle() {
        return this.categoryTitle;
    }

    /**
     * @return the parentCategoryTitle
     */
    public final String getParentCategoryTitle() {
        return this.parentCategoryTitle;
    }

    /**
     * @return the position
     */
    public final int getPosition() {
        return this.position;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return this.categoryTitle;
    }
}
