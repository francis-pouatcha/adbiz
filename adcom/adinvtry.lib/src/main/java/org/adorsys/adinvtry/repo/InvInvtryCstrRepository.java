package org.adorsys.adinvtry.repo;

import org.adorsys.adcore.repo.CoreAbstEntityCstrRepo;
import org.adorsys.adinvtry.jpa.InvInvtryCstr;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = InvInvtryCstr.class)
public interface InvInvtryCstrRepository extends CoreAbstEntityCstrRepo<InvInvtryCstr>{}
