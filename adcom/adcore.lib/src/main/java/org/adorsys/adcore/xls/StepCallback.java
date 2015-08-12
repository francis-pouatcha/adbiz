package org.adorsys.adcore.xls;

public interface StepCallback {
	public void markSynchPoint(String stepIdentif, int rowNum);
	public int readSyncPoint(String stepIdentif);
	public void done(String stepIdentif);
}
