package org.adorsys.adinvtry.loader;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.exceptions.AdRestException;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectSearchInput;
import org.adorsys.adcore.loader.ejb.CorLdrStepCallback;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectLookup;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectManager;
import org.adorsys.adcore.utils.SequenceGenerator;
import org.adorsys.adcore.xls.CoreAbstBsnsitemLoader;
import org.adorsys.adcore.xls.CoreAbstLoader;
import org.adorsys.adcore.xls.PropertyDesc;
import org.adorsys.adcore.xls.StepCallback;
import org.adorsys.adinvtry.api.InvInvtryManager;
import org.adorsys.adinvtry.jpa.InvInvtry;
import org.adorsys.adinvtry.jpa.InvInvtryCstr;
import org.adorsys.adinvtry.jpa.InvInvtryHstry;
import org.adorsys.adinvtry.jpa.InvInvtryItem;
import org.adorsys.adinvtry.jpa.InvJob;
import org.adorsys.adinvtry.jpa.InvStep;
import org.adorsys.adinvtry.rest.InvInvtryLookup;
import org.adorsys.adstock.jpa.StkArticleLot;
import org.adorsys.adstock.jpa.StkLotInSctnStockQty;
import org.adorsys.adstock.rest.StkArt2SectionLookup;
import org.adorsys.adstock.rest.StkArticleLotLookup;
import org.adorsys.adstock.rest.StkLotInSctnStockQtyLookup;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class InvInvtryItemLoader extends CoreAbstBsnsitemLoader<InvInvtry, InvInvtryItem, InvInvtryHstry, InvJob, InvStep, InvInvtryCstr, CoreAbstBsnsObjectSearchInput<InvInvtry>>{
	
	private static Logger LOG = Logger.getLogger(InvInvtryItemLoader.class.getName());

	@Inject
	private InvInvtryManager manager;
	@Inject
	private InvInvtryLookup lookup;
	@EJB
	private InvInvtryItemLoader loader;
	@EJB
	private CorLdrStepCallback stepCallback;
	@Inject
	private StkArt2SectionLookup art2SectionLookup;
	@Inject
	private StkArticleLotLookup articleLotLookup;
	@Inject
	private StkLotInSctnStockQtyLookup lotInSctnStockQtyLookup;

	@Override
	protected InvInvtryItem newObject() {
		return new InvInvtryItem();
	}

	@Override
	protected CoreAbstBsnsObjectManager<InvInvtry, InvInvtryItem, InvInvtryHstry, InvJob, InvStep, InvInvtryCstr, CoreAbstBsnsObjectSearchInput<InvInvtry>> getManager() {
		return manager;
	}

	@Override
	protected CoreAbstBsnsObjectLookup<InvInvtry> getLookup() {
		return lookup;
	}

	@Override
	public InvInvtryItem save(InvInvtryItem invtryItem, List<PropertyDesc> fields) {
		if(!discoverSection(invtryItem)) return null;// For initial inventories. If no section, return false to suspend job.

		if(StringUtils.isBlank(invtryItem.getLotPic())){
			String lotPic = null;
			String artPic = invtryItem.getArtPic();
			Long countByArtPic = articleLotLookup.countByArtPic(artPic);
			if(countByArtPic<=0){// Initializing system. Just assign a lot number.
				lotPic = SequenceGenerator.getSequence(SequenceGenerator.LOT_SEQUENCE_PREFIXE);
			} else {
				int proessed = 0;
				int max = 100;
				StkLotInSctnStockQty lotInStock = null;
				while(proessed<countByArtPic){
					int firstResult = proessed;
					proessed+=max;
					List<StkArticleLot> found = articleLotLookup.findByArtPic(artPic, firstResult, max);
					for (StkArticleLot stkArticleLot : found) {
						// The lot of an article is also supposed to be given. In this case of an intial
						// inventory, we will try to find it out in the storage.
						lotInStock = lotInSctnStockQtyLookup.findLatest(artPic, stkArticleLot.getLotPic(), invtryItem.getSection());
						if(lotInStock!=null)break;
					}
				}
				if(lotInStock==null) {// this is not good. If i have an article lot, there might be some stock somewhere.
					lotPic = SequenceGenerator.getSequence(SequenceGenerator.LOT_SEQUENCE_PREFIXE);
				} else {
					lotPic = lotInStock.getLotPic();
				}
			}
			invtryItem.setLotPic(lotPic);
		}
		return super.save(invtryItem, fields);
	}
	
	/**
	 * We are not expecting an item being counted not to have a section. This means the 
	 * person counting will be able to select the section in which the item is located.
	 * 
	 * Thus something like this happens only during initial inventory, we we assume that
	 * the mapping between article and section is already provided.
	 * 
	 * @param invtryItem
	 */
	private boolean discoverSection(InvInvtryItem invtryItem){
		// CHeck if section exists. If not get section from art to section table.
		if(StringUtils.isBlank(invtryItem.getSection())){
			InvInvtry invtry = lookup.findByIdentif(invtryItem.getCntnrIdentif());
			String sectionCode = art2SectionLookup.discoverSection(invtryItem.getArtPic(), invtry.getSection());
			if(sectionCode!=null){
				invtryItem.setSection(sectionCode);
			}else {
				Long count = art2SectionLookup.count();
				LOG.warning("No configured section for article : " + invtryItem.getArtPic());
				if(count<=0){
					LOG.warning("Stock initialization job might not have run yet. Will suspend inventory job and wait for stock job to run.");
				} else {
					LOG.warning("Stock initialization job might have run. But still missing section for article : " + invtryItem.getArtPic() + "  Will suspend inventory job. Check and setup a section for that article.");
				}
				return false;
			}
		}
		return true;
	}

	@Override
	protected CoreAbstLoader<InvInvtryItem> getLoader() {
		return loader;
	}

	@Override
	protected StepCallback getStepCallback() {
		return stepCallback;
	}

	@Override
	public boolean done(InvInvtryItem last) {
		String cntnrIdentif = last.getCntnrIdentif();
		try {
			manager.close(cntnrIdentif);
			manager.post(cntnrIdentif);
		} catch (AdRestException es){
			throw new IllegalStateException(es);
		}
		return true;
	}
}
