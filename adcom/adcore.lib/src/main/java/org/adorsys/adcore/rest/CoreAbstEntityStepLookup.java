package org.adorsys.adcore.rest;

import java.util.Date;
import java.util.List;

import org.adorsys.adcore.jpa.CoreAbstEntityStep;
import org.adorsys.adcore.repo.CoreAbstEntityStepRepo;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;

public abstract class CoreAbstEntityStepLookup<E extends CoreAbstEntityStep>
		extends CoreAbstIdentifLookup<E> {
	protected abstract CoreAbstEntityStepRepo<E> getStepRepo();

	protected CoreAbstIdentifRepo<E> getRepo() {
		return getStepRepo();
	};
//
//	public Long countByCntnrIdentifAndStartedIsNullAndEndedIsNull(
//			String cntnrIdentif) {
//		return getStepRepo().findByCntnrIdentifAndStartedIsNullAndEndedIsNull(cntnrIdentif).count();
//	}
//
//	public List<E> findByCntnrIdentifAndStartedIsNullAndEndedIsNull(
//			String cntnrIdentif, int start, int max) {
//		return getStepRepo().findByCntnrIdentifAndStartedIsNullAndEndedIsNull(cntnrIdentif).firstResult(start)
//				.maxResults(max).getResultList();
//	}

//	public Long countByCntnrIdentifAndStartedIsNotNullAndEndedIsNull(
//			String cntnrIdentif) {
//		return getStepRepo().findByCntnrIdentifAndStartedIsNotNullAndEndedIsNull(cntnrIdentif).count();
//	}
//
//	public List<E> findByCntnrIdentifAndStartedIsNotNullAndEndedIsNull(
//			String cntnrIdentif, int start, int max) {
//		return getStepRepo().findByCntnrIdentifAndStartedIsNotNullAndEndedIsNull(cntnrIdentif).firstResult(start)
//				.maxResults(max).getResultList();
//	}

	public Long countByStartedIsNotNullAndEndedIsNullAndLeaseEndLessThan(
			Date now) {
		return getStepRepo().findByStartedIsNotNullAndEndedIsNullAndLeaseEndLessThan(now).count();
	}

	public List<E> findByStartedIsNotNullAndEndedIsNullAndLeaseEndLessThan(
			Date now, int start, int max) {
		return getStepRepo().findByStartedIsNotNullAndEndedIsNullAndLeaseEndLessThan(now).firstResult(start)
				.maxResults(max).getResultList();
	}
//
//	public Long countByCntnrIdentifAndStartedIsNullAndSchdldStartLessThan(String cntnrIdentif,Date now) {
//		return getStepRepo().findByCntnrIdentifAndStartedIsNullAndSchdldStartLessThan(cntnrIdentif, now).count();
//	}
//
//	public List<E> findByCntnrIdentifAndStartedIsNullAndSchdldStartLessThan(String cntnrIdentif,
//			Date now, int start, int max) {
//		return getStepRepo().findByCntnrIdentifAndStartedIsNullAndSchdldStartLessThan(cntnrIdentif, now).firstResult(start)
//				.maxResults(max).getResultList();
//	}

	public Long countByStartedIsNullAndSchdldStartLessThan(Date now) {
		return getStepRepo().findByStartedIsNullAndSchdldStartLessThan(now).count();
	}

	public List<E> findByStartedIsNullAndSchdldStartLessThan(
			Date now, int start, int max) {
		return getStepRepo().findByStartedIsNullAndSchdldStartLessThan(now).firstResult(start)
				.maxResults(max).getResultList();
	}

	public Long countByCntnrIdentifAndEndedIsNotNull(String cntnrIdentif) {
		return getStepRepo().findByCntnrIdentifAndEndedIsNotNull(cntnrIdentif).count();
	}

	public List<E> findByCntnrIdentifAndEndedIsNotNull(String cntnrIdentif,int start, int max) {
		return getStepRepo().findByCntnrIdentifAndEndedIsNotNull(cntnrIdentif).firstResult(start)
				.maxResults(max).getResultList();
	}
}
