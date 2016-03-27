package org.adorsys.adcore.loader.repo;

import org.adorsys.adcore.loader.jpa.CorLdrFileChunk;
import org.adorsys.adcore.repo.CoreAbstIdentifByteChunkRepo;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity=CorLdrFileChunk.class)
public interface CorLdrFileChunkRepo extends
	CoreAbstIdentifByteChunkRepo<CorLdrFileChunk> {
}
