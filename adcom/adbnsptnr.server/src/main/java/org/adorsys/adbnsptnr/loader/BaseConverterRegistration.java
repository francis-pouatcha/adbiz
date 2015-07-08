package org.adorsys.adbnsptnr.loader;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.adorsys.adbnsptnr.jpa.BpPtnrContactRole;
import org.adorsys.adbnsptnr.jpa.BpPtnrContractElt;
import org.adorsys.adbnsptnr.jpa.BpPtnrIdType;
import org.adorsys.adbnsptnr.jpa.BpPtnrRole;
import org.adorsys.adbnsptnr.jpa.BpPtnrType;
import org.adorsys.adcore.xls.XlsConverterFactory;

@Startup
@Singleton
public class BaseConverterRegistration {

	@Inject
	private BpPtnrContactRoleConverter bpPtnrContactRoleConverter;

	@Inject
	private BpPtnrIdTypeConverter bpPtnrIdTypeConverter;

	@Inject
	private BpPtnrRoleConverter bpPtnrRoleConverter;

	@Inject
	private BpPtnrTypeConverter bpPtnrTypeConverter;
	
	@Inject
	private BpPtnrContractEltConverter bpPtnrContractEltConverter;

	@Inject
	private XlsConverterFactory converterFactory;
	
	@PostConstruct
	public void postConstruct(){
		converterFactory.registerConverter(BpPtnrContactRole.class.getName(), bpPtnrContactRoleConverter);
		converterFactory.registerConverter(BpPtnrIdType.class.getName(), bpPtnrIdTypeConverter);
		converterFactory.registerConverter(BpPtnrRole.class.getName(), bpPtnrRoleConverter);
		converterFactory.registerConverter(BpPtnrType.class.getName(), bpPtnrTypeConverter);
		converterFactory.registerConverter(BpPtnrContractElt.class.getName(), bpPtnrContractEltConverter);
	}
}
