package org.adorsys.adinvtry.repo;

import org.adorsys.adcore.repo.CoreAbstEntityJobRepo;
import org.adorsys.adinvtry.jpa.InvJob;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = InvJob.class)
public interface InvJobRepo extends CoreAbstEntityJobRepo<InvJob> {};
