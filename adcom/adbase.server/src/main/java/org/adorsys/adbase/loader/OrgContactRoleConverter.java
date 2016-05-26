package org.adorsys.adbase.loader;

import javax.ejb.Stateless;

import org.adorsys.adbase.jpa.OrgContactRole;
import org.adorsys.adcore.xls.AbstractObjectConverter;

@Stateless
public class OrgContactRoleConverter extends AbstractObjectConverter<OrgContactRole> {
	@Override
	protected OrgContactRole parse(String val) {
		return OrgContactRole.valueOf(val);
	}
}
