package org.adorsys.adkeycloak.model.b2.adapters;

import org.adorsys.adcore.b2.jpa.B2Entity;
import org.adorsys.adcore.be.repo.B2AbstractStore;

/**
 * 
 * @author fpo
 *
 * @param <T>
 */
public abstract class AbstractB2Adapter<T extends B2Entity> {

	private B2AbstractStore<T> b2Store;

    public AbstractB2Adapter(B2AbstractStore<T> b2Store) {
        this.b2Store = b2Store;
    }

    protected abstract T getEntity();

    protected void updateEntity() {
    	b2Store.save(getEntity());
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;

        if (o == null || getClass() != o.getClass()) return false;

        @SuppressWarnings("rawtypes")
		AbstractB2Adapter that = (AbstractB2Adapter) o;

        if (getEntity() == null && that.getEntity() == null) return true;
        return getEntity().equals(that.getEntity());
    }

    @Override
    public int hashCode() {
        return getEntity()!=null ? getEntity().hashCode() : super.hashCode();
    }
}
