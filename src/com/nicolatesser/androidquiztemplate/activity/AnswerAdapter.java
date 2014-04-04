package com.nicolatesser.androidquiztemplate.activity;


import java.util.Collections;
import java.util.List;

import com.nicolatesser.androidquiztemplate.R;
import com.nicolatesser.androidquiztemplate.quiz.Answer;


import android.R.integer;
import android.content.Context;
import android.nfc.cardemulation.OffHostApduService;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

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
       

        //fix the height
        int height = parent.getHeight();    
        int proportionalHeight = ((int) Math.floor(height/(data.size())))-12;
        int minButtonHeight = (int)getContext().getResources().getDimension(R.dimen.button_size)+12;

        int targetHeight = Math.max(proportionalHeight, minButtonHeight);
        
        button.setHeight(targetHeight);
        button.setOnClickListener(listener);
        styleAnswerButton(button, answer.getText());
        return v;
    }
   
    
	private void styleAnswerButton(Button button,
			String answerText) {
		
		int maxCharsForLine = 25;
		String formattedText = TextUtils.formatText(answerText, maxCharsForLine);
		button.setText(formattedText);
		
		int numberOfLines = TextUtils.numberOfLines(formattedText);
		int length = answerText.length();
		
		if (length<=maxCharsForLine) {
			button.setTextSize(getContext().getResources().getDimension(R.dimen.answer_big_size));
		}
		else {
			if (numberOfLines>1) {
				button.setTextSize(getContext().getResources().getDimension(R.dimen.answer_small_size));
			}
			else {
				if (length<=2*maxCharsForLine) {
					button.setTextSize(getContext().getResources().getDimension(R.dimen.answer_big_size));
				}
				else {
					button.setTextSize(getContext().getResources().getDimension(R.dimen.answer_small_size));
				}
			}
		}
		
	}
    
}
