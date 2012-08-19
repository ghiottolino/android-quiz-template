package com.nicolatesser.androidquiztemplate.quiz;

public class Session {

	private Integer totalAttempts = 0;
	private Integer correctAttempts = 0;
	private Integer consecutiveAttempts = 0;

	public Integer getTotalAttempts() {
		return totalAttempts;
	}

	public void setTotalAttempts(Integer totalAttempts) {
		this.totalAttempts = totalAttempts;
	}

	public Integer getCorrectAttempts() {
		return correctAttempts;
	}

	public void setCorrectAttempts(Integer correctAttempts) {
		this.correctAttempts = correctAttempts;
	}

	public Integer getConsecutiveAttempts() {
		return consecutiveAttempts;
	}

	public void setConsecutiveAttempts(Integer consecutive) {
		this.consecutiveAttempts = consecutive;
	}

}
