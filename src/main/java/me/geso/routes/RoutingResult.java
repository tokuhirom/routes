package me.geso.routes;

import java.util.HashMap;
import java.util.Map;

public class RoutingResult<T> {
	protected boolean methodAllowed;
	T destination;
	Map<String, String> captured = new HashMap<>();

	public boolean methodAllowed() {
		return methodAllowed;
	}

	public T getDestination() {
		return destination;
	}

	public void putCaptured(String name, String group) {
		captured.put(name, group);
	}

	public Map<String,String> getCaptured() {
		return captured;
	}
}