package org.adorsys.adinvtry.repo;

import org.adorsys.adcore.repo.CoreAbstBsnsItemRepo;
import org.adorsys.adinvtry.jpa.InvInvtryItemArchive;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = InvInvtryItemArchive.class)
public interface InvInvtryItemArchiveRepo extends CoreAbstBsnsItemRepo<InvInvtryItemArchive> {}
