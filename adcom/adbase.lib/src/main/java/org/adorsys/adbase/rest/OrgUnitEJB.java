package org.adorsys.adbase.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.jpa.OrgUnit;
import org.adorsys.adbase.jpa.OuType;
import org.adorsys.adbase.repo.OrgUnitRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class OrgUnitEJB {
	@Inject
	private OrgUnitRepository repository;

	@Inject
	private OuTypeEJB ouTypeEJB;

	@Inject
	private EntityManager em;

	public OrgUnit create(OrgUnit entity) {
		return repository.save(attach(entity));
	}

	public OrgUnit createCustom(OrgUnit entity, String parentId) {
		String typeIdentif = entity.getTypeIdentif();
		OuType ouType = ouTypeEJB.findByIdentif(typeIdentif, new Date());
		Integer idSize = ouType.getIdSize();
		String generatedId = RandomStringUtils.randomAlphanumeric(idSize)
				.toUpperCase();
		generatedId = parentId.concat(generatedId);
		entity.setIdentif(generatedId);
		return create(entity);
	}

	public OrgUnit deleteById(String id) {
		OrgUnit entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
		}
		return entity;
	}

	/**
	 * Update the validTo of the entity, and save.
	 * 
	 * @param id
	 * @return
	 */
	public OrgUnit deleteCustomById(String id) {
		OrgUnit entity = repository.findBy(id);
		if (entity != null) {
			entity.setValidTo(new Date());
			repository.save(entity);
		}
		return entity;
	}

	public OrgUnit update(OrgUnit entity) {
		return repository.save(attach(entity));
	}

	public OrgUnit findById(String id) {
		return repository.findBy(id);
	}

	public List<OrgUnit> listAll(int start, int max) {
		return repository.findAll(start, max);
	}

	public Long count() {
		return repository.count();
	}

	public List<OrgUnit> findBy(OrgUnit entity, int start, int max,
			SingularAttribute<OrgUnit, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(OrgUnit entity,
			SingularAttribute<OrgUnit, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<OrgUnit> findByLike(OrgUnit entity, int start, int max,
			SingularAttribute<OrgUnit, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(OrgUnit entity,
			SingularAttribute<OrgUnit, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private OrgUnit attach(OrgUnit entity) {
		if (entity == null)
			return null;

		return entity;
	}

	public OrgUnit findByIdentif(String identif, Date validOn) {
		List<OrgUnit> resultList = repository.findByIdentif(identif, validOn)
				.orderAsc("validFrom").maxResults(1).getResultList();
		if (resultList.isEmpty())
			return null;
		return resultList.iterator().next();
	}

	public List<OrgUnit> searchOrgUnits(String fullName, String typeIdentif,
			String ctryIso3, Date validFrom, int first, int max) {

		validFrom = validFrom == null ? new Date() : validFrom;

		StringBuilder qBuilder = new StringBuilder(
				"SELECT e FROM OrgUnit AS e WHERE e.validFrom <= :from AND (e.validTo IS NULL OR e.validTo > :from)");
		boolean isFullName = false;
		boolean isTypeIdentif = false;
		boolean isCtryIso3 = false;
		if (StringUtils.isNotBlank(fullName)) {
			qBuilder.append(" AND LOWER(e.fullName) LIKE (LOWER(:fullName))");
			isFullName = true;
		}
		if (StringUtils.isNotBlank(typeIdentif)) {
			qBuilder.append(" AND e.typeIdentif = :typeIdentif");
			isTypeIdentif = true;
		}
		if (StringUtils.isNotBlank(ctryIso3)) {
			qBuilder.append(" AND UPPER(e.ctryIso3) = UPPER(:ctryIso3)");
			isCtryIso3 = true;
		}
		qBuilder.append(" ORDER BY e.validFrom DESC");
		TypedQuery<OrgUnit> query = em.createQuery(qBuilder.toString(),
				OrgUnit.class);

		query.setParameter("from", validFrom);

		if (isFullName) {
			query.setParameter("fullName", "%" + fullName + "%");
		}
		if (isTypeIdentif) {
			query.setParameter("typeIdentif", typeIdentif);
		}
		if (isCtryIso3) {
			query.setParameter("ctryIso3", ctryIso3);
		}

		query.setFirstResult(first).setMaxResults(max);

		return query.getResultList();
	}

	public Long countOrgUnits(String fullName, String typeIdentif,
			String ctryIso3, Date validFrom, int first, int max) {

		validFrom = validFrom == null ? new Date() : validFrom;

		StringBuilder qBuilder = new StringBuilder(
				"SELECT COUNT(e) FROM OrgUnit AS e WHERE e.validFrom <= :from AND (e.validTo IS NULL OR e.validTo > :from)");
		boolean isFullName = false;
		boolean isTypeIdentif = false;
		boolean isCtryIso3 = false;
		if (StringUtils.isNotBlank(fullName)) {
			qBuilder.append(" AND LOWER(e.fullName) LIKE (LOWER(:fullName))");
			isFullName = true;
		}
		if (StringUtils.isNotBlank(typeIdentif)) {
			qBuilder.append(" AND e.typeIdentif = :typeIdentif");
			isTypeIdentif = true;
		}
		if (StringUtils.isNotBlank(ctryIso3)) {
			qBuilder.append(" AND UPPER(e.ctryIso3) = UPPER(:ctryIso3)");
			isCtryIso3 = true;
		}
		TypedQuery<Long> query = em
				.createQuery(qBuilder.toString(), Long.class);

		query.setParameter("from", validFrom);
		if (isFullName) {
			query.setParameter("fullName", "%" + fullName + "%");
		}
		if (isTypeIdentif) {
			query.setParameter("typeIdentif", typeIdentif);
		}
		if (isCtryIso3) {
			query.setParameter("ctryIso3", ctryIso3);
		}
		return query.getSingleResult();
	}

	/**
	 * @param ouIdentif
	 * @param validOn
	 * @return
	 */
	public List<OrgUnit> findActifsByIdentif(String ouIdentif, Date validOn) {
		if (validOn == null) {
			validOn = new Date();
		}
		return repository.findActifsByIdentif(ouIdentif + "%", validOn);
	}

	public List<OrgUnit> findTreeByIdentif(String identif, Date validFrom) {

		validFrom = validFrom == null ? new Date() : validFrom;

		StringBuilder qBuilder = new StringBuilder(
				"SELECT e FROM OrgUnit AS e WHERE e.validFrom <= :from AND (e.validTo IS NULL OR e.validTo > :from)  AND LOWER(e.identif) LIKE (LOWER(:identif))");
		TypedQuery<OrgUnit> query = em.createQuery(qBuilder.toString(),
				OrgUnit.class);
		query.setParameter("from", validFrom);
		query.setParameter("identif", identif+"%");

		query.setMaxResults(100);
		return query.getResultList();
	}

}
