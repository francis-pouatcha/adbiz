package org.adorsys.adcore.props;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adcore.jpa.CoreAbstBsnsItemHeader;

@Singleton
public class CoreAbstBsnsItemHeaderProps extends AbstEntiyProps {

	@Inject
	private CoreAbstIdentifObjectProps superProps;

	@Override
	protected CoreAbstIdentifObjectProps getSuperProps() {
		return superProps;
	}

	@Override
	public Class<CoreAbstBsnsItemHeader> getEntityClass() {
		return CoreAbstBsnsItemHeader.class;
	}

}
