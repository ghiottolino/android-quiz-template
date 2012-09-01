package com.nicolatesser.androidquiztemplate.quiz;

import java.io.Serializable;

import java.util.List;
import java.util.Vector;

public class Settings implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<String> selectedSettings;

	public Settings(String seriailzedSettings) {
		selectedSettings = new Vector<String>();
		if (seriailzedSettings!=null)
		{
			String[] split = seriailzedSettings.split(";");
			for (int i=0;i<split.length;i++)
			{
				selectedSettings.add(split[i]);
			}
		}
	}

	public List<String> getSelectedSettings() {
		return selectedSettings;
	}

	public void setSelectedSettings(List<String> selectedSessions) {
		this.selectedSettings = selectedSessions;
	}

	public String toString() {
		String serializedSettings = "";
		for (String session : selectedSettings) {
			serializedSettings += session + ";";
		}
		return serializedSettings;
	}

}
