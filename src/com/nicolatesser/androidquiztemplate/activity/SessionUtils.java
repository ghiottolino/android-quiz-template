package com.nicolatesser.androidquiztemplate.activity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.nicolatesser.androidquiztemplate.quiz.Session;

public class SessionUtils {
	public static final String RECORD_PREF_KEY = "RECORD";

	public static final String SESSION_PREF_KEY = "SESSION";

	public static final String SELECTED_CATEGORIES_PREF_KEY = "CATEGORIES";

	public static void setRecord(Activity activity, int record) {
		saveIntFieldInPreferences(activity, RECORD_PREF_KEY, record);
	}

	public static void setSession(Activity activity, Session session) {
		saveStringFieldInPreferences(activity, SESSION_PREF_KEY,
				session.toString());
	}

	public static int loadRecord(Activity activity) {
		int record = getIntFieldInPreferences(activity, RECORD_PREF_KEY);
		return record;
	}

	protected String loadSession(Activity activity) {
		String serializedSession = getStringFieldInPreferences(activity,
				SESSION_PREF_KEY);
		return serializedSession;
	}

	public static List<String> getSelectedCategories(Activity activity) {

		Set<String> selections;
		try {
			SharedPreferences preferences = PreferenceManager
					.getDefaultSharedPreferences(activity.getBaseContext());
			selections = preferences.getStringSet(SELECTED_CATEGORIES_PREF_KEY,
					new HashSet<String>());
		} catch (NoSuchMethodError e) {
			selections=new HashSet<String>();
		}
		List<String> selectedCategories = new ArrayList<String>();
		selectedCategories.addAll(selections);

		return selectedCategories;
	}

	protected static Integer getIntFieldInPreferences(Activity activity,
			String fieldName) {
		String settingPrefixName = activity.getApplicationContext()
				.getPackageName();
		SharedPreferences settings = activity.getSharedPreferences(
				settingPrefixName, 0);
		int field = 0;
		try {
			field = settings.getInt(fieldName, 0);
		} catch (ClassCastException e) {
			// do nothing
		}

		return field;
	}

	protected static String getStringFieldInPreferences(Activity activity,
			String fieldName) {
		String settingPrefixName = activity.getApplicationContext()
				.getPackageName();
		SharedPreferences settings = activity.getSharedPreferences(
				settingPrefixName, 0);
		String field = "";
		try {
			field = settings.getString(fieldName, "");
		} catch (ClassCastException e) {
			// do nothing
		}
		return field;
	}

	protected static void saveIntFieldInPreferences(Activity activity,
			String fieldName, Integer n) {

		String settingPrefixName = activity.getApplicationContext()
				.getPackageName();
		SharedPreferences settings = activity.getSharedPreferences(
				settingPrefixName, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(fieldName, n);

		// Commit the edits!
		boolean commit = editor.commit();
	}

	protected static void saveStringFieldInPreferences(Activity activity,
			String fieldName, String value) {

		String settingPrefixName = activity.getApplicationContext()
				.getPackageName();
		SharedPreferences settings = activity.getSharedPreferences(
				settingPrefixName, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(fieldName, value);

		// Commit the edits!
		boolean commit = editor.commit();
	}
}
