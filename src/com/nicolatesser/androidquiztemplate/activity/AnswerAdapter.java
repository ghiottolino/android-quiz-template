package com.nicolatesser.androidquiztemplate.activity;


import java.util.Collections;
import java.util.List;

import com.nicolatesser.androidquiztemplate.R;
import com.nicolatesser.androidquiztemplate.quiz.Answer;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

public class AnswerAdapter extends ArrayAdapter<Answer>{

    Context context;
    int layoutResourceId;   
    List<Answer> data = null;
    OnClickListener listener = null;
   
    public AnswerAdapter(Context context, int layoutResourceId, List<Answer> data, OnClickListener listener) {
    	super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        this.listener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
 

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.quiz_answer, parent, false);
        Button button = (Button) v.findViewById(R.id.answer);
        Answer answer = data.get(position);
        button.setText(answer.getText());

        //fix the height
        int height = parent.getHeight();    
        int proportionalHeight = ((int) Math.floor(height/(data.size())))-12;
        int minButtonHeight = (int)getContext().getResources().getDimension(R.dimen.button_size)+12;

        int targetHeight = Math.max(proportionalHeight, minButtonHeight);
        
        button.setHeight(targetHeight);
        button.setOnClickListener(listener);
        return v;
    }
   
}
