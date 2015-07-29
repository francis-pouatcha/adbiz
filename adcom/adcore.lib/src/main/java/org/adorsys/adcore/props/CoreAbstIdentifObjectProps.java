package org.adorsys.adcore.props;

import javax.inject.Singleton;

import org.adorsys.adcore.jpa.CoreAbstIdentifObject;

@Singleton
public class CoreAbstIdentifObjectProps extends AbstEntiyProps<CoreAbstIdentifObject> {

	@Override
	protected AbstEntiyProps<CoreAbstIdentifObject> getSuperProps() {
		return null;
	}

	@Override
	public Class<CoreAbstIdentifObject> getEntityClass() {
		return CoreAbstIdentifObject.class;
	}
}