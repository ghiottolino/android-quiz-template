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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (correct ? 1231 : 1237);
		result = prime * result + ((text == null) ? 0 : text.hashCode());
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
		if (correct != other.correct)
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}
    
    
}
