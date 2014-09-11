package me.geso.routes;


import java.util.List;

/**
 * HTTP route.
 *
 * @param <T> type of the destination.
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

	public PathRoute<T> getPathRoute() {
		return pathRoute;
	}

	/**
	 * @param method
	 * @param path
	 * @return instance of {@code RoutingResult<T>}, null if not matched.
	 */
	public RoutingResult<T> match(String method, String path) {
		RoutingResult<T> result = pathRoute.match(path);
		if (result != null) {
			result.methodAllowed = methods.contains(method);
			return result;
		} else {
			return null;
		}
	}
}