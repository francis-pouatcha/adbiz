package org.adorsys.adinvtry.loader;

import javax.ejb.Stateless;

import org.adorsys.adcore.xls.AbstractObjectConverter;
import org.adorsys.adinvtry.jpa.InvInvtryStatus;

@Stateless
public class InvInvtryStatusConverter extends AbstractObjectConverter<InvInvtryStatus> {
	@Override
	protected InvInvtryStatus parse(String val) {
		return InvInvtryStatus.valueOf(val);
	}
}
