package org.adorsys.adstock.rest;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adcore.props.AbstEntiyProps;
import org.adorsys.adcore.props.CoreAbstBsnsItemProps;
import org.adorsys.adstock.jpa.StkArticleLot;

@Singleton
public class StkArticleLotProps extends AbstEntiyProps<StkArticleLot> {

	@Inject
	private CoreAbstBsnsItemProps superProps;

	@Override
	protected AbstEntiyProps<? super StkArticleLot> getSuperProps() {
		return superProps;
	}

	@Override
	public Class<StkArticleLot> getEntityClass() {
		return StkArticleLot.class;
	}
}
