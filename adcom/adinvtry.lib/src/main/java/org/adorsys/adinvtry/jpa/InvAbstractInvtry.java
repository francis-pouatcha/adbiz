package org.adorsys.adinvtry.jpa;

import javax.persistence.MappedSuperclass;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstBsnsObject;
import org.apache.commons.lang3.StringUtils;

@MappedSuperclass
@Description("InvInvtry_description")
public abstract class InvAbstractInvtry extends CoreAbstBsnsObject {
	private static final long serialVersionUID = -995381668004556176L;

	@Override
	protected String makeIdentif() {
		if(StringUtils.isBlank(identif)) throw new IllegalStateException("Identifier must be set before creation");
		return identif;
	}
	
	
}
