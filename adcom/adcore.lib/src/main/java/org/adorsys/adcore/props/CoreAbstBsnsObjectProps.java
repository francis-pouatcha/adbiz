package org.adorsys.adcore.props;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adcore.jpa.CoreAbstBsnsObject;

@Singleton
public class CoreAbstBsnsObjectProps  extends AbstEntiyProps<CoreAbstBsnsObject> {

	@Inject
	private CoreAbstBsnsObjectHeaderProps superProps;
	
	@Override
	protected CoreAbstBsnsObjectHeaderProps getSuperProps() {
		return superProps;
	}

	@Override
	public Class<CoreAbstBsnsObject> getEntityClass() {
		return CoreAbstBsnsObject.class;
	}

}
