package com.nicolatesser.androidquiztemplate.quiz;

import java.io.Serializable;

public class Session implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer totalAttempts = 0;
	private Integer correctAttempts = 0;
	private Integer consecutiveAttempts = 0;

	public Session() {

	}

	public Session(String seriailzedSession) {
		try {
			String[] split = seriailzedSession.split(";", 3);
			totalAttempts = Integer.parseInt(split[0]);
			correctAttempts = Integer.parseInt(split[1]);
			consecutiveAttempts = Integer.parseInt(split[2]);

		} catch (Exception e) {
			totalAttempts = 0;
			correctAttempts = 0;
			consecutiveAttempts = 0;
		}
	}

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

	public String toString() {
		String serializedSession = totalAttempts + ";" + correctAttempts + ";"
				+ consecutiveAttempts;
		return serializedSession;
	}

}
