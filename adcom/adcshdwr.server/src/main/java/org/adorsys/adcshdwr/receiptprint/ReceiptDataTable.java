package org.adorsys.adcshdwr.receiptprint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;

public class ReceiptDataTable {
	
	private final ReceiptDataTableMetaData tableMetaData;
	private final List<ReceiptDataRow> receiptDataRows = new ArrayList<ReceiptDataRow>();
	
	public ReceiptDataTable(ReceiptDataTableMetaData tableMetaData) {
		super();
		this.tableMetaData = tableMetaData;
	}
	
	public void write(PDPageContentStream contentStream, float docWidth, float docHeight) throws IOException{
		float posY = docHeight - tableMetaData.getMargin()-tableMetaData.getRowHeight();
		for (ReceiptDataRow receiptDataRow : receiptDataRows) {
			posY = receiptDataRow.writeItemLine(contentStream, posY);
		}
	}
	
	public float computeHeight(){
		float height = 0f;
		for (ReceiptDataRow receiptDataRow : receiptDataRows) {
			height+=receiptDataRow.getHeight();
		}
		return height + 4 * tableMetaData.getRowHeight() + 2 * (tableMetaData.getMargin());
	}

	public ReceiptDataTableMetaData getTableMetaData() {
		return tableMetaData;
	}

	public List<ReceiptDataRow> getReceiptDataRows() {
		return receiptDataRows;
	}
	
	

}
