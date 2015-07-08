package org.adorsys.adsales.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class SlsDeliveryEvt extends SlsAbstractDlvrHstry {

	private static final long serialVersionUID = 647242504501685190L;

	@Column
	@NotNull
	private String evtName;

	public String getEvtName() {
		return evtName;
	}

	public void setEvtName(String evtName) {
		this.evtName = evtName;
	}
}