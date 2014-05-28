# Routes for Java

This is a tiny routing library for Java.

## Dependencies

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

 * Java 1.7+
