package org.adorsys.adbnsptnr.loader;

import javax.ejb.Stateless;

import org.adorsys.adbnsptnr.jpa.BpPtnrContractElt;
import org.adorsys.adcore.xls.AbstractObjectConverter;

@Stateless
public class BpPtnrContractEltConverter extends AbstractObjectConverter<BpPtnrContractElt> {
	@Override
	protected BpPtnrContractElt parse(String val) {
		return BpPtnrContractElt.valueOf(val);
	}
}
