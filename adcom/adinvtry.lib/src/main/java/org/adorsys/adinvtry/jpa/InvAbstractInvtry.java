package org.adorsys.adinvtry.jpa;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstBsnsObject;

@MappedSuperclass
@Description("InvInvtry_description")
public abstract class InvAbstractInvtry extends CoreAbstBsnsObject {
	private static final long serialVersionUID = -995381668004556176L;
	
	@Column
	private String rangeStart;	

	@Column
	private String rangeEnd;
	
	public String getRangeEnd() {
		return rangeEnd;
	}

	public void setRangeEnd(String rangeEnd) {
		this.rangeEnd = rangeEnd;
	}

	public String getRangeStart() {
		return rangeStart;
	}

	public void setRangeStart(String rangeStart) {
		this.rangeStart = rangeStart;
	}
}
