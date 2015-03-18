package me.geso.routes;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Utility class named "K".
 */
public class K {
	/**
	 * Create new LinkedHashMap from list of Objects.
	 * 
	 * @param objects
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <Key, Val> Map<Key, Val> map(Object... objects) {
		if (objects.length % 2 != 0) {
			throw new IllegalArgumentException(
					"arguments should be even number");
		}
		LinkedHashMap<Key, Val> map = new LinkedHashMap<>(
				objects.length / 2);
		for (int i = 0; i < objects.length; i += 2) {
			map.put((Key) objects[i], (Val) objects[i + 1]);
		}
		return Collections.unmodifiableMap(map);
	}

}
