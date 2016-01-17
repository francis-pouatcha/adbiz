package org.adorsys.adkeycloak.model.b2.jpa;

import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcore.b2.jpa.B2Entity;
import org.adorsys.adcore.b2.jpa.B2PersContent;
import org.adorsys.adcore.b2.jpa.B2PersContent_;
import org.keycloak.models.entities.RealmEntity;

public class B2RealmEntity extends RealmEntity implements B2Entity {
	public static final String ENT_TYPE = "realms";

	private int version;

	@Override
	public int getVersion() {
		return version;
	}

	@Override
	public String getCtnrId() {
		return getId();
	}

	@Override
	public String getGuardId() {
		return null;
	}

	@Override
	public String getEntType() {
		return ENT_TYPE;
	}

	@Override
	public String getIdx1() {
		return getName();
	}
	public static SingularAttribute<B2PersContent, String> getNameField(){
		return B2PersContent_.idx1;
	} 

	@Override
	public String getIdx2() {
		return null;
	}

	@Override
	public String getIdx3() {
		return null;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Override
	public String getIdx4() {
		return null;
	}

	@Override
	public String getIdx5() {
		return null;
	}

	@Override
	public String getIdx6() {
		return null;
	}

	@Override
	public String getRootId() {
		return getId();
	}

//	public void afterRemove() {

        // Remove all groups of this realm
//        context.getMongoStore().removeEntities(MongoGroupEntity.class, query, true, context);


        // Remove all roles of this realm
//        context.getMongoStore().removeEntities(MongoRoleEntity.class, query, true, context);

        // Remove all clients of this realm
//        context.getMongoStore().removeEntities(MongoClientEntity.class, query, true, context);
//    }
}
