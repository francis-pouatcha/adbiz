package org.adorsys.adstock.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.adorsys.adcore.rest.CoreAbstBsnsItemEndpoint;
import org.adorsys.adstock.jpa.StkArticleLot;
import org.adorsys.adstock.jpa.StkArticleLot2StrgSctn;
import org.adorsys.adstock.jpa.StkArticleLotSearchInput;
import org.adorsys.adstock.jpa.StkArticleLotSearchResult;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/stkarticlelots")
public class StkArticleLotEndpoint extends CoreAbstBsnsItemEndpoint<StkArticleLot>{

	@Inject
	private StkArticleLotEJB ejb;
	@Inject
	private StkArticleLotLookup lookup;

	@Inject
	private StkArticleLotDetachHelper detachHelper;

	@Inject
	private StkArticleLot2StrgSctnLookup strgSctnEJB;

	@POST
	@Path("/findBy")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public StkArticleLotSearchResult findBy(StkArticleLotSearchInput searchInput) {
		if (searchInput.isFindByNameRange()) {
			return findArticleLotProducts(searchInput);
		}
		return (StkArticleLotSearchResult) super.findBy(searchInput);
	}

	private StkArticleLotSearchResult findArticleLotProducts(
			StkArticleLotSearchInput searchInput) {
		List<String> artPics = searchInput.getArtPics();
		List<StkArticleLotSearchResult> searchResults = new ArrayList<StkArticleLotSearchResult>(
				artPics.size());
		for (String artPic : artPics) {
			StkArticleLotSearchInput tempSearchInput = new StkArticleLotSearchInput();
			StkArticleLot entity = new StkArticleLot();
			entity.setArtPic(artPic);
			tempSearchInput.setEntity(entity);
			List<String> fieldNames = new ArrayList<String>();
			fieldNames.add("artPic");
			tempSearchInput.setFieldNames(fieldNames);
			SingularAttribute<StkArticleLot, ?>[] attributes = readSeachAttributes(tempSearchInput);
			Long count = lookup.countBy(tempSearchInput.getEntity(), attributes);
			List<StkArticleLot> resultList = lookup.findBy(
					tempSearchInput.getEntity(), tempSearchInput.getStart(),
					tempSearchInput.getMax(), attributes);
			StkArticleLotSearchResult sr = new StkArticleLotSearchResult(count,
					detach(resultList), detach(tempSearchInput));
			StkArticleLotSearchResult tempSearchResult = processSearchResult(
					tempSearchInput, sr);
			searchResults.add(tempSearchResult);
		}
		StkArticleLotSearchResult searchResult = new StkArticleLotSearchResult();
		Long total = 0l;
		List<StkArticleLot> resultList = new ArrayList<StkArticleLot>();
		for (StkArticleLotSearchResult sr : searchResults) {
			total = total + sr.getCount();
			resultList.addAll(sr.getResultList());
		}
		searchResult.setCount(total);
		searchResult.setResultList(resultList);
		return searchResult;
	}

	@POST
	@Path("/findCustom")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public StkArticleLotSearchResult findCustom(
			StkArticleLotSearchInput searchInput) {
		Long count = lookup.countCustom(searchInput);
		List<StkArticleLot> resultList = lookup.findCustom(searchInput);
		return new StkArticleLotSearchResult(count, detach(resultList),
				detach(searchInput));
	}

	private StkArticleLot detach(StkArticleLot entity) {
		return detachHelper.detach(entity);
	}

	private List<StkArticleLot> detach(List<StkArticleLot> list) {
		return detachHelper.detach(list);
	}

	private StkArticleLotSearchInput detach(StkArticleLotSearchInput searchInput) {
		return detachHelper.detach(searchInput);
	}

	private StkArticleLotSearchResult processSearchResult(
			StkArticleLotSearchInput searchInput,
			StkArticleLotSearchResult searchResult) {
		List<StkArticleLot> resultList = searchResult.getResultList();
		Map<String, StkArticleLot2StrgSctn> foundCache = new HashMap<String, StkArticleLot2StrgSctn>();
		if (StringUtils.isNotBlank(searchInput.getSectionCode())) {
			for (StkArticleLot stkArticleLot : resultList) {
				StkArticleLot2StrgSctn sctn = strgSctnEJB
						.findByStrgSectionAndLotPicAndArtPic(
								searchInput.getSectionCode(),
								stkArticleLot.getLotPic(),
								stkArticleLot.getArtPic());
				if (sctn != null)
					putAndCache(foundCache, Arrays.asList(sctn), stkArticleLot);
			}
		} else if (searchInput.isWithStrgSection()) {
			for (StkArticleLot stkArticleLot : resultList) {
				List<StkArticleLot2StrgSctn> sctns = strgSctnEJB
						.findByArtPicAndLotPic(stkArticleLot.getArtPic(),
								stkArticleLot.getLotPic());
				putAndCache(foundCache, sctns, stkArticleLot);
			}
		}
		return searchResult;
	}

	public void putAndCache(Map<String, StkArticleLot2StrgSctn> foundCache,
			List<StkArticleLot2StrgSctn> sctns, StkArticleLot stkArticleLot) {
		for (StkArticleLot2StrgSctn strgSctn : sctns) {
			if (!foundCache.containsKey(strgSctn.getId())) {
				foundCache.put(strgSctn.getId(), strgSctn);
				stkArticleLot.getStrgSctns().add(strgSctn);
			} else {
				if (!stkArticleLot.getStrgSctns().contains(strgSctn)) {
					stkArticleLot.getStrgSctns().add(strgSctn);
				}
			}
		}
	}
}