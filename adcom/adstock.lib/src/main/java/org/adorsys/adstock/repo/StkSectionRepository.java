package org.adorsys.adstock.repo;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adstock.jpa.StkSection;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = StkSection.class)
public interface StkSectionRepository extends CoreAbstIdentifRepo<StkSection>{}
