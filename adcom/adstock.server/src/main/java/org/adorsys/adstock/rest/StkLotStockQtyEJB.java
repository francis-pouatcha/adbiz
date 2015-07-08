package org.adorsys.adstock.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adstock.event.StkLotStockQtyEvent;
import org.adorsys.adstock.jpa.StkArticleLot2StrgSctn;
import org.adorsys.adstock.jpa.StkLotStockQty;
import org.adorsys.adstock.repo.StkLotStockQtyRepository;

@Stateless
public class StkLotStockQtyEJB {
	@Inject
	@StkLotStockQtyEvent
	private Event<StkLotStockQty> qtyEvent;

	@Inject
	private StkLotStockQtyRepository repository;
	
	@Inject
	private StkArticleLot2StrgSctnEJB articleLot2StrgSctnEJB;

	public StkLotStockQty create(StkLotStockQty entity) {
		entity = repository.save(attach(entity));
		qtyEvent.fire(entity);
		return entity;
	}

	public StkLotStockQty deleteById(String id) {
		StkLotStockQty entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
		}
		return entity;
	}

	public StkLotStockQty update(StkLotStockQty entity) {
		return repository.save(attach(entity));
	}

	public StkLotStockQty findById(String id) {
		return repository.findBy(id);
	}

	public List<StkLotStockQty> listAll(int start, int max) {
		return repository.findAll(start, max);
	}

	public Long count() {
		return repository.count();
	}

	public List<StkLotStockQty> findBy(StkLotStockQty entity, int start,
			int max, SingularAttribute<StkLotStockQty, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(StkLotStockQty entity,
			SingularAttribute<StkLotStockQty, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<StkLotStockQty> findByLike(StkLotStockQty entity, int start,
			int max, SingularAttribute<StkLotStockQty, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(StkLotStockQty entity,
			SingularAttribute<StkLotStockQty, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private StkLotStockQty attach(StkLotStockQty entity) {
		if (entity == null)
			return null;

		return entity;
	}

	public List<StkLotStockQty> findByArtPicAndLotPic(String artPic,
			String lotPic) {
		return repository.findByArtPicAndLotPic(artPic, lotPic).getResultList();
	}

	public List<StkLotStockQty> findByArtPic(String artPic) {
		return repository.findByArtPic(artPic);
	}

	public StkLotStockQty findLatestQty(String artPic, String lotPic,
			String section) {
		BigDecimal base = BigDecimal.ZERO;
		Date qtyDate = null;
		int seqNbr = 0;
		List<StkLotStockQty> rl = repository
				.findByArtPicAndLotPicAndSectionAndCnsldtd(artPic, lotPic,
						section, Boolean.TRUE).getResultList();
		if (!rl.isEmpty()) {
			StkLotStockQty stockQty = rl.iterator().next();
			base = stockQty.getStockQty();
			seqNbr = stockQty.getSeqNbr();
			qtyDate = stockQty.getQtyDt();
		}
		List<StkLotStockQty> resultList2 = repository
				.findByArtPicAndLotPicAndSectionAndSeq(artPic, lotPic, section,
						seqNbr).orderDesc("seqNbr").getResultList();

		StkLotStockQty oldest = null;
		if (!resultList2.isEmpty())
			oldest = resultList2.iterator().next();
		for (StkLotStockQty s : resultList2) {
			base = base.add(s.getStockQty());
			qtyDate = s.getQtyDt();
		}
		StkLotStockQty stkLotStockQty = new StkLotStockQty();
		stkLotStockQty.setArtPic(artPic);
		stkLotStockQty.setLotPic(lotPic);
		stkLotStockQty.setSection(section);
		if (oldest != null) {
			stkLotStockQty.setParentRcrd(oldest.getId());
			stkLotStockQty.setSeqNbr(oldest.getSeqNbr() + 1);
		}

		stkLotStockQty.setQtyDt(qtyDate != null ? qtyDate : new Date());
		stkLotStockQty.setStockQty(base);
		return stkLotStockQty;
	}

	public List<StkLotStockQty> findLatestQties(String artPic, String lotPic) {
		List<StkLotStockQty> result = new ArrayList<StkLotStockQty>();
		List<StkArticleLot2StrgSctn> sections = articleLot2StrgSctnEJB.findByArtPicAndLotPic(artPic, lotPic);
		for (StkArticleLot2StrgSctn section : sections) {
			StkLotStockQty qty = findLatestQty(artPic, lotPic, section.getStrgSection());
			if(qty!=null) result.add(qty);
		}
		return result ;
	}

	public List<StkLotStockQty> findLatestArtStockQuantities(String artPic, List<String> lotPics) {
		List<StkLotStockQty> result = new ArrayList<StkLotStockQty>();
		for (String lotPic : lotPics) {
			List<StkLotStockQty> qties = findLatestQties(artPic, lotPic);
			for (StkLotStockQty qty : qties) {
				result.add(qty);
			}
		}
		return result;
	}

	public List<StkLotStockQty> findLatestArtStockQuantities(List<StkLotStockQty> models) {
		List<StkLotStockQty> result = new ArrayList<StkLotStockQty>();
		for (StkLotStockQty s : result) {
			StkLotStockQty findLatestQty = findLatestQty(s.getArtPic(),
					s.getLotPic(), s.getSection());
			result.add(findLatestQty);
		}
		return result;
	}

	public StkLotStockQty findBase(String artPic, String lotPic, String section) {
		List<StkLotStockQty> rl = repository
				.findByArtPicAndLotPicAndSectionAndCnsldtd(artPic, lotPic,
						section, Boolean.TRUE).getResultList();
		if (!rl.isEmpty()) {
			return rl.iterator().next();
		}
		return null;
	}

	public List<StkLotStockQty> findByArtPicAndLotPicAndSectionAndSeq(
			String artPic, String lotPic, String section, Integer seqNbr) {
		return repository
				.findByArtPicAndLotPicAndSectionAndSeq(artPic, lotPic, section,
						seqNbr).orderDesc("seqNbr").getResultList();
	}
}
