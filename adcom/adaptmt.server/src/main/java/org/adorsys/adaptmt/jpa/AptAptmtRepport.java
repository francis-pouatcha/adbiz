package org.adorsys.adaptmt.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.CoreAbstTimedData;

@Entity
public class AptAptmtRepport extends CoreAbstTimedData {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6804688696774500010L;

	@Column
	@NotNull
	private String aptmtRepportnNbr;

	@Column
	@NotNull
	private String title;

	@Column
	private String description;

	@Column
	@NotNull
	private String aptmtIdentify;

	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	@Column
	@NotNull
	private String createUserId;

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

	public String getAptmtIdentify() {
		return this.aptmtIdentify;
	}

	public void setAptmtIdentify(final String aptmtIdentify) {
		this.aptmtIdentify = aptmtIdentify;
	}

	public Date getCreated() {
		return this.created;
	}

	public void setCreated(final Date created) {
		this.created = created;
	}

	public String getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(final String createUserId) {
		this.createUserId = createUserId;
	}

	public String getAptmtRepportnNbr() {
		return aptmtRepportnNbr;
	}

	public void setAptmtRepportnNbr(String aptmtRepportnNbr) {
		this.aptmtRepportnNbr = aptmtRepportnNbr;
	}

	@Override
	public String toString() {
		return "AptAptmtRepport [aptmtRepportnNbr=" + aptmtRepportnNbr
				+ ", title=" + title + ", description=" + description
				+ ", aptmtIdentify=" + aptmtIdentify + ", created=" + created
				+ ", createUserId=" + createUserId + "]";
	}

	@Override
	protected String makeIdentif() {
		return aptmtRepportnNbr;
	}

}