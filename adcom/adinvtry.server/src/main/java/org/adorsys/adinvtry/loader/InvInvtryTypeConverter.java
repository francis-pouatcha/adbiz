package org.adorsys.adinvtry.loader;

import javax.ejb.Stateless;

import org.adorsys.adcore.xls.AbstractObjectConverter;
import org.adorsys.adinvtry.jpa.InvInvtryType;

@Stateless
public class InvInvtryTypeConverter extends AbstractObjectConverter<InvInvtryType> {
	@Override
	protected InvInvtryType parse(String val) {
		return InvInvtryType.valueOf(val);
	}
}
