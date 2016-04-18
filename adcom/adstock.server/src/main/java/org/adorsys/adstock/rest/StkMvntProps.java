package org.adorsys.adstock.rest;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adcore.props.AbstEntiyProps;
import org.adorsys.adcore.props.CoreAbstBsnsItemProps;
import org.adorsys.adstock.jpa.StkMvnt;

@Singleton
public class StkMvntProps extends AbstEntiyProps {

	@Inject
	private CoreAbstBsnsItemProps superProps;
	
	@Override
	protected AbstEntiyProps getSuperProps() {
		return superProps;
	}

	@Override
	public Class<StkMvnt> getEntityClass() {
		return StkMvnt.class;
	}
}
