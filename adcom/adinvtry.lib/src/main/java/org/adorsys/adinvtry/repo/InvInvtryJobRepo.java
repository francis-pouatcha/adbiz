package org.adorsys.adinvtry.repo;

import org.adorsys.adcore.repo.CoreAbstEntityJobRepo;
import org.adorsys.adinvtry.jpa.InvInvtryJob;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = InvInvtryJob.class)
public interface InvInvtryJobRepo extends CoreAbstEntityJobRepo<InvInvtryJob> {};
