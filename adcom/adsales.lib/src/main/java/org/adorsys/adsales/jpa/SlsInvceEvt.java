package org.adorsys.adsales.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.CoreAbstEntityHstry;

@Entity
public class SlsInvceEvt extends CoreAbstEntityHstry {
	
	private static final long serialVersionUID = -5486176829694036284L;

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