package org.adorsys.adcore.jpa;

import java.util.ArrayList;
import java.util.List;

import org.adorsys.adcore.pdfreport.PdfReportProperties;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeInfo.As;
import org.codehaus.jackson.annotate.JsonTypeInfo.Id;

/**
 * Holds an entity and corresponding field descriptions for a search by example
 * call.
 * 
 * @author francis pouatcha
 *
 */
@JsonTypeInfo(use=Id.CLASS, include=As.PROPERTY, property="className")
public abstract class CoreSearchInput<T> {
	public static final int MAX_MAX = 200;

	/**
	 * The entity holding search inputs.
	 */
	private T entity;

	/**
	 * The start cursor
	 */
	private int start = -1;

	/**
	 * The max number of records to return.
	 */
	private int max = MAX_MAX;

	/**
	 * The field names to be included in the search.
	 */
	private List<String> fieldNames = new ArrayList<String>();

	private List<CoreSortOrder> sortFieldNames = new ArrayList<CoreSortOrder>();

	private PdfReportProperties reportProperties;

	private String className;
	
	protected CoreSearchInput() {
		className = this.getClass().getName();
	}

	public T getEntity() {
		return entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}

	public List<String> getFieldNames() {
		return fieldNames;
	}

	public void setFieldNames(List<String> fieldNames) {
		this.fieldNames = fieldNames;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = checkMax(max);
	}

	public List<CoreSortOrder> getSortFieldNames() {
		return sortFieldNames;
	}

	public void setSortFieldNames(List<CoreSortOrder> sortFieldNames) {
		this.sortFieldNames = sortFieldNames;
	}

	public static final int checkMax(int max) {
		if (max <= 0 || max > MAX_MAX)
			throw new IllegalArgumentException(
					"Max must be more than 0 less than or equals: " + MAX_MAX);
		return max;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public PdfReportProperties getReportProperties() {
		return reportProperties;
	}

	public void setReportProperties(PdfReportProperties reportProperties) {
		this.reportProperties = reportProperties;
	}
}
