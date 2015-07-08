package org.adorsys.adsales.repo;

import java.util.List;

import org.adorsys.adsales.jpa.SlsInvceEvtLease;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = SlsInvceEvtLease.class)
public interface SlsInvceEvtLeaseRepository extends
		EntityRepository<SlsInvceEvtLease, String> {

	public List<SlsInvceEvtLease> findByEvtId(String evtId);

	public List<SlsInvceEvtLease> findByEvtIdAndHandlerName(String evtId, String handlerName);
}
