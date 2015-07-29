package org.adorsys.adcshdwr.receiptprint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;

public class ReceiptDataTwoColsBuilder {
	
	private final ReceiptDataTableMetaData receiptMetaData;
	private final ReceiptDataTwoColsMetaData lineMetaData;
	private final ReceiptDataTwoCol receiptDataLine;
	
	private boolean built;
	
	public ReceiptDataTwoColsBuilder(ReceiptDataTableMetaData receiptMetaData,
			ReceiptDataTwoColsMetaData lineMetaData) {
		super();
		this.receiptMetaData = receiptMetaData;
		this.lineMetaData = lineMetaData;
		this.receiptDataLine = new ReceiptDataTwoCol(receiptMetaData, lineMetaData);
	}

	public void build(ReceiptDataTable rdt) throws IOException{
		receiptDataLine.getDesignationChunks().clear();
		float designationSize = lineMetaData.getDesignation();
		
		String desString = receiptDataLine.getDesignation();
		ReceiptDataUtils.split(desString, designationSize, receiptMetaData, receiptDataLine.isBold(), receiptDataLine.getDesignationChunks());

		receiptDataLine.setHeight(lineMetaData.getRowHeight() * receiptDataLine.getDesignationChunks().size());
		
		built = true;
		rdt.getReceiptDataRows().add(receiptDataLine);
	}
	
	public ReceiptDataTwoColsBuilder withDesignation(String designation) throws IOException {
		assert(!built);
		receiptDataLine.setDesignation(designation);
		return this;
	}

	public ReceiptDataTwoColsBuilder withValue(String value) {
		assert(!built);
		receiptDataLine.setValue(value);
		return this;
	}

	public ReceiptDataTwoColsBuilder withBoldFont() {
		assert(!built);
		receiptDataLine.setBold(true);
		return this;
	}
	public ReceiptDataTwoColsBuilder withCenterAlign() {
		assert(!built);
		receiptDataLine.setCentered(true);
		return this;
	}



	static class ReceiptDataTwoCol implements ReceiptDataRow {

		private final ReceiptDataTableMetaData receiptMetaData;
		private final ReceiptDataTwoColsMetaData lineMetaData;

		private String value;
		
		private String designation;
		
		private boolean bold;

		private boolean centered;

		private final List<String> designationChunks = new ArrayList<String>();
		
		private float height;
		
		private ReceiptDataTwoCol(ReceiptDataTableMetaData receiptMetaData,
				ReceiptDataTwoColsMetaData lineMetaData) {
			super();
			this.receiptMetaData = receiptMetaData;
			this.lineMetaData = lineMetaData;
		}

		private String getDesignation() {
			return designation;
		}

		private void setDesignation(String designation) {
			this.designation = designation;
		}

		private void setValue(String value) {
			this.value = value;
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
			
			// Set bold font if needed
			if(bold) contentStream.setFont(receiptMetaData.getBoldFont(), receiptMetaData.getFontSize());
			
			float positionY = posY;
			float positionX = receiptMetaData.getMargin();
			
			float desPosY = positionY;
			boolean first = true;
			for (String des : designationChunks) {
				if(StringUtils.isBlank(des)) continue;
				float desPosX = positionX;
				if(centered){ // move the position
					float boxSize = lineMetaData.getDesignation();
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
				desPosY -= lineMetaData.getRowHeight();
			}
			
			if(StringUtils.isNotBlank(value)){
				positionX += lineMetaData.getDesignation()+lineMetaData.getCellMargin();
				float desPosX = positionX;
				float boxSize = lineMetaData.getValue();
				if(centered){ // move the position
					desPosX = positionX + ReceiptDataUtils.centerPrint(value, boxSize, receiptMetaData, bold);
				} else {
					desPosX = positionX + ReceiptDataUtils.rightPrint(value, boxSize, receiptMetaData, bold);
				}
				contentStream.beginText();
				contentStream.moveTextPositionByAmount(desPosX, positionY);
				contentStream.drawString(StringUtils.isBlank(value)?"":value);
				contentStream.endText();
			}

			// Reset simple bold foont if needed
			if(bold) contentStream.setFont(receiptMetaData.getFont(), receiptMetaData.getFontSize());
			
			return desPosY;
		}
	}	

}
