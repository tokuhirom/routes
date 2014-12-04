package me.geso.routes;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.LinkedHashMap;

import org.junit.Test;

public class PathRouteTest {

	@Test
	public void test() {
		PathRoute<String> route = new PathRoute<String>("/", "Root");
		LinkedHashMap<String, String> captured = new LinkedHashMap<>();
		boolean matched = route.match("/", captured);
		assertThat(matched, is(true));
	}

	@Test
	public void testCompileToRegexp() {
		PathRoute<String> route = new PathRoute<String>("/", "Root");
		assertEquals("/HO", route.compileToRegexp("/HO"));
		assertEquals("/H\\.O", route.compileToRegexp("/H.O"));
		assertEquals("/(?<id>[a-zA-Z0-9._-]+)", route.compileToRegexp("/{id}"));
	}

	@Test
	public void testCapture() {
		PathRoute<String> route = new PathRoute<String>("/{id}", "Detail");
		LinkedHashMap<String, String> captured = new LinkedHashMap<>();
		boolean matched = route.match("/5963", captured);
		assertTrue(matched);
		assertThat(captured, is(K.<String, String> map("id", "5963")));
	}

	@Test
	public void testCaptureLatLng() {
		PathRoute<String> route = new PathRoute<String>("/{lat}/{lng}",
				"Detail");
		LinkedHashMap<String, String> captured = new LinkedHashMap<>();
		boolean matched = route.match("/137.555/64.222", captured);
		assertTrue(matched);
		assertThat(captured,
				is(K.<String, String> map("lat", "137.555", "lng", "64.222")));
	}

	@Test
	public void testCaptureFoo() {
		PathRoute<String> route = new PathRoute<String>("/foo/*", "Detail");
		LinkedHashMap<String, String> captured = new LinkedHashMap<>();
		boolean matched = route.match("/foo/bar/baz", captured);
		assertTrue(matched);
		assertThat(captured, is(K.<String, String> map("*", "bar/baz")));
	}

}
