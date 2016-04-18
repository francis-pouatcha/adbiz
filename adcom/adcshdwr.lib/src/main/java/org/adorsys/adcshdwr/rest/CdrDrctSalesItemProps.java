package org.adorsys.adcshdwr.rest;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adcore.jpa.CoreAbstBsnsItem;
import org.adorsys.adcore.props.AbstEntiyProps;
import org.adorsys.adcore.props.CoreAbstBsnsItemProps;

@Singleton
public class CdrDrctSalesItemProps extends AbstEntiyProps {

	@Inject
	private CoreAbstBsnsItemProps superProps;

	@Override
	protected AbstEntiyProps getSuperProps() {
		return superProps;
	}

	@Override
	public Class<CoreAbstBsnsItem> getEntityClass() {
		return CoreAbstBsnsItem.class;
	}
}
