package org.adorsys.adbase.repo;

import org.adorsys.adbase.jpa.BaseProcessStatus;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = BaseProcessStatus.class)
public interface BaseProcessStatusRepository extends EntityRepository<BaseProcessStatus, String>{}
