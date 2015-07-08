package org.adorsys.adbnsptnr.loader;

import javax.ejb.Stateless;

import org.adorsys.adbnsptnr.jpa.BpPtnrType;
import org.adorsys.adcore.xls.AbstractObjectConverter;

@Stateless
public class BpPtnrTypeConverter extends AbstractObjectConverter<BpPtnrType> {
	@Override
	protected BpPtnrType parse(String val) {
		return BpPtnrType.valueOf(val);
	}
}
