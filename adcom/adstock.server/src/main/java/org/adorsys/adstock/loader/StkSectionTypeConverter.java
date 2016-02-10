package org.adorsys.adstock.loader;

import javax.ejb.Stateless;

import org.adorsys.adcore.xls.AbstractObjectConverter;
import org.adorsys.adstock.jpa.StkSectionType;

@Stateless
public class StkSectionTypeConverter extends AbstractObjectConverter<StkSectionType> {
	@Override
	protected StkSectionType parse(String val) {
		return StkSectionType.valueOf(val);
	}
}
