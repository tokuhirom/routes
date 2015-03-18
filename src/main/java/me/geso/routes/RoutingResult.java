package me.geso.routes;

import java.util.LinkedHashMap;
import java.util.Map;

public class RoutingResult<T> {
	protected final boolean methodAllowed;
	private final T destination;
	private final Map<String, String> captured;

	public RoutingResult(boolean methodAllowed, T destination,
			LinkedHashMap<String, String> captured) {
		this.methodAllowed = methodAllowed;
		this.destination = destination;
		this.captured = captured;
	}

	/**
	 * This method returns true if it denied by HTTP method mismatch. False
	 * otherwise.
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

	private void putCaptured(String name, String group) {
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
