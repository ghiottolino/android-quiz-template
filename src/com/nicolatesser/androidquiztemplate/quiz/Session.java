package com.nicolatesser.androidquiztemplate.quiz;


import java.io.Serializable;

public class Session implements Serializable {

	private Integer totalAttempts = 0;
	private Integer correctAttempts = 0;
	private Integer consecutiveAttempts = 0;
	private Integer bestConsecutiveAttempts = 0;

	public Session() {

	}

	public Session(String seriailzedSession) {
		try {
			String[] split = seriailzedSession.split(";", 4);
			totalAttempts = Integer.parseInt(split[0]);
			correctAttempts = Integer.parseInt(split[1]);
			consecutiveAttempts = Integer.parseInt(split[2]);
			bestConsecutiveAttempts = Integer.parseInt(split[3]);

		} catch (Exception e) {
			totalAttempts = 0;
			correctAttempts = 0;
			consecutiveAttempts = 0;
			bestConsecutiveAttempts = 0;
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

	public String getCorrectnessRate() {
		return getCorrectnessRate(correctAttempts, totalAttempts);
	}

	private String getCorrectnessRate(Integer correct, Integer total) {
		int perc = (int) Math.round(((float) correct / (float) total) * 100);
		return Integer.toString(perc) + "%";
	}

	public void setCorrectAttempts(Integer correctAttempts) {
		this.correctAttempts = correctAttempts;
	}

	public Integer getConsecutiveAttempts() {
		return consecutiveAttempts;
	}

	public void setConsecutiveAttempts(Integer consecutive) {
		this.consecutiveAttempts = consecutive;
		if (consecutive > bestConsecutiveAttempts) {
			bestConsecutiveAttempts = consecutive;
		}
	}

	public Integer getBestConsecutiveAttempts() {
		return bestConsecutiveAttempts;
	}

	public String toString() {
		String serializedSession = totalAttempts + ";" + correctAttempts + ";"
				+ consecutiveAttempts + ";" + bestConsecutiveAttempts;
		return serializedSession;
	}

}
