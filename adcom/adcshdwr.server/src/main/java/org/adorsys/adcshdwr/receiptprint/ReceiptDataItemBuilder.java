package org.adorsys.adcshdwr.receiptprint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;

public class ReceiptDataItemBuilder {
	private final ReceiptDataTableMetaData receiptMetaData;
	private final ReceiptDataItemMetaData itemsMetaData;

	private final ReceiptDataInvoiceItem receiptDataItem;
	
	private boolean built;

	public ReceiptDataItemBuilder(ReceiptDataTableMetaData receiptMetaData,
			ReceiptDataItemMetaData itemsMetaData) {
		super();
		this.receiptMetaData = receiptMetaData;
		this.itemsMetaData = itemsMetaData;
		receiptDataItem = new ReceiptDataInvoiceItem(receiptMetaData, itemsMetaData);
	}

	public void build(ReceiptDataTable rdt) throws IOException{
		
		receiptDataItem.getDesignationChunks().clear();
		
		float designationSize = itemsMetaData.getDesignation();
		
		String desString = itemsMetaData.isMergeCodeAndDesignation()?receiptDataItem.getCode()+" " + receiptDataItem.getDesignation(): receiptDataItem.getDesignation();
		
		ReceiptDataUtils.split(desString, designationSize, receiptMetaData, receiptDataItem.isBold(), receiptDataItem.getDesignationChunks());
		receiptDataItem.setHeight(itemsMetaData.getRowHeight() * receiptDataItem.getDesignationChunks().size());

		built = true;
		rdt.getReceiptDataRows().add(receiptDataItem);
	}

	public ReceiptDataItemBuilder withCode(String code) {
		assert(!built);
		receiptDataItem.setCode(code);
		return this;
	}

	public ReceiptDataItemBuilder withDesignation(String designation) throws IOException {
		assert(!built);
		receiptDataItem.setDesignation(designation);
		return this;
	}

	public ReceiptDataItemBuilder withQuantity(String quantity) {
		assert(!built);
		receiptDataItem.setQuantity(quantity);
		return this;
	}

	public ReceiptDataItemBuilder withTotal(String total) {
		assert(!built);
		receiptDataItem.setTotal(total);
		return this;
	}
	
	public ReceiptDataItemBuilder withBoldFont() {
		assert(!built);
		receiptDataItem.setBold(true);
		return this;
	}

	public ReceiptDataItemBuilder withCenterAlign() {
		assert(!built);
		receiptDataItem.setCentered(true);
		return this;
	}
	
	static class ReceiptDataInvoiceItem implements ReceiptDataRow {

		private final ReceiptDataTableMetaData receiptMetaData;
		private final ReceiptDataItemMetaData itemsMetaData;

		private String code;
		private String designation;
		private String quantity;
		private String total;
		
		private boolean bold;
		
		private final List<String> designationChunks = new ArrayList<String>();
		
		private float height;

		private boolean centered;
		
		private ReceiptDataInvoiceItem(ReceiptDataTableMetaData receiptMetaData,
				ReceiptDataItemMetaData itemsMetaData) {
			super();
			this.receiptMetaData = receiptMetaData;
			this.itemsMetaData = itemsMetaData;
		}

		private void setCode(String code) {
			this.code = code;
		}

		private void  setDesignation(String designation) {
			this.designation = designation;
		}

		private String getCode() {
			return code;
		}

		private String getDesignation() {
			return designation;
		}

		private void setQuantity(String quantity) {
			this.quantity = quantity;
		}

		private void setTotal(String total) {
			this.total = total;
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
			
			if(!itemsMetaData.isMergeCodeAndDesignation()){
				float desPosX = positionX;
				if(centered){ // move the position
					float boxSize = itemsMetaData.getCode();
					desPosX = positionX + ReceiptDataUtils.centerPrint(code, boxSize, receiptMetaData, bold);
				}
				contentStream.beginText();
				contentStream.moveTextPositionByAmount(desPosX, positionY);
				contentStream.drawString(StringUtils.isBlank(code)?"":code);
				contentStream.endText();
				positionX += itemsMetaData.getCode()+itemsMetaData.getCellMargin();
			}
			
			float desPosY = positionY;
			boolean first = true;
			for (String des : designationChunks) {
				if(StringUtils.isBlank(des)) continue;
				float desPosX = positionX;
				if(centered){ // move the position
					float boxSize = itemsMetaData.getDesignation();
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
				desPosY -= itemsMetaData.getRowHeight();
			}

			positionX += itemsMetaData.getDesignation()+itemsMetaData.getCellMargin();
			float desPosX = positionX;
			float boxSize = itemsMetaData.getQuantity();
			if(centered){ // move the position
				desPosX = positionX + ReceiptDataUtils.centerPrint(quantity, boxSize, receiptMetaData, bold);
			} else {
				desPosX = positionX + ReceiptDataUtils.rightPrint(quantity, boxSize, receiptMetaData, bold);
			}
			contentStream.beginText();
			contentStream.moveTextPositionByAmount(desPosX, positionY);
			contentStream.drawString(StringUtils.isBlank(quantity)?"":quantity);
			contentStream.endText();

			positionX += itemsMetaData.getQuantity()+itemsMetaData.getCellMargin();
			desPosX = positionX;
			boxSize = itemsMetaData.getTotal();
			if(centered){ // move the position
				desPosX = positionX + ReceiptDataUtils.centerPrint(total, boxSize, receiptMetaData, bold);
			} else {
				desPosX = positionX + ReceiptDataUtils.rightPrint(total, boxSize, receiptMetaData, bold);
			}
			contentStream.beginText();
			contentStream.moveTextPositionByAmount(desPosX, positionY);
			contentStream.drawString(StringUtils.isBlank(total)?"":total);
			contentStream.endText();
			
			// Reset simple bold font if needed
			if(bold) contentStream.setFont(receiptMetaData.getFont(), receiptMetaData.getFontSize());

			return desPosY;
		}
		
	}	

}
