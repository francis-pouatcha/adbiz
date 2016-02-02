package org.adorsys.adkeycloak.model.b2.repo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcore.b2.jpa.B2PersContent;
import org.adorsys.adcore.b2.jpa.NnV;
import org.adorsys.adcore.b2.jpa.NnVLike;
import org.adorsys.adcore.b2.jpa.NnVs;
import org.adorsys.adcore.b2.jpa.Operation;
import org.adorsys.adkeycloak.model.b2.adapters.B2UserProvider;
import org.adorsys.adkeycloak.model.b2.jpa.B2UserEntity;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * Information to be protected:
 *  - username/password pair
 *  - email/password pair
 * @author fpo
 *
 */
public class UserStore extends B2AbstractUserStore<B2UserEntity> {
	
	/*
	 * The username index cache.
	 */
    private Cache<String, String> usernamecache;
	/*
	 * The email index cache.
	 */
    private Cache<String, String> emailcache;

	public UserStore(B2UserProvider userProvider, EntityManager em, int cacheSize) {
		super(userProvider, em, cacheSize);
		this.usernamecache = CacheBuilder.newBuilder().maximumSize(cacheSize*100).build();
		this.emailcache = CacheBuilder.newBuilder().maximumSize(cacheSize*100).build();
	}

	@Override
	protected Class<B2UserEntity> getEntityClass() {
		return B2UserEntity.class;
	}
	@Override
	protected String getEntityType() {
		return B2UserEntity.ENT_TYPE;
	}

	public B2UserEntity findByRealmIdAndUsername(String realmId, String username) {
		if(realmId==null || username==null) return null;

		String usernameIndex = B2UserEntity.makeUserNameIndex(realmId, username);
		// First look into id cache.
		String userId = usernamecache.getIfPresent(usernameIndex);
		
		if(userId!=null) {
			B2UserEntity userEntity = load(userId);
			if(userEntity!=null && 
					realmId.equals(userEntity.getRealmId())  && 
					username.equals(userEntity.getUsername())) return userEntity;
			
			// droop cache entry
			usernamecache.invalidate(usernameIndex);
		}

		NnV realmIdNV = NnV.inst(B2UserEntity.getRealmIdField(), realmId);
		NnV usernameIdNV = NnV.inst(B2UserEntity.getUsernameField(), username);
		List<B2UserEntity> entities = find(0, 1, entTypeNV, realmIdNV, usernameIdNV);
		if(entities.isEmpty()) return null;
		B2UserEntity userEntity = entities.iterator().next();
		// Put in cache
		usernamecache.put(usernameIndex, userEntity.getId());
		return userEntity;
	}

	public B2UserEntity findByRealmIdAndEmail(String realmId, String email) {
		if(realmId==null || email==null) return null;

		String emailIndex = B2UserEntity.makeEmailIndex(realmId, email);
		// First look into id cache.
		String userId = emailcache.getIfPresent(emailIndex);
		
		if(userId!=null) {
			B2UserEntity userEntity = load(userId);
			if(userEntity!=null && 
					realmId.equals(userEntity.getRealmId())  && 
					email.equals(userEntity.getEmail())) return userEntity;
			
			// droop cache entry
			emailcache.invalidate(emailIndex);
		}

		NnV realmIdNV = NnV.inst(B2UserEntity.getRealmIdField(), realmId);
		NnV emailIdNV = NnV.inst(B2UserEntity.getEmailField(), email);
		List<B2UserEntity> entities = find(0, 1, entTypeNV, realmIdNV, emailIdNV);
		if(entities.isEmpty()) return null;
		B2UserEntity userEntity = entities.iterator().next();
		// Put in cache
		emailcache.put(emailIndex, userEntity.getId());
		return userEntity;
	}

