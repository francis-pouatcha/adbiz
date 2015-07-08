package org.adorsys.adinvtry.repo;

import org.adorsys.adcore.repo.CoreAbstBsnsObjHstryRepo;
import org.adorsys.adinvtry.jpa.InvInvtryHstry;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = InvInvtryHstry.class)
public interface InvInvtryHstryRepository extends CoreAbstBsnsObjHstryRepo<InvInvtryHstry>{}
