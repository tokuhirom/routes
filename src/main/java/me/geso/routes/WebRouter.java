package me.geso.routes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WebRouter<T> {
	public List<HttpRoute<T>> patterns;

	public WebRouter() {
		patterns = new ArrayList<HttpRoute<T>>();
	}

	/**
	 * Get the path information destination.
	 *
	 * @param method HTTP method
	 * @param path PATH_INFO
	 * @return
	 */
	public RoutingResult<T> match(final String method, final String path) {
		RoutingResult<T> retval = null;
		for (HttpRoute<T> route : patterns) {
			RoutingResult<T> result = route.match(method, path);
			if (result != null) {
				retval = result;
				if (retval.methodAllowed()) {
					return retval;
				}
			}
		}
		return retval;
	}

	/**
	 * Register new route. It only matches GET/HEAD method.
	 *
	 * @param path
	 * @param destination
	 * @return
	 */
	public WebRouter<T> get(final String path, final T destination) {
		return addRoute(path, destination, Arrays.asList("GET", "HEAD"));
	}

	/**
	 * Register new route. It only matches POST method.
	 *
	 * @param path
	 * @param destination
	 * @return
	 */
	public WebRouter<T> post(final String path, final T destination) {
		return addRoute(path, destination, Arrays.asList("POST"));
	}

	/**
	 * Register new route.
	 * 
	 * @param path PATH_INFO
	 * @param destination Destination object
	 * @param methods HTTP methods it should be match.
	 * @return
	 */
	public WebRouter<T> addRoute(final String path, final T destination, final List<String> methods) {
		patterns.add(new HttpRoute<T>(path, destination, methods));
		return this;
	}
}