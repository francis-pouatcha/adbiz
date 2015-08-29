package org.adorsys.adbase.repo;

import org.adorsys.adbase.jpa.SecTerminal;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = SecTerminal.class)
public interface SecTerminalRepository extends CoreAbstIdentifRepo<SecTerminal>{}
