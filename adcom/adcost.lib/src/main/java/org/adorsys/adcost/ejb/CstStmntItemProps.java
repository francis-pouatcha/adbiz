package org.adorsys.adcost.ejb;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adcore.props.AbstEntiyProps;
import org.adorsys.adcore.props.CoreAbstBsnsItemProps;
import org.adorsys.adcost.jpa.CstStmntItem;

@Singleton
public class CstStmntItemProps extends AbstEntiyProps<CstStmntItem> {

	@Inject
	private CoreAbstBsnsItemProps superProps;

	@Override
	protected AbstEntiyProps<? super CstStmntItem> getSuperProps() {
		return superProps;
	}

	@Override
	public Class<CstStmntItem> getEntityClass() {
		return CstStmntItem.class;
	}
}
