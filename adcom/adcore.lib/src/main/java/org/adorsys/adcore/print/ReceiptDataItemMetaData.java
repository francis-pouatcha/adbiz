package org.adorsys.adcore.print;

public class ReceiptDataItemMetaData {
	
	private final float rowHeight;
	private final float width;
	private final float cellMargin;
	
	private final float code;
	private final float designation;
	private final float quantity;
	private final float total;
	
	private final boolean mergeCodeAndDesignation;

	public ReceiptDataItemMetaData(float rowHeight, float width, float margin, float cellMargin,
			float codeWidthPercent, float quantityWidthPercent,float totalWidthPercent) 
	{
		super();
		this.rowHeight = rowHeight;
		this.width = width;
		this.cellMargin = cellMargin;
		float cellMarginSpace = (codeWidthPercent==0f?0f:cellMargin) + 2*cellMargin;
		float marginSpace = cellMarginSpace + 2*margin;
		float availableWidth = width - marginSpace;
		this.code = codeWidthPercent*availableWidth;
		this.quantity = quantityWidthPercent*availableWidth;
		this.total = totalWidthPercent*availableWidth;
		// The rest of the space
		this.designation = (1f - (codeWidthPercent+quantityWidthPercent+totalWidthPercent))*availableWidth;
		if(codeWidthPercent==0f){
			mergeCodeAndDesignation=true;
		} else {
			mergeCodeAndDesignation=false;
		}
	}

	public float getRowHeight() {
		return rowHeight;
	}

	public float getWidth() {
		return width;
	}

	public float getCode() {
		return code;
	}

	public float getDesignation() {
		return designation;
	}

	public float getQuantity() {
		return quantity;
	}

	public float getTotal() {
		return total;
	}

	public boolean isMergeCodeAndDesignation() {
		return mergeCodeAndDesignation;
	}

	public float getCellMargin() {
		return cellMargin;
	}

}
