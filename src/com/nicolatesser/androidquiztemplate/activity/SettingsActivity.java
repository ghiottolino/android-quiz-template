package com.nicolatesser.androidquiztemplate.activity;

import java.util.Arrays;
import java.util.List;

import com.nicolatesser.androidquiztemplate.R;
import com.nicolatesser.androidquiztemplate.quiz.Game;
import com.nicolatesser.androidquiztemplate.quiz.GameHolder;
import com.nicolatesser.androidquiztemplate.quiz.QuestionDatabase;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.MultiSelectListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class SettingsActivity extends PreferenceActivity {
	/**
	 * Determines whether to always show the simplified settings UI, where
	 * settings are presented in a single list. When false, settings are shown
	 * as a master/detail two-pane view on tablets. When true, a single pane is
	 * shown on tablets.
	 */
	private static final boolean ALWAYS_SIMPLE_PREFS = false;

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		ActionBarHelper.setUpActionBar(this);
		setupSimplePreferencesScreen();
	}

	/**
	 * Shows the simplified settings UI if the device configuration if the
	 * device configuration dictates that a simplified, single-pane UI should be
	 * shown.
	 */
	private void setupSimplePreferencesScreen() {
		if (!isSimplePreferences(this)) {
			return;
		}

		// In the simplified UI, fragments are not used at all and we instead
		// use the older PreferenceActivity APIs.

		MultiSelectListPreference multiSelectPreference = getCategoriesPreferences(this);
		PreferenceScreen screen = getPreferenceManager()
				.createPreferenceScreen(this);
		screen.addPreference(multiSelectPreference);
	    setPreferenceScreen(screen);
	}

	/** {@inheritDoc} */
	@Override
	public boolean onIsMultiPane() {
		return isXLargeTablet(this) && !isSimplePreferences(this);
	}

	/**
	 * Helper method to determine if the device has an extra-large screen. For
	 * example, 10" tablets are extra-large.
	 */
	private static boolean isXLargeTablet(Context context) {
		return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
	}

	/**
	 * Determines whether the simplified settings UI should be shown. This is
	 * true if this is forced via {@link #ALWAYS_SIMPLE_PREFS}, or the device
	 * doesn't have newer APIs like {@link PreferenceFragment}, or the device
	 * doesn't have an extra-large screen. In these cases, a single-pane
	 * "simplified" settings UI should be shown.
	 */
	private static boolean isSimplePreferences(Context context) {
		return ALWAYS_SIMPLE_PREFS
				|| Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB
				|| !isXLargeTablet(context);
	}

	/** {@inheritDoc} */
	@Override
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void onBuildHeaders(List<Header> target) {
		if (!isSimplePreferences(this)) {
			loadHeadersFromResource(R.xml.pref_headers, target);
		}
	}


	/**
	 * This fragment shows general preferences only. It is used when the
	 * activity is showing a two-pane settings UI.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static class GeneralPreferenceFragment extends PreferenceFragment {
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			
			MultiSelectListPreference multiSelectPreference = getCategoriesPreferences((PreferenceActivity)getActivity());
			PreferenceScreen screen = getPreferenceManager()
					.createPreferenceScreen(getActivity());
			screen.addPreference(multiSelectPreference);
		    setPreferenceScreen(screen);
		}
	}

	
	
	private static MultiSelectListPreference getCategoriesPreferences(PreferenceActivity activity) {

		QuestionDatabase questionDb = QuestionDatabase.getInstance();
		List<String> catList = Arrays.asList();
		if (questionDb!=null) {
			catList = questionDb.getCategories();
		}
		CharSequence[] pref = catList.toArray(new CharSequence[catList.size()]);

		MultiSelectListPreference listPreference = new MultiSelectListPreference(
				activity);
		listPreference.setKey(SessionUtils.SELECTED_CATEGORIES_PREF_KEY);
		listPreference.setTitle("Categories");
		listPreference.setDialogTitle("Select Quiz Categories");
		listPreference.setSummary("Select all the categories you want to play with");
		listPreference.setEntries(pref);
		listPreference.setEntryValues(pref);
		return listPreference;
		
	}
}
