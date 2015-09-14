package org.adorsys.adbase.repo;

import org.adorsys.adbase.jpa.PermEntry;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PermEntry.class)
public interface PermEntryRepository extends CoreAbstIdentifRepo<PermEntry>
{}
