package org.adorsys.adbase.repo;

import org.adorsys.adbase.jpa.RoleEntry;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = RoleEntry.class)
public interface RoleEntryRepository extends CoreAbstIdentifRepo<RoleEntry>{}
