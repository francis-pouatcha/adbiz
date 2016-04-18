package org.adorsys.adcore.props;

import javax.inject.Singleton;

import org.adorsys.adcore.jpa.CoreAbstIdentifObject;

@Singleton
public class CoreAbstIdentifObjectProps extends AbstEntiyProps {

	@Override
	protected AbstEntiyProps getSuperProps() {
		return null;
	}

	@Override
	public Class<?> getEntityClass() {
		return CoreAbstIdentifObject.class;
	}
}