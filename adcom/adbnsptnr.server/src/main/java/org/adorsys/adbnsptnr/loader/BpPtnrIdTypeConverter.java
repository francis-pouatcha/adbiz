package org.adorsys.adbnsptnr.loader;

import javax.ejb.Stateless;

import org.adorsys.adbnsptnr.jpa.BpPtnrIdType;
import org.adorsys.adcore.xls.AbstractObjectConverter;

@Stateless
public class BpPtnrIdTypeConverter extends AbstractObjectConverter<BpPtnrIdType> {
	@Override
	protected BpPtnrIdType parse(String val) {
		return BpPtnrIdType.valueOf(val);
	}
}
