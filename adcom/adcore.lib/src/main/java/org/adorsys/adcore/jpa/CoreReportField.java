package org.adorsys.adcore.jpa;

public class CoreReportField {
	private float columnWidth;
	private String fieldName;
	private String headerText;

	public float getColumnWidth() {
		return columnWidth;
	}
	public void setColumnWidth(float columnWidth) {
		this.columnWidth = columnWidth;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getHeaderText() {
		return headerText;
	}
	public void setHeaderText(String headerText) {
		this.headerText = headerText;
	}
}
