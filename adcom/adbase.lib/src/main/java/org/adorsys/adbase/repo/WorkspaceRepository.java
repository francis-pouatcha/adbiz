package org.adorsys.adbase.repo;

import org.adorsys.adbase.jpa.Workspace;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = Workspace.class)
public interface WorkspaceRepository extends CoreAbstIdentifRepo<Workspace>{}
