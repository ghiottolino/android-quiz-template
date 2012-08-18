/*
 * RoboQuiz     Copyright (C) 2011 Tobias C. Sutor
 * File:        Statics.java
 * Author:      Tobias C. Sutor
 * E-Mail:      tobias@sutor-it.com
 * Web:         https://sourceforge.net/projects/roboquiz/
 * Created on:  May 30, 2011
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
public final class Statics {
    /**
     * 
     */
    public final static int allowedTime = 60 * 1000; // Seconds per question
    /**
     * 
     */
    public final static boolean backAllowed = true;
    /**
     * 
     */
    public final static int badColor = 0xFF770000;
    /**
     * 
     */
    public final static String DATABASE_FILE = "quiz.db";
    /**
     * 
     */
    public final static String DATABASE_SOURCE = "input.db";
    /**
     * 
     */
    public final static int DATABASE_VERSION = 1;
    /**
     * 
     */
    public final static String dbUpdateUrl = "http://www.example.com/test.db";
    /**
     * 
     */
    public final static int goodColor = 0xFF007700;
    /**
     * 
     */
    public final static int percentForSuccess = 85;
    /**
     * 
     */
    public final static boolean showAmountCorrectAnswers = true;
}
