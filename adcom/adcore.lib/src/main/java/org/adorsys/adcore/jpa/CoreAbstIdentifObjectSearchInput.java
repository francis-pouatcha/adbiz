package org.adorsys.adcore.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Holds an entity and corresponding field descriptions for a search by example
 * call.
 * 
 * @author francis pouatcha
 *
 */
@XmlRootElement
public abstract class CoreAbstIdentifObjectSearchInput<E extends CoreAbstIdentifObject> {

	/**
	 * The entity holding search inputs.
	 */
	private E entity;

	/**
	 * The start cursor
	 */
	private int start = -1;

	/**
	 * The max number of records to return.
	 */
	private int max = -1;

	/**
	 * The field names to be included in the search.
	 */
	private List<String> fieldNames = new ArrayList<String>();

	private List<CoreSortOrder> sortFieldNames = new ArrayList<CoreSortOrder>();
	
	private String valueDtFrom;
	private String valueDtTo;
	
	private String identifFrom;
	private String identifTo;

	public E getEntity() {
		return entity;
	}

	public void setEntity(E entity) {
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
		this.max = max;
	}

	public List<CoreSortOrder> getSortFieldNames() {
		return sortFieldNames;
	}

	public void setSortFieldNames(List<CoreSortOrder> sortFieldNames) {
		this.sortFieldNames = sortFieldNames;
	}

	public String getValueDtFrom() {
		return valueDtFrom;
	}

	public void setValueDtFrom(String valueDtFrom) {
		this.valueDtFrom = valueDtFrom;
	}

	public String getValueDtTo() {
		return valueDtTo;
	}

	public void setValueDtTo(String valueDtTo) {
		this.valueDtTo = valueDtTo;
	}

	public String getIdentifFrom() {
		return identifFrom;
	}

	public void setIdentifFrom(String identifFrom) {
		this.identifFrom = identifFrom;
	}

	public String getIdentifTo() {
		return identifTo;
	}

	public void setIdentifTo(String identifTo) {
		this.identifTo = identifTo;
	}

}
