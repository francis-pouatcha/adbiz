package org.adorsys.adsales.rest.loader;

import javax.ejb.Stateless;

import org.adorsys.adcore.enums.CoreProcessStatusEnum;
import org.adorsys.adcore.xls.AbstractObjectConverter;

@Stateless
public class BaseProcessStatusConverter extends AbstractObjectConverter<CoreProcessStatusEnum> {
	@Override
	protected CoreProcessStatusEnum parse(String val) {
		return CoreProcessStatusEnum.valueOf(val);
	}
}
