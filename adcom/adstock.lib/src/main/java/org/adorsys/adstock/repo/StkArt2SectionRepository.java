package org.adorsys.adstock.repo;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adstock.jpa.StkArt2Section;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = StkArt2Section.class)
public interface StkArt2SectionRepository extends CoreAbstIdentifRepo<StkArt2Section>{}
