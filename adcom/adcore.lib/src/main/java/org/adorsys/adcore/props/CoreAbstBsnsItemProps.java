package org.adorsys.adcore.props;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adcore.jpa.CoreAbstBsnsItem;

@Singleton
public class CoreAbstBsnsItemProps  extends AbstEntiyProps {

	@Inject
	private CoreAbstBsnsItemHeaderProps superProps;
	
	@Override
	protected CoreAbstBsnsItemHeaderProps getSuperProps() {
		return superProps;
	}

	@Override
	public Class<CoreAbstBsnsItem> getEntityClass() {
		return CoreAbstBsnsItem.class;
	}

}
