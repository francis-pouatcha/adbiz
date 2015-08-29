package org.adorsys.adbase.repo;

import org.adorsys.adbase.jpa.Country;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = Country.class)
public interface CountryRepository extends CoreAbstIdentifRepo<Country>
{
}
