/**
 * 
 */
package org.adorsys.adstock.rest.extension.invtry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adstock.jpa.StkArticleLot;
import org.adorsys.adstock.jpa.StkArticleLot2Ou;
import org.adorsys.adstock.jpa.StkLotStockQty;
import org.adorsys.adstock.rest.StkArticleLot2OuEJB;
import org.adorsys.adstock.rest.StkArticleLot2StrgSctnEJB;
import org.adorsys.adstock.rest.StkArticleLotEJB;
import org.adorsys.adstock.rest.StkLotStockQtyLookup;
import org.apache.commons.lang3.StringUtils;

/**
 * @author boriswaguia
 *
 */
@Stateless
public class StkArticleInvtryIntegrationEJB {

	@Inject
	private StkArticleLotEJB articleLotEJB;
	
	@Inject
	private StkArticleLot2OuEJB articleLot2OuEJB;
	
	@Inject
	private StkArticleLot2StrgSctnEJB articleLot2StrgSctnEJB;
	
	@Inject
	private SecurityUtil securityUtil;
	
	@Inject
	private StkLotStockQtyLookup lotStockQtyEJB;

	public Map<String, StkArticleLot> findArtLotByArticlePic(List<String> artPics) {
		if(artPics == null) artPics = new ArrayList<String>();
		Map<String,StkArticleLot> results = new HashMap<String, StkArticleLot> ();
		
		for (String artPic : artPics) {
			Map<String, StkArticleLot> artLotByArtPicMap = findArtLotByArtPicLike(artPic);
			results.putAll(artLotByArtPicMap);
		}
		return results;
	}

	private Map<String, StkArticleLot> findArtLotByArtPicLike(String artPic) {
		if(StringUtils.isBlank(artPic)) throw new IllegalArgumentException("Invalid Article Pic");
		List<StkArticleLot> artPicLike = articleLotEJB.findByArtPicLike(artPic);
		Map<String, StkArticleLot> artLotByArtPicMap = transformToPicMap(artPicLike);
		return artLotByArtPicMap;
	}

	private Map<String, StkArticleLot> transformToPicMap(
			List<StkArticleLot> artPicLike) {
		HashMap<String,StkArticleLot> map = new HashMap<String, StkArticleLot>(artPicLike.size());
		for (StkArticleLot stkArticleLot : artPicLike) {
			map.put(stkArticleLot.getArtPic(), stkArticleLot);
		}
		return map;
	}
	public Map<String, StkArticleLot2Ou> findArtLot2Ou(List<String> artPics) {
		if(artPics == null) artPics = new ArrayList<String>();
		Map<String,StkArticleLot2Ou> artLot2Ous = new HashMap<String, StkArticleLot2Ou>();
		String ouId = securityUtil.getCurrentOrgUnit().getIdentif();
		for (String artPic : artPics) {
			Map<String, StkArticleLot2Ou> resultMap = findArtLot2OuByArtPic(artPic,ouId);
			artLot2Ous.putAll(resultMap);
		}
		return artLot2Ous;
	}

	/**
	 * findArtLot2OuByArtPic.
	 *
	 * @param artPic
	 * @return
	 */
	private Map<String, StkArticleLot2Ou> findArtLot2OuByArtPic(String artPic,String ouId) {
		if(StringUtils.isBlank(artPic)) throw new IllegalArgumentException("Invalid article pic. Empty pic is not accepted here.");
		if(StringUtils.isBlank(ouId)) ouId = securityUtil.getCurrentOrgUnit().getIdentif();
		List<StkArticleLot2Ou> stkArticleLot2Ous = articleLot2OuEJB.findByArtPicLikeAndOuLike(artPic, ouId);
		return transformStkArticleLot2OusToMap(stkArticleLot2Ous);
	}

	public Map<String, List<StkLotStockQty>> findArtLotQtys(List<String> artPics) {
		if(artPics == null) artPics = new ArrayList<String>();
		Map<String, List<StkLotStockQty>> allArtStkQtys=  new HashMap<String, List<StkLotStockQty>>();
		for (String artPic : artPics) {
			List<StkLotStockQty> artStkQtys =  findArtLotQty(artPic);
			allArtStkQtys.put(artPic, artStkQtys);
		}
		return allArtStkQtys;
	}
	
	
	/**
	 * findArtLotQty.
	 *
	 * @param artPic
	 * @return
	 */
	private List<StkLotStockQty> findArtLotQty(String artPic) {
		List<StkLotStockQty> artLotQtys = lotStockQtyEJB.findByArtPic(artPic);
		return artLotQtys;
	}

	/**
	 * transformStkArticleLot2OusToMap.
	 *
	 * @param stkArticleLot2Ous
	 * @return
	 */
	private Map<String, StkArticleLot2Ou> transformStkArticleLot2OusToMap(
			List<StkArticleLot2Ou> stkArticleLot2Ous) {
		HashMap<String,StkArticleLot2Ou> map = new HashMap<String, StkArticleLot2Ou>(stkArticleLot2Ous.size());
		for (StkArticleLot2Ou stkArticleLot2Ou : stkArticleLot2Ous) {
			map.put(stkArticleLot2Ou.getArtPic(), stkArticleLot2Ou);
		}
		return map;
	}
	
	public ArticleLotSearchResult searchArtStkArtLot(ArtLotSearchInput searchInput) {
		if(searchInput == null) throw new IllegalArgumentException("searchInput should not be null");
		List<String> artPics = searchInput.getArtPics();
		Map<String, StkArticleLot2Ou> artLot2Ous = findArtLot2Ou(artPics);
		Map<String, StkArticleLot> lotByArticlePics = findArtLotByArticlePic(artPics);
		Map<String, List<StkLotStockQty>> artLotQtys = findArtLotQtys(artPics);
		
		List<StkArticleLotDTO> resultList = new ArrayList<StkArticleLotDTO>(artPics.size());
		for (String artPic : artPics) {
			StkArticleLotDTO articleLotDTO = new StkArticleLotDTO();
			StkArticleLot2Ou articleLot2Ou = artLot2Ous.get(artPic);
			StkArticleLot stkArticleLot = lotByArticlePics.get(artPic);
			List<StkLotStockQty> stkLotStkQtys = artLotQtys.get(artPic);
			

			articleLotDTO.setArtPic(artPic);
			articleLotDTO.setArticleLot2Ou(articleLot2Ou);
			articleLotDTO.setStkArticleLot(stkArticleLot);
			articleLotDTO.setStkLotStkQtys(stkLotStkQtys);
			
			resultList.add(articleLotDTO);
		}
		
		ArticleLotSearchResult lotSearchResult = new ArticleLotSearchResult(Long.valueOf(artLot2Ous.size()), resultList, searchInput);
		return lotSearchResult;
	}
}
