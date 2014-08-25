# Routes for Java

[![Build Status](https://travis-ci.org/tokuhirom/routes.svg?branch=master)](https://travis-ci.org/tokuhirom/routes)

This is a tiny routing library for Java.

## Synopsis


### Your WebAction.java

	@FunctionalInterface
	public interface WebAction {
		void call(ServletRequest req, ServletResponse res);
	}

### Your routing code.

		// Create routing rules.
		router = new WebRouter<WebAction>();
		router.get("/", RootController::index)
				.get("/sample-json", RootController::sampleJson);
				
		// Match
		RoutingResult rr = router.match("GET", "/sample-json");
		if (rr != null) {
			if (rr.methodAllowed()) {
				return res405();
			} else {
				WebAction dst = rr.getDestination();
				return dst.invoke();
			}
		} else {
			return res404();
		}


## Dependencies

 * Java 8
