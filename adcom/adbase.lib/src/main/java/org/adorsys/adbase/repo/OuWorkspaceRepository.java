package org.adorsys.adbase.repo;

import org.adorsys.adbase.jpa.OuWorkspace;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = OuWorkspace.class)
public interface OuWorkspaceRepository extends CoreAbstIdentifRepo<OuWorkspace>{}
