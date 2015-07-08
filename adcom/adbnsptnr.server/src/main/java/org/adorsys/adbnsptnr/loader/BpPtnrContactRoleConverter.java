package org.adorsys.adbnsptnr.loader;

import javax.ejb.Stateless;

import org.adorsys.adbnsptnr.jpa.BpPtnrContactRole;
import org.adorsys.adcore.xls.AbstractObjectConverter;

@Stateless
public class BpPtnrContactRoleConverter extends AbstractObjectConverter<BpPtnrContactRole> {
	@Override
	protected BpPtnrContactRole parse(String val) {
		return BpPtnrContactRole.valueOf(val);
	}
}
