package org.adorsys.adinvtry.repo;

import org.adorsys.adcore.repo.CoreAbstIdentifObjectHstryRepo;
import org.adorsys.adinvtry.jpa.InvInvtryHstryArchive;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = InvInvtryHstryArchive.class)
public interface InvInvtryHstryArchiveRepo extends CoreAbstIdentifObjectHstryRepo<InvInvtryHstryArchive>{}
