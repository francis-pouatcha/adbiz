package org.adorsys.adkcloak.loader;

import java.io.File;
import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.adorsys.adcore.loader.ejb.CorLdrStepCallback;
import org.adorsys.adcore.xls.CoreAbstLoader;
import org.adorsys.adcore.xls.StepCallback;
import org.apache.commons.io.FileUtils;

@Stateless
public class DeployConfigLoader extends CoreAbstModelLoader<DeployConfig> {

	@EJB
	private CorLdrStepCallback stepCallback;
	@EJB 
	private DeployConfigLoader loader;	

	@Override
	protected DeployConfig newObject() {
		return new DeployConfig();
	}
	@Override
	protected StepCallback getStepCallback() {
		return stepCallback;
	}

	@Override
	protected CoreAbstLoader<DeployConfig> getLoader() {
		return loader;
	}

	@Override
	protected Object getIdentif(DeployConfig t) {
		return t;
	}
	@Override
	protected DeployConfig lookup(Object identif) {
		return null;
	}
	
	@Override
	protected DeployConfig update(DeployConfig t) {
		return t;
	}
	
	@Override
	protected DeployConfig create(DeployConfig t) {
		File file = new File(t.getConfigStr());
		try {
			FileUtils.touch(file);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
		return t;
	}
	
}
