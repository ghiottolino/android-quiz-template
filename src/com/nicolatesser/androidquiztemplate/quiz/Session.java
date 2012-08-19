package com.nicolatesser.androidquiztemplate.quiz;

public class Session {

	private int totalAttempts = 0;
	private int correctAttempts = 0;
	private int consecutiveAttempts = 0;

	public int getTotalAttempts() {
		return totalAttempts;
	}

	public void setTotalAttempts(int totalAttempts) {
		this.totalAttempts = totalAttempts;
	}

	public int getCorrectAttempts() {
		return correctAttempts;
	}

	public void setCorrectAttempts(int correctAttempts) {
		this.correctAttempts = correctAttempts;
	}

	public int getConsecutiveAttempts() {
		return consecutiveAttempts;
	}

	public void setConsecutiveAttempts(int consecutive) {
		this.consecutiveAttempts = consecutive;
	}

}
