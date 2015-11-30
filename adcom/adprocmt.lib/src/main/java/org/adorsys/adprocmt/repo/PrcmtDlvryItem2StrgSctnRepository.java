package org.adorsys.adprocmt.repo;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2StrgSctn;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtDlvryItem2StrgSctn.class)
public interface PrcmtDlvryItem2StrgSctnRepository extends CoreAbstIdentifRepo<PrcmtDlvryItem2StrgSctn>{}
