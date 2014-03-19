package com.nicolatesser.androidquiztemplate.quiz;


public class Answer {

    private final String text;
    private final boolean correct;

    
    /**
     * @param text the actual text of the answer
     * @param correct whether this is an correct answer
     */
    public Answer(final String text, final boolean correct) {
        this.text=text;
        this.correct=correct;
    }

    public String getText() {
        return this.text;
    }

    public boolean isCorrect() {
        return this.correct;
    }
    
}
