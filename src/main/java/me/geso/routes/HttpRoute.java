package me.geso.routes;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * HTTP route.
 *
 * @param <T>
 *            type of the destination.
 * @author tokuhirom
 */

public class HttpRoute<T> {
	@Override
	public String toString() {
		return "HttpRoute [pathRoute=" + pathRoute + ", methods=" + methods
				+ "]";
	}

	private final PathRoute<T> pathRoute;

	private final List<String> methods;

	/**
	 * Create new instance.
	 *
	 * @param path
	 * @param destination
	 * @param methods
	 */
	public HttpRoute(String path, T destination, List<String> methods) {
		this.pathRoute = new PathRoute<>(path, destination);
		this.methods = methods;
	}

	public List<String> getMethods() {
		return methods;
	}

	public T getDestination() {
		return pathRoute.getDestination();
	}

	public String getPath() {
		return pathRoute.getPath();
	}

	/**
	 * @param method
	 * @param path
	 * @return instance of {@code RoutingResult<T>}, null if not matched.
	 */
	public RoutingResult<T> match(String method, String path) {
		// It should be insertion ordered for testing.
		LinkedHashMap<String, String> captured = new LinkedHashMap<>();
		if (pathRoute.match(path, captured)) {
			boolean methodAllowed = methods.contains(method);
			return new RoutingResult<>(methodAllowed,
					pathRoute.getDestination(), captured);
		} else {
			return null;
		}
	}
}
