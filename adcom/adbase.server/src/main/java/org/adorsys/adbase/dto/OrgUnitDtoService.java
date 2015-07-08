/**
 * 
 */
package org.adorsys.adbase.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Singleton;
import javax.inject.Inject;

import org.adorsys.adbase.exception.NotFoundOrNotActifEntityException;
import org.adorsys.adbase.jpa.Country;
import org.adorsys.adbase.jpa.OrgUnit;
import org.adorsys.adbase.jpa.OuType;
import org.adorsys.adbase.rest.CountryEJB;
import org.adorsys.adbase.rest.OrgUnitEJB;
import org.adorsys.adbase.rest.OuTypeEJB;
import org.apache.commons.lang3.StringUtils;

/**
 * @author boriswaguia
 *
 */
@Singleton
public class OrgUnitDtoService {

	@Inject
	private OrgUnitEJB orgUnitEJB;
	
	@Inject
	private OuTypeEJB ouTypeEJB;
	
	@Inject
	private CountryEJB countryEJB;
	
	
	public OrgUnitDto convertOrgUnit(String orgUnitIdentif) throws NotFoundOrNotActifEntityException{
		if(StringUtils.isBlank(orgUnitIdentif)){
			throw new IllegalArgumentException();
		}
		OrgUnit orgUnit = orgUnitEJB.findByIdentif(orgUnitIdentif, new Date());
		check(orgUnit);
		return convertOrgUnit(orgUnit);
	}


	public OrgUnitDto convertOrgUnit(OrgUnit orgUnit)
			throws NotFoundOrNotActifEntityException {
		String typeIdentif = orgUnit.getTypeIdentif();
		String ctryIso3 = orgUnit.getCtryIso3();
		OuType ouType = ouTypeEJB.findByIdentif(typeIdentif, new Date());
		check(ouType);
		String typeName = ouType.getTypeName();
		Country country = countryEJB.findByIso3(ctryIso3, new Date());
		check(country);
		String ctrName = country.getName();
		
		OrgUnitDto orgUnitDto = OrgUnitDto.createDto(orgUnit, ctryIso3, typeName, ctrName);
		return orgUnitDto;
	}
	
	public List<OrgUnitDto> convertOrgUnits(List<OrgUnit> orgUnits) throws NotFoundOrNotActifEntityException{
		List<OrgUnitDto> dtos = new ArrayList<OrgUnitDto>();
		for (OrgUnit orgUnit : orgUnits) {
			OrgUnitDto orgUnitDto = convertOrgUnit(orgUnit.getIdentif());
			dtos.add(orgUnitDto);
		}
		return dtos;
	}


	private void check(Object entity)
			throws NotFoundOrNotActifEntityException {
		if(entity == null){
			throw new NotFoundOrNotActifEntityException();
		}
	}
}