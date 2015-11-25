package org.adorsys.adprocmt.rest;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adcore.props.AbstEntiyProps;
import org.adorsys.adcore.props.CoreAbstBsnsItemProps;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem;

@Singleton
public class PrcmtDlvryItemProps extends AbstEntiyProps<PrcmtDlvryItem> {

	@Inject
	private CoreAbstBsnsItemProps superProps;

	@Override
	protected AbstEntiyProps<? super PrcmtDlvryItem> getSuperProps() {
		return superProps;
	}

	@Override
	public Class<PrcmtDlvryItem> getEntityClass() {
		return PrcmtDlvryItem.class;
	}
}
