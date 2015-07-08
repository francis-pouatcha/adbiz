package org.adorsys.adaptmt.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adaptmt.jpa.AptAptmt;
import org.adorsys.adaptmt.jpa.AptAptmtSearchInput;
import org.adorsys.adaptmt.jpa.AptmtStatus;
import org.adorsys.adaptmt.repo.AptAptmtRepository;
import org.adorsys.adaptmt.shedules.AptAptmtOngoingEvent;
import org.adorsys.adbase.jpa.Login;
import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adcore.utils.SequenceGenerator;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

@Stateless
public class AptAptmtEJB {

	@Inject
	private AptAptmtRepository repository;

	@Inject
	private SecurityUtil securityUtil;

	@Inject
	AptAptmtBsPtnrEJB aptAptmtBsPtnrEJB;

	@Inject
	private EntityManager em;

	@Inject
	@AptAptmtOngoingEvent
	private Event<AptAptmt> aptAptmtOngoinEvent;

	private static final String FIND_CUSTOM_QUERY = "SELECT e FROM AptAptmt AS e";
	private static final String COUNT_CUSTOM_QUERY = "SELECT count(e.id) FROM AptAptmt AS e";

	public AptAptmt create(AptAptmt entity) {

		Date now = new Date();

		System.out.println(entity.getAppointmentDate());

		if (checkAptAptmtDate(entity.getAppointmentDate(), now))
			entity.setCreateDate(now);
		else
			entity.setCreateDate(DateUtils.addWeeks(now, 1));

		entity.setAptmtnNbr(SequenceGenerator
				.getSequence(SequenceGenerator.APPOINTMENT_NUMBER_SEQUENCE_PREFIXE));

		Login login = securityUtil.getConnectedUser();
		entity.setCreatedUserId(login.getIdentif());

		AptAptmt aptAptmt = repository.save(attach(entity));
		aptAptmtOngoinEvent.fire(aptAptmt);

		return aptAptmt;
	}

