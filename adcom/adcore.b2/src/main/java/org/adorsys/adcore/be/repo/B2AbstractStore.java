package org.adorsys.adcore.be.repo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcore.b2.jpa.B2ByteChunk;
import org.adorsys.adcore.b2.jpa.B2ByteChunk_;
import org.adorsys.adcore.b2.jpa.B2DeleteMarker;
import org.adorsys.adcore.b2.jpa.B2Entity;
import org.adorsys.adcore.b2.jpa.B2PersContent;
import org.adorsys.adcore.b2.jpa.B2PersContent_;
import org.adorsys.adcore.b2.jpa.B2PersHeader;
import org.adorsys.adcore.b2.jpa.NnV;
import org.adorsys.adcore.b2.jpa.NnVLike;
import org.adorsys.adcore.b2.jpa.NnVs;
import org.adorsys.adcore.b2.util.ByteSplitter;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public abstract class B2AbstractStore<T extends B2Entity> {
	
	private static final int CHUNK_SIZE = 1000000; // 1 MB
	
    protected  EntityManager em;
	CriteriaBuilder cb;
    
    private LoadingCache<String, T> cache;
    
    private ObjectMapper mapper = new ObjectMapper();
        
    protected abstract Class<T> getEntityClass();
    protected abstract String getEntityType();
    
    private final int cacheSize;
    
	public B2AbstractStore(EntityManager em, int cacheSize) {
		this.cacheSize = cacheSize;
		this.em = em;
		this.cb = em.getCriteriaBuilder();
		CacheLoader<String, T> cl = new CacheLoader<String, T>(){
			@Override
			public T load(String key) throws Exception {
				return loadFromB2Store(key);
			}
		};
		this.cache = CacheBuilder.newBuilder().maximumSize(cacheSize).build(cl);
		
		mapper = mapper.setVisibility(JsonMethod.ALL, Visibility.NONE);
		mapper = mapper.setVisibility(JsonMethod.FIELD, Visibility.ANY);
	}

	/**
	 * Creating or saving an entity
	 * 
	 * 1- 
	 * @param t
	 */
	public void save(T t) {
		// 1- Exception if the id is not set
		if(t==null || t.getId()==null) throw new IllegalStateException("Missing id for persisten object.");
		
		String key = t.getId();
		
		// 2- Lookup in the database. Also lookup header
		B2PersContent persObject = em.find(B2PersContent.class, key);
		if(persObject==null) persObject = new B2PersContent();
		
		updatePersObj(persObject, t);

		String contentId = UUID.randomUUID().toString();
		List<B2ByteChunk> chunkList = getChunkList(t,contentId);
		if(persObject.getId()==null){
			persObject.setId(t.getId());
			persObject.setContentId(contentId);
			em.persist(persObject);
		} else {
			// current content id
			deleteStaleChunks(persObject.getContentId());
			persObject.setContentId(contentId);
			em.merge(persObject);
		}
		// persist new chunks
		for (B2ByteChunk b2ByteChunk : chunkList) {
			em.persist(b2ByteChunk);
		}			
		// In both cases, refresh.
		cache.refresh(t.getId());
	}
	
	private List<B2ByteChunk> getChunkList(T t, String contentId){
		List<B2ByteChunk> chunkList = new ArrayList<B2ByteChunk>();
		try {
			byte[] bytes = mapper.writeValueAsBytes(t);
			B2ByteChunk lastChunk = null;
			List<byte[]> byteList = ByteSplitter.splitt(bytes, CHUNK_SIZE);
			for (byte[] bs : byteList) {
				lastChunk = newB2ByteChunk(lastChunk, bs, contentId, t.getRootId());
				chunkList.add(lastChunk);
			}
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
		return chunkList;
	}
	
	private B2ByteChunk newB2ByteChunk(B2ByteChunk lastChunk, byte[] bs, String cntnrId, String rootId){
		B2ByteChunk bc = new B2ByteChunk();
		bc.setContent(bs);
		bc.setSize(bs.length);
		bc.setCtnrId(cntnrId);
		bc.setRootId(rootId);
		bc.setId(UUID.randomUUID().toString());
		if(lastChunk!=null){
			bc.setPrevId(lastChunk.getId());
			lastChunk.setNextId(bc.getId());
			bc.setPosition(lastChunk.getPosition()+1);
		}
		return bc;
	}

	public int deleteStaleChunks(String ctnrId){
		CriteriaDelete<B2ByteChunk> criteriaDelete = cb.createCriteriaDelete(B2ByteChunk.class);
		Root<B2ByteChunk> rootDelete = criteriaDelete.from(B2ByteChunk.class);
		criteriaDelete = criteriaDelete.where(cb.equal(rootDelete.get(B2ByteChunk_.ctnrId),ctnrId));
		return em.createQuery(criteriaDelete).executeUpdate();
	}

	/**
	 * Loading an entity.
	 * 
	 * 1- First check and return cached version.
	 * 2- If cached version is null, return null;
	 * 3- IF header is null, then return t. No header maintained.
	 * 4- If header version is higher than entity version, refresh entity.
	 * @param id
	 * @return
	 */
	public T load(String id){
		// 1- First check and return cached version.
		T t = cache.getIfPresent(id);
		if(t==null){
			// first check if existent in the store before loading.
			Long countById = countById(id);
			if(countById<1) return null;
		}
		// triggers loading.
		t = this.cache.getUnchecked(id);
		
		// 2- Shall not happen
		if(t==null) return null;
		
		// 3- IF header is null, then return t. No header maintained.
		B2PersHeader header = em.find(B2PersHeader.class, id);
		if(header==null) return t;
		
		// 4- If header version is higher than entity version, refresh and reload entity.
		if(header.getVersion()>t.getVersion()){
			// refresh
			this.cache.refresh(id);
			// reload t
			t=this.cache.getUnchecked(id);
		}
		return t;
	}
	
	protected B2PersContent loadNative(String id){
		return em.find(B2PersContent.class, id);
	}
	
	public List<T> load(List<String> ids){
		List<T> result = new ArrayList<T>();
		for (String id : ids) {
			T t = load(id);
			if(t!=null) result.add(t);
		}
		return result;
	}
	
	/**
	 * In order to remove an object in a transactionaly safe way, it is important to split the
	 * deletion into many transactions. Each transaction will delete a qty n of objects from the tree.
	 * 
	 * The root should be deleted in the first transaction.
	 * 
	 * @param id
	 */
	public boolean remove(String id){
		if(id==null) return false;
		B2PersContent persObject = em.find(B2PersContent.class, id);
		if(persObject==null) return false;
		
		boolean doDeleteMarker = false;
		if(persObject.getRootId().equals(id)){// exculde root.
			CriteriaQuery<B2PersContent> criteriaQuery = cb.createQuery(B2PersContent.class);
			Root<B2PersContent> root = criteriaQuery.from(B2PersContent.class);
			Predicate equalRoot = cb.equal(root.get(B2PersContent_.rootId), persObject.getRootId());		
			Long rootDependantCount = count(root, equalRoot);
			if(rootDependantCount>1)doDeleteMarker=true;
		} else {
			CriteriaQuery<B2PersContent> criteriaQuery = cb.createQuery(B2PersContent.class);
			Root<B2PersContent> root = criteriaQuery.from(B2PersContent.class);
			Long cntnrDependantCount = count(root, cb.equal(root.get(B2PersContent_.ctnrId), persObject.getCtnrId()));
			int minDepCount = 0;
			if(persObject.getId().equals(persObject.getCtnrId())){
				minDepCount +=1;
			}
			if(cntnrDependantCount>minDepCount)doDeleteMarker=true;
		}

		// Create delete marker.
		if(doDeleteMarker){
			B2DeleteMarker deleteMarker = em.find(B2DeleteMarker.class, id);
			if(deleteMarker==null){
				deleteMarker = new B2DeleteMarker();
				deleteMarker.setExpirDt(new Date());
				deleteMarker.setId(id);
				em.persist(deleteMarker);
			}
		}

		em.remove(persObject);
		deleteStaleChunks(persObject.getContentId());
		removeStrict(id);

		return true;
	}
	
	protected abstract void removeStrict(String entType, String id);

	private List<T> toEntity(List<String> ids){
		List<T> result = new ArrayList<T>();
		for (String id : ids) {
			T t = load(id);
			result.add(t);
		}
		return result;
	}

	protected Long count(NnV... selections){
		CriteriaQuery<Long> query = cb.createQuery(Long.class);
		Root<B2PersContent> root = query.from(B2PersContent.class);
		Predicate[] predicates = processPredicates(root, selections); 
		return count(query, root, predicates);
	}
	protected Long count(NnVs... selections){
		CriteriaQuery<Long> query = cb.createQuery(Long.class);
		Root<B2PersContent> root = query.from(B2PersContent.class);
		Predicate[] predicates = processPredicates(root, selections);
		return count(query, root, predicates);
	}
	protected Long count(Root<B2PersContent> root, Predicate... predicates){
		CriteriaQuery<Long> query = cb.createQuery(Long.class);
		return count(query, root, predicates);
	}

	protected Long count(CriteriaQuery<Long> query, Root<B2PersContent> root, Predicate... predicates){
		return em.createQuery(query.select(cb.count(root.get(B2PersContent_.id)))
				.where(cb.and(predicates))).getSingleResult();
	}
	
	protected Long countById(String id){
		return count(entTypeNV, NnV.inst(B2PersContent_.id, id));
	}

	protected List<T> find(int start, int max, NnV... selections){
		CriteriaQuery<String> query = cb.createQuery(String.class);
		Root<B2PersContent> root = query.from(B2PersContent.class);
		Predicate[] predicates = processPredicates(root, selections); 
		return find(query, root, start, max, predicates);
	}
	protected List<T> find(int start, int max, NnVs... selections){
		CriteriaQuery<String> query = cb.createQuery(String.class);
		Root<B2PersContent> root = query.from(B2PersContent.class);
		Predicate[] predicates = processPredicates(root, selections); 
		return find(query, root, start, max, predicates);
	}
	
	public List<String> findIds(int start, int max, NnV... selections){
		return find(B2PersContent_.id, start, max, selections);
	}

	protected List<String> find(SingularAttribute<? super B2PersContent, String> view, int start, int max, NnV... selections){
		CriteriaQuery<String> query = cb.createQuery(String.class);
		Root<B2PersContent> root = query.from(B2PersContent.class);
		Predicate[] predicates = processPredicates(root, selections); 
		return find(view, query, root, start, max, predicates);
	}
	protected List<String> find(SingularAttribute<? super B2PersContent, String> view, int start, int max, NnVs... selections){
		CriteriaQuery<String> query = cb.createQuery(String.class);
		Root<B2PersContent> root = query.from(B2PersContent.class);
		Predicate[] predicates = processPredicates(root, selections); 
		return find(view, query, root, start, max, predicates);
	}

	private List<String> find(SingularAttribute<? super B2PersContent, String> view, CriteriaQuery<String> query, Root<B2PersContent> root, int start, int max, Predicate... predicates){
		return em.createQuery(query.select(root.get(view))
				.where(cb.and(predicates))).setFirstResult(start).setMaxResults(max).getResultList();
	}
	private List<T> find(CriteriaQuery<String> query, Root<B2PersContent> root, int start, int max, Predicate... predicates){
		List<String> ids = find(B2PersContent_.id, query, root, start, max, predicates);
		return toEntity(ids);
	}
	
	protected List<T> findOrderAsc(int start, int max, SingularAttribute<? super B2PersContent, ?> orderAttribute, NnV... selections){
		CriteriaQuery<String> query = cb.createQuery(String.class);
		Root<B2PersContent> root = query.from(B2PersContent.class);
		Predicate[] predicates = processPredicates(root, selections); 
		return findOrderAsc(query, root, start, max, orderAttribute, predicates);
	}
	protected List<T> findOrderAsc(int start, int max, SingularAttribute<? super B2PersContent, ?> orderAttribute, NnVs... selections){
		CriteriaQuery<String> query = cb.createQuery(String.class);
		Root<B2PersContent> root = query.from(B2PersContent.class);
		Predicate[] predicates = processPredicates(root, selections);
		return findOrderAsc(query, root, start, max, orderAttribute, predicates);
	}
	private List<T> findOrderAsc(CriteriaQuery<String> query, Root<B2PersContent> root, int start, int max, SingularAttribute<? super B2PersContent, ?> orderAttribute, Predicate... predicates){
		List<String> ids = em.createQuery(query.select(root.get(B2PersContent_.id))
				.where(cb.and(predicates)).orderBy(cb.asc(root.get(orderAttribute))))
				.setFirstResult(start).setMaxResults(max).getResultList();
		return toEntity(ids);
	}
	protected List<T> findOrderDesc(int start, int max, SingularAttribute<? super B2PersContent, ?> orderAttribute, NnV... selections){
		CriteriaQuery<String> query = cb.createQuery(String.class);
		Root<B2PersContent> root = query.from(B2PersContent.class);
		Predicate[] predicates = processPredicates(root, selections); 
		return findOrderDesc(query, root, start, max, orderAttribute, predicates);
	}
	protected List<T> findOrderDesc(int start, int max, SingularAttribute<? super B2PersContent, ?> orderAttribute, NnVs... selections){
		CriteriaQuery<String> query = cb.createQuery(String.class);
		Root<B2PersContent> root = query.from(B2PersContent.class);
		Predicate[] predicates = processPredicates(root, selections);
		return findOrderDesc(query, root, start, max, orderAttribute, predicates);
	}
	private List<T> findOrderDesc(CriteriaQuery<String> query, Root<B2PersContent> root, int start, int max, SingularAttribute<? super B2PersContent, ?> orderAttribute, Predicate... predicates){
		List<String> ids = em.createQuery(query.select(root.get(B2PersContent_.id))
				.where(cb.and(predicates)).orderBy(cb.desc(root.get(orderAttribute))))
				.setFirstResult(start).setMaxResults(max).getResultList();
		return toEntity(ids);
	}

	protected List<String> findOrderAsc(SingularAttribute<? super B2PersContent, String> view, int start, int max, SingularAttribute<? super B2PersContent, ?> orderAttribute, NnV... selections){
		CriteriaQuery<String> query = cb.createQuery(String.class);
		Root<B2PersContent> root = query.from(B2PersContent.class);
		Predicate[] predicates = processPredicates(root, selections); 
		return findOrderAsc(view,query, root, start, max, orderAttribute, predicates);
	}
	protected List<String> findOrderAsc(SingularAttribute<? super B2PersContent, String> view, int start, int max, SingularAttribute<? super B2PersContent, ?> orderAttribute, NnVs... selections){
		CriteriaQuery<String> query = cb.createQuery(String.class);
		Root<B2PersContent> root = query.from(B2PersContent.class);
		Predicate[] predicates = processPredicates(root, selections);
		return findOrderAsc(view,query, root, start, max, orderAttribute, predicates);
	}
	private List<String> findOrderAsc(SingularAttribute<? super B2PersContent, String> view, CriteriaQuery<String> query, Root<B2PersContent> root, int start, int max, SingularAttribute<? super B2PersContent, ?> orderAttribute, Predicate... predicates){
		return em.createQuery(query.select(root.get(B2PersContent_.id))
				.where(cb.and(predicates)).orderBy(cb.asc(root.get(orderAttribute))))
				.setFirstResult(start).setMaxResults(max).getResultList();
	}
	
	private Predicate[] processPredicates(Root<B2PersContent> root, List<NnV> selections){
		return processPredicates(root, selections.toArray(new NnV[selections.size()]));
	}
	
	@SuppressWarnings("unchecked")
	private Predicate[] processPredicates(Root<B2PersContent> root, NnV... selections){
		Predicate[] predicates = new Predicate[selections.length]; 
		for (int i = 0; i < selections.length; i++) {
			NnV nnv = selections[i];
			switch (nnv.getOp()) {
			case EQUAL:
				predicates[i] = cb.equal(root.get(nnv.getAttribute()),nnv.getValue()); 
				break;
			case ISNOTNULL:
				predicates[i] = cb.isNotNull(root.get(nnv.getAttribute())); 
				break;
			case ISNULL:
				predicates[i] = cb.isNull(root.get(nnv.getAttribute())); 
				break;
			case LIKE:
				NnVLike nvLike = null;
				if(nnv instanceof NnVLike){
					nvLike = (NnVLike) nnv;
				} else {
					nvLike = new NnVLike((SingularAttribute<? super B2PersContent, String>) nnv.getAttribute(), nnv.getValue()==null?null:nnv.getValue().toString());
				}
				predicates[i] = cb.like(root.get(nvLike.getAttribute()),nvLike.getValue()); 
				break;
			default:
				predicates[i] = cb.equal(root.get(nnv.getAttribute()),nnv.getValue()); 
				break;
			}
		}
		return predicates;
	}

	private Predicate[] processPredicates(Root<B2PersContent> root, NnVs... selectionsList){
		List<Predicate> result = new ArrayList<Predicate>();
		for (NnVs nnVs : selectionsList) {
			Predicate[] processPredicates = processPredicates(root, nnVs.getNnvList());
			if(Predicate.BooleanOperator.OR==nnVs.getCombiner()){
				result.add(cb.or(processPredicates));
			} else {
				for (Predicate predicate : processPredicates) {
					result.add(predicate);
				}
			}
		}
		return result.toArray(new Predicate[result.size()]);
	}

	public void removeStrict(String id){
		// 1- invalidate if in  cache
		this.cache.invalidate(id);
	}
	public void removeStrict(List<String> ids){
		for (String id : ids) {
			removeStrict(id);
		}
	}
	
	private T loadFromB2Store(String id){
		if(id==null) return null;
		B2PersContent p = em.find(B2PersContent.class, id);
		if(p==null) return null;
		
		try {
			byte[] content = loadBytes(p.getContentId(), id);
			if(content==null) return null;
			return mapper.readValue(content, getEntityClass());
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	private byte[] loadBytes(String cntnrId, String entityId) throws IOException{
		B2ByteChunk byteChunk = loadFirstByteChunk(cntnrId, entityId);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bos.write(byteChunk.getContent());
		while(byteChunk.getNextId()!=null){
			B2ByteChunk nextChunk = em.find(B2ByteChunk.class, byteChunk.getId());
			if(nextChunk==null)throwCorruptedContent(entityId);
			if(nextChunk.getPosition()!=byteChunk.getPosition()+1)throwCorruptedContent(entityId);
			byteChunk = nextChunk;
			bos.write(byteChunk.getContent());
		}
		return bos.toByteArray();
	}
	
	private B2ByteChunk loadFirstByteChunk(String cntnrId, String entityId){
		CriteriaQuery<B2ByteChunk> criteriaQuery = cb.createQuery(B2ByteChunk.class);
		Root<B2ByteChunk> root = criteriaQuery.from(B2ByteChunk.class);
		Predicate equalRoot = cb.equal(root.get(B2ByteChunk_.ctnrId), cntnrId);
		Predicate prevIdNv = cb.isNull(root.get(B2ByteChunk_.prevId));
		List<B2ByteChunk> resultList = em.createQuery(criteriaQuery.where(cb.and(equalRoot,prevIdNv))).getResultList();
		if(resultList.size()!=1)throwCorruptedContent(entityId);
		return resultList.iterator().next();
	}
	
	private void throwCorruptedContent(String entityId){
		throw new IllegalStateException("Corrupted content in B2ByteChunk. entityId: " + entityId);
	}
	
	private void updatePersObj(B2PersContent p, T e){
		p.setRootId(e.getRootId());
		p.setCtnrId(e.getCtnrId());
		p.setEntType(e.getEntType());
		p.setGuardId(e.getGuardId());
		p.setIdx1(e.getIdx1());
		p.setIdx2(e.getIdx2());
		p.setIdx3(e.getIdx3());
		p.setIdx4(e.getIdx4());
		p.setIdx5(e.getIdx5());
		p.setIdx6(e.getIdx6());
		if(p.getValueDt()==null)p.setValueDt(new Date());
	}
	
	protected NnV entTypeNV = NnV.inst(B2PersContent_.entType, getEntityType());
	public List<T> listAll() {
		Long count = count(NnV.inst(B2PersContent_.entType, getEntityType()));
		return find(0, count.intValue(), entTypeNV);
	}
	public Long countAll() {
		return count(entTypeNV);
	}
	public List<String> listIds(int start, int max){
		return find(B2PersContent_.id, start, max, entTypeNV);
	}

	public int removeBatch(NnV... selections){
		Long count = count(selections);
		int processed = 0;
		int batchSize = cacheSize;// Just set the batch size to tha cache size.
		
		CriteriaQuery<String> querySelect = cb.createQuery(String.class);
		Root<B2PersContent> rootSelect = querySelect.from(B2PersContent.class);
		Predicate[] predicatesSelect = processPredicates(rootSelect, selections); 		
		while(processed<count){
			int start = processed;
			start+=batchSize;
			List<String> ids = find(B2PersContent_.id, querySelect, rootSelect, start, batchSize, predicatesSelect);
			removeStrict(ids);
		}
		
		CriteriaDelete<B2PersContent> criteriaDelete = cb.createCriteriaDelete(B2PersContent.class);
		Root<B2PersContent> rootDelete = criteriaDelete.from(B2PersContent.class);
		Predicate[] predicatesDelete = processPredicates(rootDelete, selections); 		
		return em.createQuery(criteriaDelete.where(predicatesDelete)).executeUpdate();
	}
	
	public int updateBatch(SingularAttribute<? super B2PersContent, String> attribute, String value, NnV... selections){
		Long count = count(selections);
		int processed = 0;
		int batchSize = cacheSize;// Just set the batch size to tha cache size.

		CriteriaQuery<String> querySelect = cb.createQuery(String.class);
		Root<B2PersContent> rootSelect = querySelect.from(B2PersContent.class);
		Predicate[] predicatesSelect = processPredicates(rootSelect, selections); 		
		while(processed<count){
			int start = processed;
			start+=batchSize;
			List<String> ids = find(B2PersContent_.id, querySelect, rootSelect, start, batchSize, predicatesSelect);
			removeStrict(ids);
		}

		CriteriaUpdate<B2PersContent> criteriaUpdate = cb.createCriteriaUpdate(B2PersContent.class);
		Root<B2PersContent> rootUpdate = criteriaUpdate.from(B2PersContent.class);
		Predicate[] predicatesUpdate = processPredicates(rootUpdate, selections); 		
		
		return em.createQuery(criteriaUpdate.set(attribute, value).where(predicatesUpdate)).executeUpdate();
	}

}
