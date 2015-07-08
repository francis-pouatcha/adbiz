/**
 * 
 */
package org.adorsys.adbase.rest;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Singleton;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.OrgUnit;
import org.adorsys.adbase.jpa.OrgUnitId;
import org.adorsys.adbase.jpa.OuType;

/**
 * @author boriswaguia
 *
 */
@Singleton
public class OrgUnitIdEJB {
	
	
	@Inject
	private OrgUnitEJB orgUnitEJB;
	
	@Inject
	private OuTypeEJB ouTypeEJB;
	
	private static OrgUnitId createOrgUnitId(String identif, Map<String, OuType> ouTypeMap, Map<String, OrgUnit> orgUnitMap) {
		OrgUnitId result = null;
		OrgUnit orgUnit = orgUnitMap.get(identif);
		if(orgUnit==null) return result;
		OrgUnitId childOrgUnitId = new OrgUnitId();
		result = childOrgUnitId;
		childOrgUnitId.setOrgUnit(orgUnit);
		childOrgUnitId.setOuType(ouTypeMap.get(orgUnit.getTypeIdentif()));
		String parentIdentif = orgUnit.getParentIdentif();
		while(parentIdentif!=null){
			OrgUnit parentOrgUnit = orgUnitMap.get(parentIdentif);
			if(parentOrgUnit==null) break;

			parentIdentif = parentOrgUnit.getParentIdentif();
			OrgUnitId parentOrgUnitId = new OrgUnitId();
			parentOrgUnitId.setOrgUnit(parentOrgUnit);
			parentOrgUnitId.setOuType(ouTypeMap.get(parentOrgUnit.getTypeIdentif()));
			childOrgUnitId.setParentId(parentOrgUnitId);
			childOrgUnitId = parentOrgUnitId;
		}
		return result;
	}
	
	public OrgUnitId toOrgUnitId(String ouId){
		List<OrgUnit> ous = orgUnitEJB.findTreeByIdentif(ouId, new Date());
		List<OuType> findActifsFromNow = ouTypeEJB.findActifsFromNow();
		Map<String, OuType> ouTypeMap = new HashMap<String, OuType>();
		for (OuType ouType : findActifsFromNow) {
			ouTypeMap.put(ouType.getIdentif(), ouType);
		}
		Map<String, OrgUnit> orgUnitMap = new HashMap<String, OrgUnit>();
		for (OrgUnit ou : ous) {
			orgUnitMap.put(ou.getIdentif(), ou);
		}
		return createOrgUnitId(ouId, ouTypeMap, orgUnitMap);
	}
	
}
