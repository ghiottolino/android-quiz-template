package com.nicolatesser.androidquiztemplate.quiz;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class Session implements Externalizable {

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

	@Override
	public void readExternal(ObjectInput arg0) throws IOException,
			ClassNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeExternal(ObjectOutput arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}

}
