package org.adorsys.adbase.repo;

import org.adorsys.adbase.jpa.BaseCountryName;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = BaseCountryName.class)
public interface BaseCountryNameRepository extends CoreAbstIdentifRepo<BaseCountryName>{}
