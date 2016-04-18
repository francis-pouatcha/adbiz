package org.adorsys.adcshdwr.repo;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcshdwr.jpa.CdrPymntItem;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CdrPymntItem.class)
public interface CdrPymntItemRepo extends CoreAbstIdentifRepo<CdrPymntItem>
{}
