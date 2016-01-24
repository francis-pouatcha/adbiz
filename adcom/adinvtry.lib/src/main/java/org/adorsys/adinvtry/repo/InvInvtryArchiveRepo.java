package org.adorsys.adinvtry.repo;

import org.adorsys.adcore.repo.CoreAbstBsnsObjectRepo;
import org.adorsys.adinvtry.jpa.InvInvtry;
import org.adorsys.adinvtry.jpa.InvInvtryArchive;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = InvInvtryArchive.class)
public interface InvInvtryArchiveRepo extends CoreAbstBsnsObjectRepo<InvInvtryArchive>
{
}
