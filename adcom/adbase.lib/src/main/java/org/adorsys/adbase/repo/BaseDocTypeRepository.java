package org.adorsys.adbase.repo;

import org.adorsys.adbase.jpa.BaseDocType;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = BaseDocType.class)
public interface BaseDocTypeRepository extends EntityRepository<BaseDocType, String>{}
