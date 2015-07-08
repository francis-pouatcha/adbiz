package org.adorsys.adcore.pdfreport;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.adorsys.adcore.props.AbstEntiyProps;
import org.adorsys.adcore.utils.DateUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jgroups.util.UUID;

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
 * @param <T>
 *
 */
public abstract class PdfReportTemplate<T> {
		
	protected abstract AbstEntiyProps<T> getEntiyProps();

	/* The document. */
	private Document document;
	
	/* The report table. */
	private PdfPTable reportTable;
	
	/* The file or baos if small document.*/
//	private Outpu baos;
	File file;
		
	private String username = "anonymous";
	
	/** The user date. */
	private Date userDate = new Date();
	
	private String userLanguage = "en";
	
	private List<String> fieldsName = Collections.emptyList();

	/** The bold font. */
	static Font boldFont = FontFactory.getFont("Times-Roman", 10, Font.BOLD);
	
	/** The font. */
	static Font font = FontFactory.getFont("Times-Roman", 10);

	public PdfReportTemplate<T> withUsername(String username){
		checkDone();
		this.username = username;
		return this;
	}
	public PdfReportTemplate<T> withUserDate(Date date){
		checkDone();
		this.userDate = date;
		return this;
	}
	public PdfReportTemplate<T> withFields(List<String> fields){
		checkDone();
		this.fieldsName = fields;
		return this;
	}
	public PdfReportTemplate<T> withLanguage(String lang){
		checkDone();
		this.userLanguage = lang;
		return this;
	}
	
	public PdfReportTemplate<T> setup() throws DocumentException {
		checkDone();
		document = new Document(PageSize.A4.rotate(),5,5,5,5);
		FileOutputStream fos = null;
		try {
			file = File.createTempFile(username + "_" + getEntiyProps().getEntityClass().getSimpleName() + "_" + UUID.randomUUID().toString(), "."+ userLanguage +".pdf");
			fos = new FileOutputStream(file);
			PdfWriter.getInstance(document, fos);
		} catch (IOException | DocumentException e) {
			if(fos==null) IOUtils.closeQuietly(fos);
			if(file!=null) FileUtils.deleteQuietly(file);
			throw new IllegalStateException(e);
		}
		
		resetDocument();
		
		return this;
	}

	/**
	 * Adds the items.
	 * @param <T>
	 * 
	 * @param items
	 *            the items
	 * @throws DocumentException 
	 */
	public PdfReportTemplate<T> addItems(List<T> items) throws DocumentException {
		if(document==null) setup();
		for(T entity:items){
			newTableRow(getEntiyProps().fieldValues(fieldsName, entity));
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
	private void fillTableHaeder() throws DocumentException {
		int size = fieldsName.size();
	
		reportTable = new PdfPTable(size);
		reportTable.setWidthPercentage(100);
		reportTable.setHeaderRows(1);
		
		PdfPCell pdfPCell;
		for(String fieldName:fieldsName){		
			pdfPCell = new PdfPCell();
			pdfPCell.setFixedHeight(38f);
			pdfPCell.addElement(new Phrase(fieldName,boldFont));
			reportTable.addCell(pdfPCell);
		}
	}

	private void printReportHeader() throws DocumentException {

		Paragraph paragraph = new Paragraph(new Phrase("LISTE "+getEntiyProps().getEntityClass().getSimpleName(),boldFont));
		paragraph.setAlignment(Element.ALIGN_CENTER);
		document.add(paragraph);

		document.add(Chunk.NEWLINE);

		document.add(new LineSeparator());

		paragraph = new Paragraph(new Phrase("Imprime Le  : "+DateUtil.format(userDate,DateUtil.DATE_FORMAT_SHORT))+" Par : "+username);
		paragraph.setAlignment(Element.ALIGN_RIGHT);
		document.add(paragraph);

		document.add(Chunk.NEWLINE);
	}



	/**
	 * The Class StandardText.
	 */
	static class StandardText extends Phrase{
		
		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = -5796192414147292471L;
		
		/**
		 * Instantiates a new standard text.
		 */
		StandardText() {
			super();
			setFont(font);
		}
		
		/**
		 * Instantiates a new standard text.
		 * 
		 * @param text
		 *            the text
		 */
		StandardText(String text) {
			super(text);
			setFont(font);
		}
	}

	/**
	 * The Class BoldText.
	 */
	static class BoldText extends Phrase {
		
		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = -6569891897489003768L;
		
		/**
		 * Instantiates a new bold text.
		 */
		BoldText() {
			super();
			setFont(boldFont);
		}
		
		/**
		 * Instantiates a new bold text.
		 * 
		 * @param text
		 *            the text
		 */
		BoldText(String text) {
			super(text);
			setFont(boldFont);
		}
	}

	/**
	 * The Class RightParagraph.
	 */
	static class RightParagraph extends Paragraph {
		
		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 986392503142787342L;

		/**
		 * Instantiates a new right paragraph.
		 * 
		 * @param phrase
		 *            the phrase
		 */
		public RightParagraph(Phrase phrase) {
			super(phrase);
			setAlignment(Element.ALIGN_RIGHT);
		}

		/**
		 * Instantiates a new right paragraph.
		 * 
		 * @param string
		 *            the string
		 */
		public RightParagraph(String string) {
			this(new Phrase(string));
		}
	}

	/**
	 * Close document.
	 */
	public void closeDocument() {
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
	public void resetDocument() throws DocumentException{
		document.open();
		printReportHeader();
		fillTableHaeder();
	}
}
