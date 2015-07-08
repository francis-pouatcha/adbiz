package org.adorsys.adcshdwr.receiptprint;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class ReceiptDataUtils {
	
	public static void split(String textString, float boxSize, ReceiptDataTableMetaData receiptMetaData, 
			boolean bold, final List<String> collector) throws IOException
	{
		String[] textArray = {};
		
		textArray= StringUtils.splitByWholeSeparator(textString, " ");
	
		String currentLine = "";
		for (String text : textArray) {
			if(StringUtils.isBlank(text)) continue;
			text = text.trim();
			float textWidth = receiptMetaData.getWidth(text, bold);
			if(textWidth>=boxSize){
				if (StringUtils.isNotBlank(currentLine)){
					collector.add(currentLine);
					currentLine = "";
				}
				//int chunkSize = (text.length() * (int)(boxSize/textWidth))-3;
				int chunkSize= 1;
				
				String substring = StringUtils.substring(text, 0, chunkSize);
				collector.add(substring);
				
				String restString = StringUtils.substring(text, substring.length());
				
				while(restString.length()>chunkSize){
					substring = StringUtils.substring(restString, 0, chunkSize);
					collector.add(substring);
					restString = StringUtils.substring(text, substring.length());
				}
				if(StringUtils.isNotBlank(restString)){
					collector.add(restString);
				}
			} else {
				String prospectiveLine = currentLine + " "+  text;
				textWidth = receiptMetaData.getWidth(prospectiveLine, bold);
				if(textWidth>boxSize){
					collector.add(currentLine);
					currentLine = text;
				} else {
					currentLine = prospectiveLine;
				}
			}
		}
		if(StringUtils.isNotBlank(currentLine)) collector.add(currentLine);
	}
	
	public static float centerPrint(String textString, float boxSize, ReceiptDataTableMetaData receiptMetaData, boolean bold) throws IOException{
		float textWidth = receiptMetaData.getWidth(textString, bold);
		float shift = (boxSize-textWidth)/2;
		return shift;
	}

	public static float rightPrint(String textString, float boxSize, ReceiptDataTableMetaData receiptMetaData, boolean bold) throws IOException{
		float textWidth = receiptMetaData.getWidth(textString, bold);
		float shift = boxSize-textWidth;
		return shift;
	}

}
