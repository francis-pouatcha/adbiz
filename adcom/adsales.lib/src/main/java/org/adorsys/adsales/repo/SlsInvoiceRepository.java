package org.adorsys.adsales.repo;

import org.adorsys.adsales.jpa.SlsInvoice;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = SlsInvoice.class)
public interface SlsInvoiceRepository extends EntityRepository<SlsInvoice, String>
{
}
