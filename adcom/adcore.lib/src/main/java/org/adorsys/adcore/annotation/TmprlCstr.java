package org.adorsys.adcore.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.adorsys.adcore.enums.CoreTmprlDpndncy;

@Target({ ElementType.TYPE, METHOD, FIELD  })
@Retention(RUNTIME)
@Documented
public @interface TmprlCstr {
	/**
	 * The value of this description. This can be either a plain text description or a
	 * resource key..
	 * 
	 * @return
	 */
	public CoreTmprlDpndncy value();
}
