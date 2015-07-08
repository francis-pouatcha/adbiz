package org.adorsys.adbase.loader;

import javax.ejb.Stateless;

import org.adorsys.adbase.jpa.PermActionEnum;
import org.adorsys.adcore.xls.AbstractObjectConverter;

@Stateless
public class PermActionEnumConverter extends AbstractObjectConverter<PermActionEnum> {
	@Override
	protected PermActionEnum parse(String val) {
		return PermActionEnum.valueOf(val);
	}
}
