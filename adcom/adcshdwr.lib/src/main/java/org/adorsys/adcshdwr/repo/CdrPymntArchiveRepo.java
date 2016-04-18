package org.adorsys.adcshdwr.repo;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcshdwr.jpa.CdrPymntArchive;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CdrPymntArchive.class)
public interface CdrPymntArchiveRepo extends CoreAbstIdentifRepo<CdrPymntArchive>
{}
