package org.adorsys.adcatal.repo;

import org.adorsys.adcatal.jpa.CatalPicMapping;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CatalPicMapping.class)
public interface CatalPicMappingRepository extends CoreAbstIdentifRepo<CatalPicMapping>
{}
