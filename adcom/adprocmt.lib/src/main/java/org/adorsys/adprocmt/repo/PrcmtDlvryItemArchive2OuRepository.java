package org.adorsys.adprocmt.repo;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItemArchive2Ou;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtDlvryItemArchive2Ou.class)
public interface PrcmtDlvryItemArchive2OuRepository extends CoreAbstIdentifRepo<PrcmtDlvryItemArchive2Ou>{

	public QueryResult<PrcmtDlvryItemArchive2Ou> findByCntnrIdentifAndRcvngOrgUnit(String cntnrIdentif, String rcvngOrgUnit);
}
