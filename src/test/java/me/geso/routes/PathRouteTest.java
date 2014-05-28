package me.geso.routes;

import static org.junit.Assert.*;

import org.junit.Test;

public class PathRouteTest {

	@Test
	public void test() {
		PathRoute<String> route = new PathRoute<String>("/", "Root");
		RoutingResult<String> match = route.match("/");
		assertNotNull(match);
		assertTrue(match.methodAllowed());
		assertEquals("Root", match.getDestination());
	}
	
	@Test
	public void testCompileToRegexp() {
		PathRoute<String> route = new PathRoute<String>("/", "Root");
		assertEquals("/HO", route.compileToRegexp("/HO"));
		assertEquals("/H\\.O", route.compileToRegexp("/H.O"));
		assertEquals("/(?<id>[a-zA-Z0-9]+)", route.compileToRegexp("/{id}"));
	}

	@Test
	public void testCapture() {
		PathRoute<String> route = new PathRoute<String>("/{id}", "Detail");
		RoutingResult<String> match = route.match("/5963");
		assertNotNull(match);
		assertTrue(match.methodAllowed());
		assertEquals("Detail", match.getDestination());
		assertEquals("5963", match.getCaptured().get("id"));
	}

	@Test
	public void testCaptureFoo() {
		PathRoute<String> route = new PathRoute<String>("/foo/*", "Detail");
		RoutingResult<String> match = route.match("/foo/bar/baz");
		assertNotNull(match);
		assertTrue(match.methodAllowed());
		assertEquals("Detail", match.getDestination());
		assertEquals("bar/baz", match.getCaptured().get("*"));
	}

}
