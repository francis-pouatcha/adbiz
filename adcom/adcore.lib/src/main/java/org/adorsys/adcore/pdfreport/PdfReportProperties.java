package org.adorsys.adcore.pdfreport;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.adorsys.adcore.jpa.CoreReportField;

public class PdfReportProperties {

	private String username = "anonymous";
	
	/** The user date. */
	private Date userDate = new Date();
	
	private String userLanguage = "en";
	
	private List<CoreReportField> reportFields = Collections.emptyList();
	
	private int fontSize = 10;
	
	private String fontFamily = "Times-Roman";

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getUserDate() {
		return userDate;
	}

	public void setUserDate(Date userDate) {
		this.userDate = userDate;
	}

	public String getUserLanguage() {
		return userLanguage;
	}

	public void setUserLanguage(String userLanguage) {
		this.userLanguage = userLanguage;
	}

	public List<CoreReportField> getReportFields() {
		return reportFields;
	}

	public void setReportFields(List<CoreReportField> fields) {
		this.reportFields = fields;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public String getFontFamily() {
		return fontFamily;
	}

	public void setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
	}
	
	
}
