package me.geso.routes;

import java.util.Map;
import java.util.TreeMap;

public class RoutingResult<T> {
	protected boolean methodAllowed;
	T destination;
	Map<String, String> captured = new TreeMap<>();

	/**
	 * This method returns true if it denied by HTTP method mismatch.
	 * False otherwise.
	 * 
	 * @return
	 */
	public boolean methodAllowed() {
		return methodAllowed;
	}

	/**
	 * Get destination object.
	 * 
	 * @return
	 */
	public T getDestination() {
		return destination;
	}

	void putCaptured(String name, String group) {
		captured.put(name, group);
	}

	/**
	 * Get captured variables in Map.
	 * 
	 * @return
	 */
	public Map<String, String> getCaptured() {
		return captured;
	}
}