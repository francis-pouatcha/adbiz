package org.adorsys.adsales.repo;

import java.util.List;

import org.adorsys.adsales.jpa.SlsInvceEvt;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = SlsInvceEvt.class)
public interface SlsInvceEvtRepository extends EntityRepository<SlsInvceEvt, String>
{

	public List<SlsInvceEvt> findByEvtName(String evtName);
}
