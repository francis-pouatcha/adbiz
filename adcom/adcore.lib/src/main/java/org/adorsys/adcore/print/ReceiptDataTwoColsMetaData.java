package org.adorsys.adcore.print;

public class ReceiptDataTwoColsMetaData {
	
	private final float rowHeight;
	private final float width;
	private final float cellMargin;
	
	private final float designation;
	private final float value;

	public ReceiptDataTwoColsMetaData(float rowHeight, float width, float margin, float cellMargin,
			float valueWidthPercent) 
	{
		super();
		this.rowHeight = rowHeight;
		this.width = width;
		this.cellMargin = cellMargin;
		float marginSpace = 2*margin + 2*cellMargin;
		float availableWidth = width - marginSpace;
		this.value = valueWidthPercent*availableWidth;
		this.designation = (1f-valueWidthPercent)*availableWidth;
	}

	public float getRowHeight() {
		return rowHeight;
	}

	public float getWidth() {
		return width;
	}

	public float getDesignation() {
		return designation;
	}

	public float getCellMargin() {
		return cellMargin;
	}

	public float getValue() {
		return value;
	}

}
