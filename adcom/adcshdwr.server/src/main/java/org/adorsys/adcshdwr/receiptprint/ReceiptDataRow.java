package org.adorsys.adcshdwr.receiptprint;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;

public interface ReceiptDataRow {
	
    public float getHeight();
	
	public float writeItemLine(PDPageContentStream contentStream, float posX) throws IOException;

}
