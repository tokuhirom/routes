package me.geso.routes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WebRouter<T> {
	public List<HttpRoute<T>> patterns;

	public WebRouter() {
		patterns = new ArrayList<HttpRoute<T>>();
	}

	public RoutingResult<T> match(String method, String path) {
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

	public WebRouter<T> get(String path, T target) {
		return addRoute(path, target, Arrays.asList("GET", "HEAD"));
	}

	public WebRouter<T> post(String path, T target) {
		return addRoute(path, target, Arrays.asList("POST"));
	}

	public WebRouter<T> addRoute(String path, T target, List<String> methods) {
		patterns.add(new HttpRoute<T>(path, target, methods));
		return this;
	}
}
