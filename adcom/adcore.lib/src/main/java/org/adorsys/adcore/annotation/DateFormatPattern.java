package org.adorsys.adcore.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.FIELD  })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DateFormatPattern {

	public String pattern();
	
	public String locale() default "";
	
	public String suffix() default "";
	
	public String prefix() default "";
}