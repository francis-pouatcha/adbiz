package org.adorsys.adinvtry.jpa;

import javax.persistence.MappedSuperclass;

import org.adorsys.adcore.jpa.CoreAbstBsnsObject;
import org.adorsys.javaext.description.Description;

@MappedSuperclass
@Description("InvInvtry_description")
public abstract class InvAbstractInvtry extends CoreAbstBsnsObject {
	private static final long serialVersionUID = -995381668004556176L;
}
