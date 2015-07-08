package org.adorsys.adbase.repo;

import org.adorsys.adbase.jpa.BaseSttlmtOp;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = BaseSttlmtOp.class)
public interface BaseSttlmtOpRepository extends EntityRepository<BaseSttlmtOp, String>{}
