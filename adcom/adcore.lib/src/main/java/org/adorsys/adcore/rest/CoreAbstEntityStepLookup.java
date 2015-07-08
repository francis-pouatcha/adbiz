package org.adorsys.adcore.rest;

import java.util.Date;
import java.util.List;

import org.adorsys.adcore.jpa.CoreAbstEntityStep;
import org.adorsys.adcore.repo.CoreAbstEntityStepRepo;
import org.adorsys.adcore.repo.CoreAbstIdentifDataRepo;

public abstract class CoreAbstEntityStepLookup<E extends CoreAbstEntityStep>
		extends CoreAbstIdentifiedLookup<E> {
	protected abstract CoreAbstEntityStepRepo<E> getStepRepo();

	protected CoreAbstIdentifDataRepo<E> getRepo() {
		return getStepRepo();
	};

	public Long countByJobIdentif(String jobIdentif) {
		return getStepRepo().findByJobIdentif(jobIdentif).count();
	}

	public List<E> findByJobIdentif(String jobIdentif, int start, int max) {
		return getStepRepo().findByJobIdentif(jobIdentif).firstResult(start)
				.maxResults(max).getResultList();
	}

	public Long countByJobIdentifAndStartedIsNullAndEndedIsNull(
			String jobIdentif) {
		return getStepRepo().findByJobIdentifAndStartedIsNullAndEndedIsNull(jobIdentif).count();
	}

	public List<E> findByJobIdentifAndStartedIsNullAndEndedIsNull(
			String jobIdentif, int start, int max) {
		return getStepRepo().findByJobIdentifAndStartedIsNullAndEndedIsNull(jobIdentif).firstResult(start)
				.maxResults(max).getResultList();
	}

	public Long countByJobIdentifAndStartedIsNotNullAndEndedIsNull(
			String jobIdentif) {
		return getStepRepo().findByJobIdentifAndStartedIsNotNullAndEndedIsNull(jobIdentif).count();
	}

	public List<E> findByJobIdentifAndStartedIsNotNullAndEndedIsNull(
			String jobIdentif, int start, int max) {
		return getStepRepo().findByJobIdentifAndStartedIsNotNullAndEndedIsNull(jobIdentif).firstResult(start)
				.maxResults(max).getResultList();
	}

	public Long countByStartedIsNotNullAndEndedIsNullAndLeaseEndLessThan(
			Date now) {
		return getStepRepo().findByStartedIsNotNullAndEndedIsNullAndLeaseEndLessThan(now).count();
	}

	public List<E> findByStartedIsNotNullAndEndedIsNullAndLeaseEndLessThan(
			Date now, int start, int max) {
		return getStepRepo().findByStartedIsNotNullAndEndedIsNullAndLeaseEndLessThan(now).firstResult(start)
				.maxResults(max).getResultList();
	}

	public Long countByJobIdentifAndStartedIsNullAndSchdldStartLessThan(Date now) {
		return getStepRepo().findByJobIdentifAndStartedIsNullAndSchdldStartLessThan(now).count();
	}

	public List<E> findByJobIdentifAndStartedIsNullAndSchdldStartLessThan(
			Date now, int start, int max) {
		return getStepRepo().findByJobIdentifAndStartedIsNullAndSchdldStartLessThan(now).firstResult(start)
				.maxResults(max).getResultList();
	}
}
