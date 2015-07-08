package org.adorsys.adbase.repo;

import org.adorsys.adbase.jpa.BaseRoleInProcess;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = BaseRoleInProcess.class)
public interface BaseRoleInProcessRepository extends EntityRepository<BaseRoleInProcess, String>{}