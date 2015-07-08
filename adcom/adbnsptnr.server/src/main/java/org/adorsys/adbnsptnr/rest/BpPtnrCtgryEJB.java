package org.adorsys.adbnsptnr.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adbnsptnr.jpa.BpPtnrContract;
import org.adorsys.adbnsptnr.jpa.BpPtnrCtgry;
import org.adorsys.adbnsptnr.jpa.BpPtnrCtgryDtls;
import org.adorsys.adbnsptnr.repo.BpPtnrCtgryRepository;
import org.adorsys.adcore.utils.SequenceGenerator;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class BpPtnrCtgryEJB {

	@Inject
	private BpPtnrCtgryRepository repository;
	@Inject
	private SecurityUtil securityUtil;

	@Inject
	private BpPtnrCtgryDtlsEJB ctgryDtlsEJB;
	@Inject
	private BpPtnrContractEJB ptnrContractJB;

	public BpPtnrCtgry create(BpPtnrCtgry entity) {
		BpPtnrCtgryDtls ctgryDtls = entity.getCtgryDtls();
		BpPtnrCtgryDtls parentCtgryDtls = entity.getParentCtgryDtls();
		if (parentCtgryDtls != null)
			entity.setParentCode(parentCtgryDtls.getCtgryCode());

		if (StringUtils.isBlank(entity.getCtgryCode())) {
			entity.setCtgryCode(SequenceGenerator
					.getSequence(SequenceGenerator.BUSINESS_PARTNER_CATEGORY_SEQUENCE_PREFIXE));
		}
		entity = repository.save(attach(entity));
		String langIso2 = securityUtil.getUserLange();
		if (ctgryDtls != null) {
			ctgryDtls.setCtgryCode(entity.getCtgryCode());
			if (StringUtils.isBlank(ctgryDtls.getLangIso2()))
				ctgryDtls.setLangIso2(langIso2);
			ctgryDtls = ctgryDtlsEJB.create(ctgryDtls);
		}
	

		entity.setCtgryDtls(ctgryDtls);
		if (parentCtgryDtls != null) {
			entity.setParentCtgryDtls(parentCtgryDtls);
		}

		return entity;
	}

	public BpPtnrCtgry deleteByIdentif(String ctgryCode) {
		BpPtnrCtgry entity = findByIdentif(ctgryCode);
		if (entity != null) {
			repository.remove(entity);
			List<BpPtnrCtgryDtls> ctgyDtls = ctgryDtlsEJB
					.findByCtgryCode(ctgryCode);
			for (BpPtnrCtgryDtls bpPtnrCtgryDtls : ctgyDtls) {
				ctgryDtlsEJB.deleteById(bpPtnrCtgryDtls.getId());
			}
			List<BpPtnrContract> ctgryDscnts = ptnrContractJB
					.findByHolderIdentif(ctgryCode);
			for (BpPtnrContract bpCtgryDscnt : ctgryDscnts) {
				ptnrContractJB.deleteById(bpCtgryDscnt.getId());
			}
		}
		return entity;
	}

	public BpPtnrCtgry update(BpPtnrCtgry entity) {
		BpPtnrCtgry bpPtnrCtgry = repository.save(attach(entity));
		return processI18n(bpPtnrCtgry, new Date());
	}

	public BpPtnrCtgry findById(String id) {
		return processI18n(repository.findBy(id), new Date());
	}

	public List<BpPtnrCtgry> listAll(int start, int max) {
		return processI18n(repository.findAll(start, max), new Date());
	}

	public Long count() {
		return repository.count();
	}

	public List<BpPtnrCtgry> findBy(BpPtnrCtgry entity, int start, int max,
			SingularAttribute<BpPtnrCtgry, ?>[] attributes) {
		return processI18n(repository.findBy(entity, start, max, attributes), new Date());
	}

	public Long countBy(BpPtnrCtgry entity,
			SingularAttribute<BpPtnrCtgry, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<BpPtnrCtgry> findByLike(BpPtnrCtgry entity, int start, int max,
			SingularAttribute<BpPtnrCtgry, ?>[] attributes) {
		return processI18n(repository.findByLike(entity, start, max, attributes), new Date());
	}

	public Long countByLike(BpPtnrCtgry entity,
			SingularAttribute<BpPtnrCtgry, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private BpPtnrCtgry attach(BpPtnrCtgry entity) {
		if (entity == null)
			return null;

		return entity;
	}

	public BpPtnrCtgry findByIdentif(String identif) {
		List<BpPtnrCtgry> resultList = repository.findByIdentif(identif).maxResults(1).getResultList();
		if (resultList.isEmpty())
			return null;
		return processI18n(resultList.iterator().next(), new Date());
	}

	private BpPtnrCtgry processI18n(BpPtnrCtgry ptnrCtgry, Date validOn) {
		if (ptnrCtgry == null)
			return null;

		String ctgryCode = ptnrCtgry.getCtgryCode();
		BpPtnrCtgryDtls ctgryDtls = findDetails(ctgryCode, validOn);
		ptnrCtgry.setCtgryDtls(ctgryDtls);

		if (StringUtils.isNotBlank(ptnrCtgry.getParentCode())) {
			ptnrCtgry.setParentCtgryDtls(findDetails(ptnrCtgry.getParentCode(),
					validOn));
		}
		return ptnrCtgry;
	}

	private List<BpPtnrCtgry> processI18n(List<BpPtnrCtgry> list,
			Date validOn) {
		for (BpPtnrCtgry bpPtnrCtgry : list) {
			processI18n(bpPtnrCtgry, validOn);
		}
		return list;
	}

	private BpPtnrCtgryDtls findDetails(String ctgryCode, Date validOn) {
		String langIso2 = securityUtil.getUserLange();

		String identif = BpPtnrCtgryDtls.toIdentif(ctgryCode, langIso2);
		BpPtnrCtgryDtls ctgryDtls = ctgryDtlsEJB
				.findByIdentif(identif, validOn);
		if (ctgryDtls != null)
			return ctgryDtls;
		List<String> userLangePrefs = securityUtil.getUserLangePrefs();
		for (String lp : userLangePrefs) {
			if (StringUtils.equalsIgnoreCase(lp, langIso2))
				continue;
			identif = BpPtnrCtgryDtls.toIdentif(ctgryCode, lp);
			ctgryDtls = ctgryDtlsEJB.findByIdentif(identif, validOn);
			if (ctgryDtls != null)
				return ctgryDtls;
		}
		return null;
	}

}
