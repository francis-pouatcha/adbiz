package org.adorsys.adbase.repo;

import org.adorsys.adbase.jpa.BaseCountryName;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = BaseCountryName.class)
public interface BaseCountryNameRepository extends EntityRepository<BaseCountryName, String>
{
}
