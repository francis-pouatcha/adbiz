package org.adorsys.adinvtry.rest;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adcore.props.AbstEntiyProps;
import org.adorsys.adcore.props.CoreAbstBsnsItemProps;
import org.adorsys.adinvtry.jpa.InvInvtryItem;

@Singleton
public class InvInvtryItemProps extends AbstEntiyProps<InvInvtryItem> {

	@Inject
	private CoreAbstBsnsItemProps superProps;

	@Override
	protected AbstEntiyProps<? super InvInvtryItem> getSuperProps() {
		return superProps;
	}

	@Override
	public Class<InvInvtryItem> getEntityClass() {
		return InvInvtryItem.class;
	}
}
