package org.adorsys.adbase.repo;

import org.adorsys.adbase.jpa.BaseProcStep;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = BaseProcStep.class)
public interface BaseProcStepRepository extends EntityRepository<BaseProcStep, String>{}
