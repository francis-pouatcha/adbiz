package org.adorsys.adstock.rest.extension.invtry;

import java.util.List;

/**
 * 
 * @author boriswaguia
 *
 */
public class ArtLotSearchInput {
	/**
	 * The artPic fields. A list of artPics is used here, in case we want to do a batch search of products.
	 */
	private List<String> artPics;
	/**
	 * The start field.
	 */
	private long start;
	/**
	 * The max field.
	 */
	private long max;
	/**
	 * The fields field.
	 */
	private List<String> fields;
	/**
	 * The applyLike field.
	 */
	private boolean applyLike;
	
	public ArtLotSearchInput() {}
	
	public List<String> getArtPics() {
		return artPics;
	}

	public void setArtPics(List<String> artPics) {
		this.artPics = artPics;
	}

	public long getStart() {
		return start;
	}
	public void setStart(long start) {
		this.start = start;
	}
	public long getMax() {
		return max;
	}
	public void setMax(long max) {
		this.max = max;
	}
	public List<String> getFields() {
		return fields;
	}
	public void setFields(List<String> fields) {
		this.fields = fields;
	}
	public boolean isApplyLike() {
		return applyLike;
	}
	public void setApplyLike(boolean applyLike) {
		this.applyLike = applyLike;
	}
	@Override
	public String toString() {
		return "ArtLotSearchInput [artPics=" + artPics + ", start=" + start
				+ ", max=" + max + ", fields=" + fields + ", applyLike="
				+ applyLike + "]";
	}
	
}