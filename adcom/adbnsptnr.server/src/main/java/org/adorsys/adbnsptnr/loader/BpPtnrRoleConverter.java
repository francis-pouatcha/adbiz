package org.adorsys.adbnsptnr.loader;

import javax.ejb.Stateless;

import org.adorsys.adbnsptnr.jpa.BpPtnrRole;
import org.adorsys.adcore.xls.AbstractObjectConverter;

@Stateless
public class BpPtnrRoleConverter extends AbstractObjectConverter<BpPtnrRole> {
	@Override
	protected BpPtnrRole parse(String val) {
		return BpPtnrRole.valueOf(val);
	}
}
