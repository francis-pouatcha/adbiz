/**
 * 
 */
package org.adorsys.adinvtry.rest.util;

/**
 * @author boriswaguia
 * @param <S>
 * @param <K>
 *
 */
public abstract class AbstractMapper<T, K, V> {
	T entity;
	
	public abstract void setEntity(T t);
	public abstract K getKey();
	public abstract V getValue();
}
