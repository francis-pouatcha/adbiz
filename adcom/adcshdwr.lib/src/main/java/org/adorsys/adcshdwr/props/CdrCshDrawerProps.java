package org.adorsys.adcshdwr.props;

import javax.ejb.Singleton;
import javax.inject.Inject;

import org.adorsys.adcore.props.AbstEntiyProps;
import org.adorsys.adcore.props.CoreAbstIdentifObjectProps;
import org.adorsys.adcshdwr.jpa.CdrCshDrawer;

@Singleton
public class CdrCshDrawerProps extends AbstEntiyProps {

	@Inject
	private CoreAbstIdentifObjectProps superProps;
	
	@Override
	protected AbstEntiyProps getSuperProps() {
		return superProps;
	}

	@Override
	public Class<CdrCshDrawer> getEntityClass() {
		return CdrCshDrawer.class;
	}

}
