package me.geso.routes;


import java.util.List;

/**
 * HTTP route.
 * 
 * @author tokuhirom
 *
 * @param <T> type of the destination.
 */
class HttpRoute<T> {
	@Override
	public String toString() {
		return "HttpRoute [pathRoute=" + pathRoute + ", methods=" + methods
				+ "]";
	}

	protected PathRoute<T> pathRoute;
	protected List<String> methods;

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

	/**
	 * 
	 * @param method
	 * @param path
	 * @return instance of RoutingResult<T>, null if not matched.
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