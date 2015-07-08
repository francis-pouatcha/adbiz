package org.adorsys.adbase.repo;

import org.adorsys.adbase.jpa.BaseConfig;
import org.adorsys.adcore.repo.CoreAbstIdentifDataRepo;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = BaseConfig.class)
public interface BaseConfigRepository extends CoreAbstIdentifDataRepo<BaseConfig> {}
