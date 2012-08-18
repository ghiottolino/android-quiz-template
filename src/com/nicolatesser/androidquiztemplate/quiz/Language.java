/*
 * Copyright (C) 2011 Tobias C. Sutor
 * File:        Language.java
 * Author:      Tobias C. Sutor
 * E-Mail:      tobias@sutor-it.com
 * Web:         https://sourceforge.net/projects/roboquiz/
 * Created on:  Jun 5, 2011
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
public final class Language {
    private String languageCode;
    private final int languageID;

    /**
     * @param languageID the numeric unique ID of the language
     * @param languageCode the language code like DE, EN, FR, ....
     */
    public Language(final int languageID, final String languageCode) {
        this.languageID = languageID;
        this.languageCode = languageCode;
    }

    /**
     * @return the languageCode
     */
    public final String getLanguageCode() {
        return this.languageCode;
    }

    /**
     * @return the languageID
     */
    public final int getLanguageID() {
        return this.languageID;
    }

    /**
     * @param languageCode the languageCode to set
     */
    public final void setLanguageCode(final String languageCode) {
        this.languageCode = languageCode;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return this.languageCode;
    }
}
