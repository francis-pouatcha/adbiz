package org.adorsys.adbnsptnr.repo;

import java.util.Date;

import org.adorsys.adbnsptnr.jpa.BpPtnrContact;
import org.adorsys.adbnsptnr.jpa.BpPtnrContactRole;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = BpPtnrContact.class)
public interface BpPtnrContactRepository extends EntityRepository<BpPtnrContact, String>
{
	@Query("SELECT e FROM BpPtnrContact AS e WHERE e.identif = ?1 AND e.validFrom <= ?2 AND (e.validTo IS NULL OR e.validTo > ?2)")
	public QueryResult<BpPtnrContact> findByIdentif(String identif, Date validOn);

	@Query("SELECT e FROM BpPtnrContact AS e WHERE e.ptnrNbr = ?1 AND e.cntctRole=?2 AND e.langIso2=?3")
	public QueryResult<BpPtnrContact> findByPtnrNbrAndCntctRoleAndLangIso2(String ptnrNbr, BpPtnrContactRole cntctRole, String langIso2);
}
