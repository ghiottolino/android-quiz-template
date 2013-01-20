/*
 * Copyright 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nicolatesser.androidquiztemplate.actionbarcompat;

import com.nicolatesser.androidquiztemplate.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;

/**
 * An extension of
 * {@link com.nicolatesser.androidquiztemplate.actionbarcompat.ActionBarHelper}
 * that provides Android 4.0-specific functionality for IceCreamSandwich
 * devices. It thus requires API level 14.
 */
@SuppressLint("NewApi")
public class ActionBarHelperICS extends ActionBarHelperHoneycomb {
	protected ActionBarHelperICS(Activity activity) {
		super(activity);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		 mOptionsMenu = menu;
		// set home button enabled as default (4.0 has default false)
		mActivity.getActionBar().setHomeButtonEnabled(true);
		// android >= 3 does not show menu button on the action bar if device
		// has a phisical menu button. this workaround shows it anyway.
		if (ViewConfiguration.get(mActivity).hasPermanentMenuKey()) {
			final MenuItem menuItem = mOptionsMenu.findItem(R.id.menu_menu);
			if (menuItem!=null)
			{
				menuItem.setVisible(true);
				menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);		
			}
		}

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	protected Context getActionBarThemedContext() {
		return mActivity.getActionBar().getThemedContext();
	}
}
