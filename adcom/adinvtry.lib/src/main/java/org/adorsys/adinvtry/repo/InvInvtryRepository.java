package org.adorsys.adinvtry.repo;

import org.adorsys.adcore.repo.CoreAbstBsnsObjectRepo;
import org.adorsys.adinvtry.jpa.InvInvtry;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = InvInvtry.class)
public interface InvInvtryRepository extends CoreAbstBsnsObjectRepo<InvInvtry>
{
}
