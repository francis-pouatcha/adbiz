package org.adorsys.adaptmt.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.CoreAbstTimedData;
import org.adorsys.javaext.description.Description;
import org.adorsys.javaext.format.DateFormatPattern;

@Entity
@Description("AptAptmt_description")
public class AptAptmt extends CoreAbstTimedData {
	private static final long serialVersionUID = 5118150729653002565L;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("AptAptmt_createDate_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	private Date createDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Description("AptAptmt_createDate_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	private Date closeDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("AptAptmt_appointmentDate_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	private Date appointmentDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Description("AptAptmt_appointmentDateEnd_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	private Date appointmentDateEnd;

	@Column
	@Description("AptAptmt_aptmtnNbr_description")
	private String aptmtnNbr;

	@Column
	@Description("AptAptmt_createdUserId_description")
	private String createdUserId;

	@Column
	@Description("AptAptmt_closedUserId_description")
	private String closedUserId;

	@Column
	@Description("AptAptmt_status_description")
	private AptmtStatus status;

	@Column
	@NotNull
	@Description("AptAptmt_title_description")
	private String title;

	@Column
	@Description("AptAptmt_description_description")
	private String description;

	@Column
	@Description("AptAptmt_locality_description")
	private String locality;

	@Column
	@Description("AptAptmt_parentIdentify_description")
	private String parentIdentify;
	
	

	public Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(final Date createDate) {
		this.createDate = createDate;
	}

	public String getAptmtnNbr() {
		return aptmtnNbr;
	}

	public void setAptmtnNbr(String aptmtnNbr) {
		this.aptmtnNbr = aptmtnNbr;
	}

	public String getCreatedUserId() {
		return this.createdUserId;
	}

	public void setCreatedUserId(final String createdUserId) {
		this.createdUserId = createdUserId;
	}

	public String getClosedUserId() {
		return this.closedUserId;
	}

	public void setClosedUserId(final String closedUserId) {
		this.closedUserId = closedUserId;
	}

	public AptmtStatus getStatus() {
		return this.status;
	}

	public void setStatus(final AptmtStatus status) {
		this.status = status;
	}

	public Date getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(Date appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	
	public Date getAppointmentDateEnd() {
		return appointmentDateEnd;
	}

	public void setAppointmentDateEnd(Date appointmentDateEnd) {
		this.appointmentDateEnd = appointmentDateEnd;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getLocality() {
		return this.locality;
	}

	public void setLocality(final String locality) {
		this.locality = locality;
	}

	public String getParentIdentify() {
		return this.parentIdentify;
	}

	public void setParentIdentify(final String parentIdentify) {
		this.parentIdentify = parentIdentify;
	}

	

	@Override
	public String toString() {
		return "AptAptmt [createDate=" + createDate + ", closeDate="
				+ closeDate + ", appointmentDate=" + appointmentDate
				+ ", appointmentDateEnd=" + appointmentDateEnd + ", aptmtnNbr="
				+ aptmtnNbr + ", createdUserId=" + createdUserId
				+ ", closedUserId=" + closedUserId + ", status=" + status
				+ ", title=" + title + ", description=" + description
				+ ", locality=" + locality + ", parentIdentify="
				+ parentIdentify + "]";
	}

	@Override
	protected String makeIdentif() {
		return aptmtnNbr;
	}

	@PrePersist
	public void checkStatus() {
		if (status == null)
			this.setStatus(AptmtStatus.FORTHCOMMING);
	}

}