	public B2UserEntity findByRealmIdAndServiceAccountClientLink(String realmId, String serviceAccountClientLink) {
		if(realmId==null || serviceAccountClientLink==null) return null;

		NnV realmIdNV = NnV.inst(B2UserEntity.getRealmIdField(), realmId);
		NnV serviceAccountdNV = NnV.inst(B2UserEntity.getServiceAccountClientLinkField(), serviceAccountClientLink);
		List<B2UserEntity> entities = find(0, 1, entTypeNV, realmIdNV, serviceAccountdNV);
		if(entities.isEmpty()) return null;
		return entities.iterator().next();
	}

	public Long countByRealmId(String realmId) {
		if(realmId==null) return 0L;
		NnV realmIdNV = NnV.inst(B2UserEntity.getRealmIdField(), realmId);
		return count(entTypeNV,realmIdNV);
	}

	public Long countByRealmIdIncludeServiceAccount(String realmId,
			boolean includeServiceAccounts) {
		if(realmId==null) return 0l;

		NnV realmIdNV = NnV.inst(B2UserEntity.getRealmIdField(), realmId);
		if(includeServiceAccounts){
			return count(entTypeNV, realmIdNV);
		} else {
			NnV serviceAccountNV = NnV.inst(B2UserEntity.getServiceAccountClientLinkField(), null, Operation.ISNULL);
			return count(entTypeNV, realmIdNV, serviceAccountNV);
		}
	}

	public List<B2UserEntity> findByRealmIdIncludeServiceAccountOrderByUsername(String realmId,
			boolean includeServiceAccounts, int firstResult, int maxResults) {
		if(realmId==null) return Collections.emptyList();

		NnV realmIdNV = NnV.inst(B2UserEntity.getRealmIdField(), realmId);
		SingularAttribute<B2PersContent, String> sortByUsername = B2UserEntity.getUsernameField();
		if(includeServiceAccounts){
			return findOrderAsc(firstResult, maxResults, sortByUsername, entTypeNV, realmIdNV);
		} else {
			NnV serviceAccountNV = NnV.inst(B2UserEntity.getServiceAccountClientLinkField(), null, Operation.ISNULL);
			return findOrderAsc(firstResult, maxResults, sortByUsername, entTypeNV, realmIdNV, serviceAccountNV);
		}
	}
	
	public Long countUsers(String search, String realmId){
    	if(search==null) return 0L;

		NnV serviceAccountNV = NnV.inst(B2UserEntity.getServiceAccountClientLinkField(), null, Operation.ISNULL);
		NnV realmIdNV = NnV.inst(B2UserEntity.getRealmIdField(), realmId);
    	int spaceInd = search.lastIndexOf(" ");
    	// Case when we have search string like "ohn Bow". Then firstName must end with "ohn" AND lastName must start with "bow" (everything case-insensitive)
    	if(search.contains("@")){
    		NnVLike emailLike = new NnVLike(B2UserEntity.getFirstNameField(), search);
    		return count(realmIdNV, serviceAccountNV, emailLike);
    	} 
    	if (spaceInd != -1) {
	    	String firstName = search.substring(0, spaceInd);
	    	String lastName = search.substring(spaceInd + 1);
	    	NnVLike firtsnameLike = new NnVLike(B2UserEntity.getFirstNameField(), firstName);
	    	NnVLike lastnameLike = new NnVLike(B2UserEntity.getLastNameField(), lastName);
    		return count(realmIdNV, serviceAccountNV, firtsnameLike, lastnameLike);
    	} else {
	    	NnVLike firtsnameLike = new NnVLike(B2UserEntity.getFirstNameField(), search);
	    	NnVLike lastnameLike = new NnVLike(B2UserEntity.getLastNameField(), search);
    		return count( 
    				NnVs.inst(realmIdNV), 
    				NnVs.inst(serviceAccountNV), 
    				NnVs.inst(Predicate.BooleanOperator.OR, firtsnameLike, lastnameLike)
    				);
    	}
	}

