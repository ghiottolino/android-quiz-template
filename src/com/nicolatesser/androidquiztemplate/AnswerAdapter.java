package com.nicolatesser.androidquiztemplate;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

public class AnswerAdapter extends ArrayAdapter<String>{

    Context context;
    int layoutResourceId;   
    String data[] = null;
   
    public AnswerAdapter(Context context, int layoutResourceId, String[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
 
        Button button;           

        if (convertView == null) {
        	button = new Button(context);
        }
        else
        {
            button = (Button)convertView;           

        }
        String answer = data[position];
        button.setText(answer);
       
        return button;
    }
   
}
