package org.adorsys.adaptmt.jpa;

import java.util.ArrayList;
import java.util.Date;
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
public class AptAptmtSearchInput {

	/**
	 * The entity holding search inputs.
	 */
	private AptAptmt entity;

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

	private Date appointmentDt;

	private Date appointmentToDt;

	private Date appointmentFromDt;

	public AptAptmt getEntity() {
		return entity;
	}

	public void setEntity(AptAptmt entity) {
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

	public Date getAppointmentDt() {
		return appointmentDt;
	}

	public void setAppointmentDt(Date appointmentDt) {
		this.appointmentDt = appointmentDt;
	}

	public Date getAppointmentToDt() {
		return appointmentToDt;
	}

	public void setAppointmentToDt(Date appointmentToDt) {
		this.appointmentToDt = appointmentToDt;
	}

	public Date getAppointmentFromDt() {
		return appointmentFromDt;
	}

	public void setAppointmentFromDt(Date appointmentFromDt) {
		this.appointmentFromDt = appointmentFromDt;
	}

	public boolean noSpecialParams(){
		return appointmentDt==null && appointmentToDt==null && appointmentFromDt==null;
	}
}
