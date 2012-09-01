/* $Id: BulletedTextListAdapter.java 57 2007-11-21 18:31:52Z steven $
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

import java.util.ArrayList;
import android.util.Log;
import java.util.List;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;

/** @author Steven Osborn - http://steven.bitsetters.com */
public class CheckBoxifiedTextListAdapter extends BaseAdapter {

     /** Remember our context so we can use it when constructing views. */
     private Context mContext;

     private List<CheckBoxifiedText> mItems = new ArrayList<CheckBoxifiedText>();
     
     private OnClickListener checkBoxHandler;

     public CheckBoxifiedTextListAdapter(Context context, OnClickListener onClickListener) {
          mContext = context;
          this.checkBoxHandler = onClickListener;
     }

     public void addItem(CheckBoxifiedText it) { mItems.add(it); }

     public void setListItems(List<CheckBoxifiedText> lit) { mItems = lit; }

     /** @return The number of items in the */
     public int getCount() { return mItems.size(); }

     public Object getItem(int position) { return mItems.get(position); }
     
     public void setChecked(boolean value, int position){
         mItems.get(position).setChecked(value);
     }
     public void selectAll(){
         for(CheckBoxifiedText cboxtxt: mItems)
              cboxtxt.setChecked(true);
         /* Things have changed, do a redraw. */
         this.notifyDataSetInvalidated();
     }
     public void deselectAll()
     {
         for(CheckBoxifiedText cboxtxt: mItems)
             cboxtxt.setChecked(false);
        /* Things have changed, do a redraw. */
        this.notifyDataSetInvalidated();
     }

     public boolean areAllItemsSelectable() { return false; }

     /** Use the array index as a unique id. */
     public long getItemId(int position) {
          return position;
     }

     /** @param convertView The old view to overwrite, if one is passed
      * @returns a CheckBoxifiedTextView that holds wraps around an CheckBoxifiedText */
     public View getView(int position, View convertView, ViewGroup parent){
          CheckBoxifiedTextView btv;
          if (convertView == null) {
               btv = new CheckBoxifiedTextView(mContext, mItems.get(position), checkBoxHandler);
          } else { // Reuse/Overwrite the View passed
               // We are assuming(!) that it is castable!
        	   CheckBoxifiedText src = mItems.get(position);
               btv = (CheckBoxifiedTextView) convertView;
               btv.setCheckBoxState(src.getChecked()); 
               btv = (CheckBoxifiedTextView) convertView;
               btv.setText(mItems.get(position).getText());
          }
          return btv;
     }
}