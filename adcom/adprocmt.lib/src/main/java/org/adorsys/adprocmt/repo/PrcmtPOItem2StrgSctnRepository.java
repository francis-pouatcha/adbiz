package org.adorsys.adprocmt.repo;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adprocmt.jpa.PrcmtPOItem2StrgSctn;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtPOItem2StrgSctn.class)
public interface PrcmtPOItem2StrgSctnRepository extends CoreAbstIdentifRepo<PrcmtPOItem2StrgSctn>{}