	public List<B2UserEntity> searchUsers(String realmId, String search, int firstResult, int maxResults){
    	if(search==null) return Collections.emptyList();

		NnV serviceAccountNV = NnV.inst(B2UserEntity.getServiceAccountClientLinkField(), null, Operation.ISNULL);
		NnV realmIdNV = NnV.inst(B2UserEntity.getRealmIdField(), realmId);
		SingularAttribute<B2PersContent, String> sortByUsername = B2UserEntity.getUsernameField();
    	int spaceInd = search.lastIndexOf(" ");
    	// Case when we have search string like "ohn Bow". Then firstName must end with "ohn" AND lastName must start with "bow" (everything case-insensitive)
    	if(search.contains("@")){
    		NnVLike emailLike = new NnVLike(B2UserEntity.getFirstNameField(), search);
    		return findOrderAsc(firstResult, maxResults, sortByUsername, realmIdNV, serviceAccountNV, emailLike);
    	} 
    	if (spaceInd != -1) {
	    	String firstName = search.substring(0, spaceInd);
	    	String lastName = search.substring(spaceInd + 1);
	    	NnVLike firtsnameLike = new NnVLike(B2UserEntity.getFirstNameField(), firstName);
	    	NnVLike lastnameLike = new NnVLike(B2UserEntity.getLastNameField(), lastName);
    		return findOrderAsc(firstResult, maxResults, sortByUsername, realmIdNV, serviceAccountNV, firtsnameLike, lastnameLike);
    	} else {
	    	NnVLike firtsnameLike = new NnVLike(B2UserEntity.getFirstNameField(), search);
	    	NnVLike lastnameLike = new NnVLike(B2UserEntity.getLastNameField(), search);
    		return findOrderAsc(firstResult, maxResults, sortByUsername,
    				NnVs.inst(realmIdNV), 
    				NnVs.inst(serviceAccountNV), 
    				NnVs.inst(Predicate.BooleanOperator.OR, firtsnameLike, lastnameLike)
    				);
	    	
    	}
	}

	public Long countUsers(String realmId, String username, String firstname, String lastname, String email){
		List<NnV> nnvList = new ArrayList<NnV>();
		nnvList.add(NnV.inst(B2UserEntity.getRealmIdField(), realmId));
		if(username!=null)
			nnvList.add(new NnVLike(B2UserEntity.getFirstNameField(), username));
		if(firstname!=null)
			nnvList.add(new NnVLike(B2UserEntity.getFirstNameField(), firstname));
		if(lastname!=null)
			nnvList.add(new NnVLike(B2UserEntity.getLastNameField(), lastname));
		if(email!=null)
			nnvList.add(new NnVLike(B2UserEntity.getFirstNameField(), email));
		
    	return count(nnvList.toArray(new NnV[nnvList.size()]));
	}	

	public List<B2UserEntity> searchUsers(String realmId, String username, String firstname, String lastname, String email, int firstResult, int maxResults){
		List<NnV> nnvList = new ArrayList<NnV>();
		nnvList.add(NnV.inst(B2UserEntity.getRealmIdField(), realmId));
		if(username!=null)
			nnvList.add(new NnVLike(B2UserEntity.getFirstNameField(), username));
		if(firstname!=null)
			nnvList.add(new NnVLike(B2UserEntity.getFirstNameField(), firstname));
		if(lastname!=null)
			nnvList.add(new NnVLike(B2UserEntity.getLastNameField(), lastname));
		if(email!=null)
			nnvList.add(new NnVLike(B2UserEntity.getFirstNameField(), email));
			
    	SingularAttribute<B2PersContent, String> sortByUsername = B2UserEntity.getUsernameField();
		
    	return findOrderAsc(firstResult, maxResults, sortByUsername, nnvList.toArray(new NnV[nnvList.size()]));
	}

	public void preRemoveByRelamId(String id) {
		// TODO Auto-generated method stub
		
	}	

}
