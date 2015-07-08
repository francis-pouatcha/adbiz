package org.adorsys.adstock.repo;

import org.adorsys.adstock.jpa.StkDlvryItemHstry;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = StkDlvryItemHstry.class)
public interface StkDlvryItemHstryRepository extends EntityRepository<StkDlvryItemHstry, String>
{
}
