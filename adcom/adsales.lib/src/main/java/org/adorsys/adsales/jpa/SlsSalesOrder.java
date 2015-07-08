package org.adorsys.adsales.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Transient;

import org.adorsys.javaext.description.Description;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity
@Description("SlsSalesOrder_description")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SlsSalesOrder extends SlsAbstractSalesOrder {
	
	private static final long serialVersionUID = -5379103455354065689L;
	
	@Transient
	private List<SlsSOItem> slsSOItems= new ArrayList<SlsSOItem>();
	
	@Transient
	private List<SlsSOPtnr> slsSOPtnrs= new ArrayList<SlsSOPtnr>();

	public List<SlsSOItem> getSlsSOItems() {
		return slsSOItems;
	}

	public void setSlsSOItems(List<SlsSOItem> slsSOItems) {
		this.slsSOItems = slsSOItems;
	}

	public List<SlsSOPtnr> getSlsSOPtnrs() {
		return slsSOPtnrs;
	}

	public void setSlsSOPtnrs(List<SlsSOPtnr> slsSOPtnrs) {
		this.slsSOPtnrs = slsSOPtnrs;
	}
	
	
}