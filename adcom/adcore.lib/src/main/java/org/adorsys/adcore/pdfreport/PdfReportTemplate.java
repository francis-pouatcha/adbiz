package org.adorsys.adcore.pdfreport;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.adorsys.adcore.jpa.CoreReportField;
import org.adorsys.adcore.props.AbstEntiyProps;
import org.adorsys.adcore.utils.DateUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

/**
 * @author guymoyo
 * @param 
 *
 */
public class PdfReportTemplate {

	/* The document. */
	private Document document;
	
	/* The report table. */
	private PdfPTable reportTable;
	
	/* The file or baos if small document.*/
//	private Outpu baos;
	File file;
	
	private PdfReportProperties reportProperties;

	private List<String> fieldNames = Collections.emptyList();

	/** The bold font. */
	private Font boldFont = FontFactory.getFont("Times-Roman", 10, Font.BOLD);
	/** The font. */
	private Font font = FontFactory.getFont("Times-Roman", 10);
	
	private AbstEntiyProps props;

	public PdfReportTemplate withEntityProps(AbstEntiyProps props){
		this.props = props;
		return this;
	}
	
	public PdfReportTemplate withReportProperties(PdfReportProperties reportProperties){
		checkDone();
		this.reportProperties = reportProperties;
		for (CoreReportField reportField : reportProperties.getReportFields()) {
			fieldNames.add(reportField.getFieldName());
		}
		boldFont = FontFactory.getFont(reportProperties.getFontFamily(), reportProperties.getFontSize(), Font.BOLD);
		font = FontFactory.getFont(reportProperties.getFontFamily(), reportProperties.getFontSize());
		return this;
	}

	private PdfReportTemplate setup() throws DocumentException {
		checkDone();
		document = new Document(PageSize.A4.rotate(),5,5,5,5);
		FileOutputStream fos = null;
		try {
			file = File.createTempFile(reportProperties.getUsername() + "_" + props.getEntityClass().getSimpleName() + "_" + java.util.UUID.randomUUID().toString(), "."+ reportProperties.getUserLanguage() +".pdf");
			fos = new FileOutputStream(file);
			PdfWriter.getInstance(document, fos);
		} catch (IOException | DocumentException e) {
			if(fos==null) IOUtils.closeQuietly(fos);
			if(file!=null) FileUtils.deleteQuietly(file);
			throw new IllegalStateException(e);
		}
		
		resetDocument(props);
		
		return this;
	}

	/**
	 * Adds the items.
	 * @param 
	 * 
	 * @param items
	 *            the items
	 * @throws DocumentException 
	 */
	public PdfReportTemplate addItems(List<?> items) throws DocumentException {
		if(document==null) setup();
		for(Object entity:items){
			newTableRow(props.fieldValues(fieldNames, entity));
		}
		return this;
	}

	private boolean done;
	public File build() throws DocumentException{
		checkDone();
		done = true;
		closeDocument();
		return file;
	}
	private void checkDone(){
		if(done) throw new IllegalStateException("report built. No more operation allowed.");
	}

	private void newTableRow(List<String> fieldsValue){
		PdfPCell pdfPCell;
		for(String value:fieldsValue){	
			 pdfPCell = new PdfPCell();
			pdfPCell.setFixedHeight(30f);
			pdfPCell.addElement(new Phrase(value,font));
			reportTable.addCell(pdfPCell);
		}
	}

	/**
	 * Fill table haeder.
	 * 
	 * @throws DocumentException
	 *             the document exception
	 */
	private void fillTableHaeder(AbstEntiyProps props) throws DocumentException {
		int size = reportProperties.getReportFields().size();
		float[] columnRelWidths = new float[size];
		for (int i = 0; i < size; i++) {
			columnRelWidths[i] = reportProperties.getReportFields().get(i).getColumnWidth();
		}
		reportTable = new PdfPTable(columnRelWidths);
		reportTable.setWidthPercentage(100);
		reportTable.setHeaderRows(1);
		
		PdfPCell pdfPCell;
		for(CoreReportField field:reportProperties.getReportFields()){		
			pdfPCell = new PdfPCell();
			pdfPCell.setFixedHeight(38f);
			String headerText = field.getHeaderText();
			String fieldName = field.getFieldName();
			if(StringUtils.isBlank(headerText)){
				headerText = props.i18n(fieldName, reportProperties.getUserLanguage());
			}
			if(StringUtils.isBlank(headerText)){
				headerText = fieldName;
			}
			pdfPCell.addElement(new Phrase(headerText,boldFont));
			reportTable.addCell(pdfPCell);
		}
	}

	private void printReportHeader(AbstEntiyProps props) throws DocumentException {

		Paragraph paragraph = new Paragraph(new Phrase("LISTE "+props.getEntityClass().getSimpleName(),boldFont));
		paragraph.setAlignment(Element.ALIGN_CENTER);
		document.add(paragraph);

		document.add(Chunk.NEWLINE);

		document.add(new LineSeparator());

		paragraph = new Paragraph(new Phrase("Imprime Le  : "+DateUtil.format(reportProperties.getUserDate(),DateUtil.DATE_FORMAT_SHORT))+" Par : "+reportProperties.getUsername());
		paragraph.setAlignment(Element.ALIGN_RIGHT);
		document.add(paragraph);

		document.add(Chunk.NEWLINE);
	}

	/**
	 * Close document.
	 */
	private void closeDocument() {
		try {
			document.add(reportTable);
			document.close();
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * Reset document.
	 * 
	 * @throws DocumentException
	 *             the document exception
	 */
	private void resetDocument(AbstEntiyProps props) throws DocumentException{
		document.open();
		printReportHeader(props);
		fillTableHaeder(props);
	}
}
