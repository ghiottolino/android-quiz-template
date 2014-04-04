package com.nicolatesser.androidquiztemplate.activity;


public class TextUtils {

	
	public static int numberOfLines(String text) {
		 String[] lines = text.split("\r\n|\r|\n");
		 return  lines.length;
	}
	
	public static String formatText(String text, int maxCharsForLine) {
		
		int length = text.length();
		
		if (length<=maxCharsForLine) return text;
		else if (!text.contains(" ")) {
			return text;
		}
		else {
			//split in 2 lines
			String[] textParts = text.split(" ");
			
			if (length<=2*maxCharsForLine) {
				StringBuffer line1=new StringBuffer();
				StringBuffer line2=new StringBuffer();
				
				for(int i=0;i<textParts.length;i++) {
					String textPart = textParts[i];
					if ((line1.length()+textPart.length()<maxCharsForLine)&&(line2.length()==0)) {
						line1.append(textPart);
						line1.append(" ");
					}
					else {
						line2.append(textPart);
						line2.append(" ");
					}
				}
				
				return line1+"\n"+line2;
			}
			//split in 3 lines
			else {
				StringBuffer line1=new StringBuffer();
				StringBuffer line2=new StringBuffer();
				StringBuffer line3=new StringBuffer();
				
				for(int i=0;i<textParts.length;i++) {
					String textPart = textParts[i];
					if ((line1.length()+textPart.length()<maxCharsForLine)&&(line2.length()==0)) {
						line1.append(textPart);
						line1.append(" ");
						
					}
					else if ((line2.length()+textPart.length()<maxCharsForLine)&&(line3.length()==0)) {
						line2.append(textPart);
						line2.append(" ");
						
					}
					else {
						line3.append(textPart);
						line3.append(" ");
					}
				}
				
				return line1+"\n"+line2+"\n"+line3;
			}
		}
		
		
		
		
		
		
		
		
	}
	
	
	
	
}
