package org.adorsys.adprocmt.repo;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItemArchive2StrgSctn;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtDlvryItemArchive2StrgSctn.class)
public interface PrcmtDlvryItemArchive2StrgSctnRepository extends CoreAbstIdentifRepo<PrcmtDlvryItemArchive2StrgSctn>{

	public QueryResult<PrcmtDlvryItemArchive2StrgSctn> findByCntnrIdentifAndStrgSection(String cntnrIdentif, String strgSection);
}
