package org.adorsys.adbase.repo;

import org.adorsys.adbase.jpa.OrgContact;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = OrgContact.class)
public interface OrgContactRepository extends CoreAbstIdentifRepo<OrgContact>{}
