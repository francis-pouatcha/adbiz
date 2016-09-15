package org.adorsys.adcore.jpa;

import java.util.Date;


/**
 * Holds an entity and corresponding field descriptions for a search by example
 * call.
 * 
 * @author francis pouatcha
 *
 */
public class CoreAbstIdentifObjectSearchInput<E extends CoreAbstIdentifObject>
		extends CoreSearchInput<E> {

	private Date valueDtFrom;
	private Date valueDtTo;

	private String identifFrom;
	private String identifTo;
	
	public Date getValueDtFrom() {
		return valueDtFrom;
	}

	public void setValueDtFrom(Date valueDtFrom) {
		this.valueDtFrom = valueDtFrom;
	}

	public Date getValueDtTo() {
		return valueDtTo;
	}

	public void setValueDtTo(Date valueDtTo) {
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
