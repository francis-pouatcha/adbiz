package org.adorsys.adcore.loader.repo;

import org.adorsys.adcore.loader.jpa.CorLdrFileStream;
import org.adorsys.adcore.repo.CoreAbstIdentifByteStreamRepo;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity=CorLdrFileStream.class)
public interface CorLdrFileStreamRepo extends
	CoreAbstIdentifByteStreamRepo<CorLdrFileStream> {
}
