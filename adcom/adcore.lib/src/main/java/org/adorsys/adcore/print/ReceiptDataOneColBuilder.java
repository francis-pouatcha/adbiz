package org.adorsys.adcore.print;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;

public class ReceiptDataOneColBuilder {
	
	private final ReceiptDataTableMetaData receiptMetaData;
	private ReceiptDataOneCol receiptDataLine;
	private boolean built;
	
	
	public ReceiptDataOneColBuilder(ReceiptDataTableMetaData receiptMetaData) {
		this.receiptMetaData = receiptMetaData;
		this.receiptDataLine = new ReceiptDataOneCol(receiptMetaData);
	}

	public void build(ReceiptDataTable rdt) throws IOException{
		receiptDataLine.getDesignationChunks().clear();
		float designationSize = receiptMetaData.getWidth()-(receiptMetaData.getMargin()*2);
		
		String desString = receiptDataLine.getDesignation();
		ReceiptDataUtils.split(desString, designationSize, receiptMetaData, receiptDataLine.isBold(), receiptDataLine.getDesignationChunks());

		receiptDataLine.setHeight(receiptMetaData.getRowHeight() * receiptDataLine.getDesignationChunks().size());
		
		built = true;
		rdt.getReceiptDataRows().add(receiptDataLine);
	}
	public ReceiptDataOneColBuilder withDesignation(String designation) throws IOException {
		assert(!built);
		receiptDataLine.setDesignation(designation);
		return this;
	}

	public ReceiptDataOneColBuilder withCenterAlign() {
		assert(!built);
		receiptDataLine.setCentered(true);
		return this;
	}

	public ReceiptDataOneColBuilder withBoldFont() {
		assert(!built);
		receiptDataLine.setBold(true);
		return this;
	}






	static class ReceiptDataOneCol implements ReceiptDataRow {

		private final ReceiptDataTableMetaData receiptMetaData;

		private String designation;
		
		private boolean bold;
		
		private boolean centered;

		private final List<String> designationChunks = new ArrayList<String>();
		
		private float height;
		
		private ReceiptDataOneCol(ReceiptDataTableMetaData receiptMetaData) {
			this.receiptMetaData = receiptMetaData;
		}

		private String getDesignation() {
			return designation;
		}

		private void setDesignation(String designation) {
			this.designation = designation;
		}

		private void setBold(boolean bold) {
			this.bold = bold;
		}
		
		private boolean isBold() {
			return bold;
		}

		private void setCentered(boolean centered) {
			this.centered = centered;
		}

		private void setHeight(float height) {
			this.height = height;
		}

		private List<String> getDesignationChunks() {
			return designationChunks;
		}

		public float getHeight() {
			return height;
		}

		public float writeItemLine(PDPageContentStream contentStream, float posY) throws IOException{
			
			// Set bold foont if needed
			if(bold) contentStream.setFont(receiptMetaData.getBoldFont(), receiptMetaData.getFontSize());
			
			float positionY = posY;
			float positionX = receiptMetaData.getMargin();
			
			float desPosY = positionY;
			boolean first = true;
			for (String des : designationChunks) {
				if(StringUtils.isBlank(des)) continue;
				float desPosX = positionX;
				if(centered){ // move the position
					float boxSize = receiptMetaData.getWidth()-(receiptMetaData.getMargin()*2);
					desPosX = positionX + ReceiptDataUtils.centerPrint(des, boxSize, receiptMetaData, bold);
				}
				contentStream.beginText();
				contentStream.moveTextPositionByAmount(desPosX, desPosY);
				if(first){
					contentStream.drawString(des);
					first = false;
				} else {
					contentStream.drawString(" "+ des);
				}
				contentStream.endText();
				desPosY -= receiptMetaData.getRowHeight();
			}

			// Reset simple bold foont if needed
			if(bold) contentStream.setFont(receiptMetaData.getFont(), receiptMetaData.getFontSize());
			
			return desPosY;
		}
	}	

}
