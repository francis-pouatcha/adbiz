package org.adorsys.adcshdwr.receiptprint;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class ReceiptDataTableMetaData {
private final float rowHeight;
	
	private final float margin;
	private final float width;
	
	private final PDFont font = PDType1Font.HELVETICA;
	private final PDFont boldFont = PDType1Font.HELVETICA_BOLD;
	
	
	private final float fontSize;
	
	public ReceiptDataTableMetaData(float margin, float width, float fontSize, float rowHeight) {
		super();
		this.margin = margin;
		this.width = width;
		this.fontSize = fontSize;
		this.rowHeight = rowHeight;
	}
	public float getMargin() {
		return margin;
	}
	public float getWidth() {
		return width;
	}
	public PDFont getFont() {
		return font;
	}
	public PDFont getBoldFont() {
		return boldFont;
	}
	public float getFontSize() {
		return fontSize;
	}
	
	public float getRowHeight() {
		return rowHeight;
	}
	public float getWidth(String s, boolean bold) throws IOException{
		// Bold
		if(bold) return getBoldFont().getStringWidth(s) / 1000 * getFontSize();
		
		// Simple
		return getFont().getStringWidth(s) / 1000 * getFontSize();		
	}
	
	

}
