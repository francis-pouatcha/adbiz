package org.adorsys.adcore.jpa;

/**
 * Holds an entity and corresponding field descriptions for a search by example
 * call.
 * 
 * @author francis pouatcha
 *
 */
public abstract class CoreAbstIdentifObjectSearchInput<E extends CoreAbstIdentifObject>
		extends CoreSearchInput<E> {

	private String valueDtFrom;
	private String valueDtTo;

	private String identifFrom;
	private String identifTo;

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
