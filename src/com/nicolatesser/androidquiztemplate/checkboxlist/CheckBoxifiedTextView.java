/* $Id: BulletedTextView.java 57 2007-11-21 18:31:52Z steven $
 *
 * Copyright 2007 Steven Osborn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/* Code modifications by Daniel Ricciotti
 * This code was built using the IconifiedText tutorial by Steven Osborn
 * http://www.anddev.org/iconified_textlist_-_the_making_of-t97.html
 * 
 * Copyright 2008 Daniel Ricciotti
 */
package com.nicolatesser.androidquiztemplate.checkboxlist;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.CheckBox;

public class CheckBoxifiedTextView extends LinearLayout {
     
     private TextView mText;
     private CheckBox mCheckBox;
     private CheckBoxifiedText mCheckBoxText;
     

     
     public CheckBoxifiedTextView(Context context, CheckBoxifiedText aCheckBoxifiedText, OnClickListener checkBoxHandler) {
          super(context);

          /* First CheckBox and the Text to the right (horizontal),
           * not above and below (vertical) */
          this.setOrientation(HORIZONTAL);
          mCheckBoxText = aCheckBoxifiedText;
          mCheckBox = new CheckBox(context);
          mCheckBox.setPadding(0, 0, 20, 0); // 5px to the right
          
          /* Set the initial state of the checkbox. */
          mCheckBox.setChecked(aCheckBoxifiedText.getChecked());
          
          
          /* At first, add the CheckBox to ourself
           * (! we are extending LinearLayout) */
          addView(mCheckBox,  new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
          
          mText = new TextView(context);
          mText.setText(aCheckBoxifiedText.getText());
         // mText.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
          
          //mText.setPadding(0, 0, 15, 0);
          addView(mText, new LinearLayout.LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
          
//          
//          OnClickListener checkBoxHandler = new OnClickListener()
//		 {
//			/**
//			 * 
//			 * Check or uncked the current checkbox!
//			 */
//			@Override
//			public void onClick(View v) {
//				// Force both the checkbox and our own type to be cehcked or
//				// unchecked!
//				setCheckBoxState(!getCheckBoxState());				
//			}
//		};
        
        mText.setOnClickListener(checkBoxHandler);
        mCheckBox.setOnClickListener(checkBoxHandler);
        
     }
     
     public void setText(String words) {
          mText.setText(words);
     }
     public void setCheckBoxState(boolean checked)
     {
    	 mCheckBox.setChecked(checked);
    	 mCheckBoxText.setChecked(checked);
    	 //DictionaryService.getInstance().setDictionary(mCheckBoxText.getShortName(), checked);
     }
     
     public boolean getCheckBoxState()
     {
    	 return mCheckBoxText.getChecked();
     }
     
     public String getText()
     {
    	 return mCheckBoxText.getShortName();
     }
     
 
     
     
 	 
     

     
}