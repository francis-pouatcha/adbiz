package org.adorsys.adcatal.repo;

import org.adorsys.adcatal.jpa.CatalProdFmly;
import org.adorsys.adcore.repo.CoreAbstIdentifDataRepo;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CatalProdFmly.class)
public interface CatalProdFmlyRepo extends CoreAbstIdentifDataRepo<CatalProdFmly>
{}
