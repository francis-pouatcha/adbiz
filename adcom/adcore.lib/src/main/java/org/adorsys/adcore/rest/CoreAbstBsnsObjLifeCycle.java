package org.adorsys.adcore.rest;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adorsys.adcore.jpa.CoreAbstBsnsObjectHstry;

/**
 * This class defines the life cycle of a business object. Among others:
 * 
 * - The entity status string from which the entity can not be changed any more (FROZEN). 
 * - The entity status string from which the object can not be deleted (erased) anymore (UNDELETABLE).
 * - The entity statuses from which processing event must be broadcasted.
 * 
 * @author francis
 *
 */
public abstract class CoreAbstBsnsObjLifeCycle<H extends CoreAbstBsnsObjectHstry> {

	protected Set<String> freeingStats = new HashSet<String>();
	protected Set<String> undeletableStats = new HashSet<String>();
	protected Set<String> broadcastingStats = new HashSet<String>();

	public boolean isFreezing(H hstry){
		return freeingStats.contains(hstry.getEntStatus());
	}
	public boolean isFreezing(List<H> hstries){
		for (H hstry : hstries) {
			if(freeingStats.contains(hstry.getEntStatus())) return false;
		}
		return true;
	}
	public boolean onUpdate(String entIdentif){
		return true;
	}
	public boolean isUndeletable(H hstry){
		return undeletableStats.contains(hstry.getEntStatus());
	}
	public boolean isBroadcasting(H hstry){
		return broadcastingStats.contains(hstry.getEntStatus());
	}
	
	public Set<String> getUndeletableStats(){
		return Collections.unmodifiableSet(undeletableStats);
	}

	public Set<String> getFreeingStats(){
		return Collections.unmodifiableSet(freeingStats);
	}

	public Set<String> getBroadcastingStats(){
		return Collections.unmodifiableSet(broadcastingStats);
	}
}
