package com.nicolatesser.androidquiztemplate;


import java.util.List;

import com.nicolatesser.androidquiztemplate.quiz.Answer;
import com.nicolatesser.androidquiztemplate.quiz.Question;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

public class AnswerAdapter extends ArrayAdapter<Answer>{

    Context context;
    int layoutResourceId;   
    List<Answer> data = null;
   
    public AnswerAdapter(Context context, int layoutResourceId, List<Answer> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
 

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.quiz_answer, parent, false);
        Button button = (Button) v.findViewById(R.id.answer);
        Answer answer = data.get(position);
        button.setText(answer.getAnswerText());

        //fix the height
        int height = parent.getHeight();    
        int targetHeight = (int) Math.floor(height/(data.size()));
        button.setHeight(targetHeight);
       
        return v;
    }
   
}
