/**
 * 
 */
package org.adorsys.adinvtry.rest.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author boriswaguia
 *
 */
public class ListToMapConverter<T, K, V> {
	public Map<K, V> conver(List<T> items, AbstractMapper<T, K, V> mapper) {
		Map<K,V> hashMap = new HashMap<K, V>();
		for (T t : items) {
			mapper.setEntity(t);
			K key = mapper.getKey();
			V value = mapper.getValue();
			hashMap.put(key, value);
		}
		return hashMap;
	}
}
