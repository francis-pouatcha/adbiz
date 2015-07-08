package org.adorsys.adcore.jpa;

public class CoreSortOrder {

	private String fieldName;
	
	private Boolean ASC = Boolean.TRUE;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Boolean getASC() {
		return ASC;
	}

	public void setASC(Boolean aSC) {
		ASC = aSC;
	}
	
}