	public AptAptmt deleteById(String id) {
		AptAptmt entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
		}
		return entity;
	}
	
	public AptAptmt close(AptAptmt entity) {
		Date now = new Date();
		entity.setCloseDate(now);
		entity.setStatus(AptmtStatus.CLOSED);
		Login login = securityUtil.getConnectedUser();
		entity.setClosedUserId(login.getIdentif());
		return repository.save(attach(entity));
	}

	public AptAptmt update(AptAptmt entity) {
		return repository.save(attach(entity));
	}

	public AptAptmt findById(String id) {
		return repository.findBy(id);
	}

	public List<AptAptmt> listAll(int start, int max) {
		return repository.findAll(start, max);
	}
	
	public Login loginConnected(){
	    Login login = securityUtil.getConnectedUser();
		return login;
		
	}

	public Long count() {
		return repository.count();
	}

	public List<AptAptmt> findBy(AptAptmt entity, int start, int max,
			SingularAttribute<AptAptmt, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(AptAptmt entity,
			SingularAttribute<AptAptmt, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<AptAptmt> findByLike(AptAptmt entity, int start, int max,
			SingularAttribute<AptAptmt, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	private StringBuilder preprocessQuery(String findOrCount,
			AptAptmtSearchInput searchInput) {
		AptAptmt entity = searchInput.getEntity();

		String whereClause = " WHERE ";
		String andClause = " AND ";

		StringBuilder qBuilder = new StringBuilder(findOrCount);
		boolean whereSet = false;

		if (searchInput.getFieldNames().contains("title")
				&& StringUtils.isNotBlank(entity.getTitle())) {
			if (!whereSet) {
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("e.title = :title");
		}
		if (searchInput.getFieldNames().contains("description")
				&& StringUtils.isNotBlank(entity.getDescription())) {
			if (!whereSet) {
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("e.description = :description");
		}
		if (searchInput.getFieldNames().contains("createdUserId")
				&& StringUtils.isNotBlank(entity.getCreatedUserId())) {
			if (!whereSet) {
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("e.createdUserId = :createdUserId");
		}
		if (searchInput.getFieldNames().contains("closedUserId")
				&& StringUtils.isNotBlank(entity.getClosedUserId())) {
			if (!whereSet) {
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("e.closedUserId = :closedUserId");
		}

		if (searchInput.getAppointmentDt() != null) {
			if (!whereSet) {
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("e.appointmentDate =: appointmentDt");
		}
		if (searchInput.getAppointmentToDt() != null) {
			if (!whereSet) {
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("e.appointmentDate <= :appointmentToDt");
		}

		if (searchInput.getAppointmentFromDt() != null) {
			if (!whereSet) {
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("e.appointmentDate >= :appointmentFromDt");
		}
		return qBuilder;
	}

	public List<AptAptmt> findCustom(AptAptmtSearchInput searchInput) {
		StringBuilder qBuilder = preprocessQuery(FIND_CUSTOM_QUERY, searchInput);
		TypedQuery<AptAptmt> query = em.createQuery(qBuilder.toString(),
				AptAptmt.class);
		setParameters(searchInput, query);

		int start = searchInput.getStart();
		int max = searchInput.getMax();

		if (start < 0)
			start = 0;
		query.setFirstResult(start);
		if (max >= 1)
			query.setMaxResults(max);

		return query.getResultList();
	}

	public void setParameters(AptAptmtSearchInput searchInput, Query query) {
		AptAptmt entity = searchInput.getEntity();

		if (searchInput.getFieldNames().contains("title")
				&& StringUtils.isNotBlank(entity.getTitle())) {
			query.setParameter("title", entity.getTitle());
		}
		if (searchInput.getFieldNames().contains("description")
				&& StringUtils.isNotBlank(entity.getDescription())) {
			query.setParameter("description", entity.getDescription());
		}
		if (searchInput.getFieldNames().contains("createdUserId")
				&& StringUtils.isNotBlank(entity.getCreatedUserId())) {
			query.setParameter("createdUserId", entity.getCreatedUserId());
		}
		if (searchInput.getFieldNames().contains("closedUserId")
				&& StringUtils.isNotBlank(entity.getClosedUserId())) {
			query.setParameter("closedUserId", entity.getClosedUserId());
		}

		if (searchInput.getAppointmentDt() != null) {
			query.setParameter("appointmentDt", searchInput.getAppointmentDt());
		}
		if (searchInput.getAppointmentToDt() != null) {
			query.setParameter("appointmentToDt",
					searchInput.getAppointmentToDt());
		}
		if (searchInput.getAppointmentFromDt() != null) {
			query.setParameter("appointmentFromDt",
					searchInput.getAppointmentFromDt());
		}
	}

	public Long countByLike(AptAptmt entity,
			SingularAttribute<AptAptmt, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	public Long countCustom(AptAptmtSearchInput searchInput) {
		StringBuilder qBuilder = preprocessQuery(COUNT_CUSTOM_QUERY,
				searchInput);
		TypedQuery<Long> query = em
				.createQuery(qBuilder.toString(), Long.class);
		setParameters(searchInput, query);
		return query.getSingleResult();
	}

	private AptAptmt attach(AptAptmt entity) {
		if (entity == null)
			return null;

		return entity;
	}

	public boolean checkAptAptmtDate(Date aptAptmtDate,
			Date aptAptmtCreationDate) {

		if (aptAptmtDate.before(aptAptmtCreationDate))
			return false;
		if (aptAptmtDate.equals(aptAptmtCreationDate))
			return false;

		return true;
	}

	public List<AptAptmt> findPreviousAptAptmt(String id) {
		return repository.findPreviousAptAptmt(id).maxResults(2)
				.getResultList();
	}

	public List<AptAptmt> findNextAptAptmt(String id) {
		return repository.findNextAptAptmt(id).maxResults(2).getResultList();
	}

	public void findAptmtBsPtnr(String aptmtIdentify) {

		// AptAptmtBsPtnr aptBsPtnr = aptAptmtBsPtnrEJB
		// .findAptmtBsPtnr(aptmtIdentify);
		/*
		 * List<BpBnsPtnr> bpBnsPtnrs = new ArrayList<BpBnsPtnr>();
		 * 
		 * for (AptAptmtBsPtnr aptbpBnsPtnr : aptBsPtnr) { String identif =
		 * aptbpBnsPtnr.getBsPtnrIdentify();
		 * bpBnsPtnrs.add(bpBnsPtnrEJB.findById(identif)); }
		 */
		// return aptBsPtnr;
	}

}
