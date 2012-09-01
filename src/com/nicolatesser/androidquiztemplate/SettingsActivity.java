package com.nicolatesser.androidquiztemplate;


import java.util.List;
import java.util.Vector;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;


import com.nicolatesser.androidquiztemplate.actionbarcompat.ActionBarActivity;
import com.nicolatesser.androidquiztemplate.checkboxlist.CheckBoxifiedText;
import com.nicolatesser.androidquiztemplate.checkboxlist.CheckBoxifiedTextListAdapter;
import com.nicolatesser.androidquiztemplate.checkboxlist.CheckBoxifiedTextView;
import com.nicolatesser.androidquiztemplate.quiz.Game;

import com.nicolatesser.androidquiztemplate.quiz.Settings;

/**
 * Thanks to http://www.anddev.org/iconified_textlist_-_the_making_of-t97.html
 */
public class SettingsActivity extends ActionBarActivity {
	/** Called when the activity is first created. */

	private ListView mListView;
	private CheckBoxifiedTextListAdapter adapter;


	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);

		mListView = (ListView) findViewById(R.id.list);

		OnClickListener checkBoxHandler = new OnClickListener() {
			@Override
			public void onClick(View v) {

				CheckBoxifiedTextView checkBoxifiedTextView = null;
				if (v instanceof CheckBoxifiedTextView) {
					checkBoxifiedTextView = (CheckBoxifiedTextView) v;
				} else if (v instanceof CheckBox) {
					CheckBox checkBox = (CheckBox) v;
					checkBoxifiedTextView = (CheckBoxifiedTextView) checkBox
							.getParent();
				} else if (v instanceof TextView) {
					TextView textView = (TextView) v;
					checkBoxifiedTextView = (CheckBoxifiedTextView) textView
							.getParent();
				}

				boolean checkBoxState = checkBoxifiedTextView
						.getCheckBoxState();
				boolean selected = !checkBoxState;

				checkBoxifiedTextView.setCheckBoxState(selected);

				Settings settings = Game.getInstance().getSettings();
				String categoy = checkBoxifiedTextView.getText();
				List<String> selectedSettings = settings.getSelectedSettings();
				if (selected) {
					// add selected category to list
					selectedSettings.add(categoy);
				} else {
					// remove selected category from list
					selectedSettings.remove(categoy);
				}
				settings.setSelectedSettings(selectedSettings);
				saveStringFieldInPreferences(QuizActivity.SETTINGS_PREF_KEY,
						settings.toString());
			}
		};

		adapter = new CheckBoxifiedTextListAdapter(this, checkBoxHandler);
		Game game = Game.getInstance();
		List<String> categories = game.getCategories();
		List<String> selectedSettings = game.getSettings()
				.getSelectedSettings();
		for (String category : categories) {
			boolean selected = false;
			if (selectedSettings.contains(category)) {
				selected = true;
			}

			adapter.addItem(new CheckBoxifiedText(category, category, selected));
		}

		// Display it
		mListView.setAdapter(adapter);

	}

	protected void saveStringFieldInPreferences(String fieldName, String value) {

		String settingPrefixName = getApplicationContext().getPackageName();
		SharedPreferences settings = getSharedPreferences(settingPrefixName, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(fieldName, value);

		// Commit the edits!
		boolean commit = editor.commit();
	}

	// MENU

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		// Calling super after populating the menu is necessary here to ensure
		// that the
		// action bar helpers have a chance to handle this event.
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();

		if (itemId == android.R.id.home) {
			// do nothing, app caller should do something

		} else if (itemId == R.id.menu_other_apps) {

			Intent goToMarket;
			goToMarket = new Intent(Intent.ACTION_VIEW,
					Uri.parse("market://search?q=pub:\"Nicola Tesser\""));
			startActivity(goToMarket);
		} else if (itemId == R.id.menu_settings) {
			this.openOptionsMenu();
		}

		return super.onOptionsItemSelected(item);
	}

}