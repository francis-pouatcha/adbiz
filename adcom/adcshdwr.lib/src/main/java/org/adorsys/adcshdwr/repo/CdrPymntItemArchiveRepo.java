package org.adorsys.adcshdwr.repo;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcshdwr.jpa.CdrPymntItemArchive;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CdrPymntItemArchive.class)
public interface CdrPymntItemArchiveRepo extends CoreAbstIdentifRepo<CdrPymntItemArchive>
{}
