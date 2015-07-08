package org.adorsys.adinvtry.repo;

import org.adorsys.adcore.repo.CoreAbstEvtRepo;
import org.adorsys.adinvtry.jpa.InvInvtryEvt;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = InvInvtryEvt.class)
public interface InvInvtryEvtRepository extends CoreAbstEvtRepo<InvInvtryEvt>{}
