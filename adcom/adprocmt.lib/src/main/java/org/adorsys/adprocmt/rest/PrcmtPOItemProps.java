package org.adorsys.adprocmt.rest;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adcore.props.AbstEntiyProps;
import org.adorsys.adcore.props.CoreAbstBsnsItemProps;
import org.adorsys.adprocmt.jpa.PrcmtPOItem;

@Singleton
public class PrcmtPOItemProps extends AbstEntiyProps {

	@Inject
	private CoreAbstBsnsItemProps superProps;

	@Override
	protected AbstEntiyProps getSuperProps() {
		return superProps;
	}

	@Override
	public Class<PrcmtPOItem> getEntityClass() {
		return PrcmtPOItem.class;
	}
}
