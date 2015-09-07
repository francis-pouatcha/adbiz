package org.adorsys.adcore.repo;

import org.adorsys.adcore.jpa.CoreAbstEntity;
import org.apache.deltaspike.data.api.EntityManagerDelegate;
import org.apache.deltaspike.data.api.EntityRepository;

public interface CoreAbstEntityRepo<E extends CoreAbstEntity> extends EntityRepository<E, String>, EntityManagerDelegate<E>{
	
}
