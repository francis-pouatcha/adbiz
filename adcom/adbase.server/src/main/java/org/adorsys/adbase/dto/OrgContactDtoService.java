package org.adorsys.adbase.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Singleton;
import javax.inject.Inject;

import org.adorsys.adbase.exception.NotFoundOrNotActifEntityException;
import org.adorsys.adbase.jpa.Country;
import org.adorsys.adbase.jpa.OrgContact;
import org.adorsys.adbase.jpa.OrgUnit;
import org.adorsys.adbase.rest.CountryEJB;
import org.adorsys.adbase.rest.OrgContactEJB;
import org.adorsys.adbase.rest.OrgUnitEJB;
import org.apache.commons.lang3.StringUtils;

@Singleton
public class OrgContactDtoService {

	@Inject
	private CountryEJB countryEJB;
	
	@Inject
	private OrgContactEJB ocEjb;
	
	@Inject
	private OrgUnitEJB orgUnitEJB;
	public OrgContactDto convert(String contactIdentif) throws NotFoundOrNotActifEntityException{
		Date validOn = new Date();
		OrgContact orgContact = ocEjb.findByIdentif(contactIdentif, validOn);
		return convert(orgContact,validOn);
	}

	public OrgContactDto convert(OrgContact orgContact,Date validOn)
			throws NotFoundOrNotActifEntityException {
		if(orgContact == null ) {
			throw new NotFoundOrNotActifEntityException();
		}
		
		String ctryIso3 = orgContact.getCountry();
		if(StringUtils.isBlank(ctryIso3)) {
			return new OrgContactDto(orgContact);
		}
		
		OrgContactDto dto = new OrgContactDto(orgContact);
		
		Country country = countryEJB.findByIso3(ctryIso3, validOn);
		
		if(country != null) {
			dto.setCtryIdentif(country.getIdentif());
			dto.setCtryIso3(ctryIso3);
			dto.setCountryName(country.getName());
		}

		String ouIdentif = orgContact.getOuIdentif();
		OrgUnit orgUnit = orgUnitEJB.findByIdentif(ouIdentif, validOn);
		if(orgContact != null ){
			dto.setOuIdentif(ouIdentif);
			dto.setOuName(orgUnit.getFullName());
		}
		return dto;
	}
	
	public List<OrgContactDto> convert(List<OrgContact> orgContacts) throws NotFoundOrNotActifEntityException {
		List<OrgContactDto> dtos = new ArrayList<OrgContactDto>();
		Date validOn = new Date();
		for (OrgContact orgContact : orgContacts) {
			OrgContactDto dto = convert(orgContact, validOn);
			dtos.add(dto);
		}
		return dtos;
	}
}
