package org.adorsys.adcore.rest;
import org.adorsys.adcore.jpa.CoreAbstEntityJob;
import org.adorsys.adcore.jpa.CoreAbstEntityStep;

public interface CoreEntityJobExecutor<J extends CoreAbstEntityJob, S extends CoreAbstEntityStep> {
	public void execute(String stepIdentif);
	public int estimateExecTimeMilisec(String stepIdentif);
}
