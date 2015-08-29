package org.adorsys.adbase.repo;

import org.adorsys.adbase.jpa.WorkspaceRestriction;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = WorkspaceRestriction.class)
public interface WorkspaceRestrictionRepository extends CoreAbstIdentifRepo<WorkspaceRestriction>{}
