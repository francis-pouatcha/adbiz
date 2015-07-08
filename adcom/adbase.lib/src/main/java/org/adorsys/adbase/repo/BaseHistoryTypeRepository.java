package org.adorsys.adbase.repo;

import org.adorsys.adbase.jpa.BaseHistoryType;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = BaseHistoryType.class)
public interface BaseHistoryTypeRepository extends EntityRepository<BaseHistoryType, String>{}