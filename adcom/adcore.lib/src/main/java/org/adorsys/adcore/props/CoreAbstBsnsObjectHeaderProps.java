package org.adorsys.adcore.props;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adcore.jpa.CoreAbstBsnsObjectHeader;

@Singleton
public class CoreAbstBsnsObjectHeaderProps extends AbstEntiyProps<CoreAbstBsnsObjectHeader> {

	@Inject
	private CoreAbstIdentifObjectProps superProps;

	@Override
	protected CoreAbstIdentifObjectProps getSuperProps() {
		return superProps;
	}

	@Override
	public Class<CoreAbstBsnsObjectHeader> getEntityClass() {
		return CoreAbstBsnsObjectHeader.class;
	}

}
