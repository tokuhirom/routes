package me.geso.routes;

import static org.junit.Assert.*;

import org.junit.Test;

public class WebRouterTest {

	@Test
	public void test() {
		WebRouter<String> router = new WebRouter<>();
		router.get("/", "root");
		RoutingResult<String> match = router.match("GET", "/");
		assertNotNull(match);
		assertTrue(match.methodAllowed());
		assertEquals("root", match.getDestination());
	}

	@Test
	public void testMethodNotAllowed() {
		WebRouter<String> router = new WebRouter<>();
		router.get("/", "root");
		{
			RoutingResult<String> match = router.match("HEAD", "/");
			assertNotNull(match);
			assertTrue(match.methodAllowed());
		}
		{
			RoutingResult<String> match = router.match("GET", "/");
			assertNotNull(match);
			assertTrue(match.methodAllowed());
		}
		// POST is not allowed
		{
			RoutingResult<String> match = router.match("POST", "/");
			assertNotNull(match);
			assertFalse(match.methodAllowed());
		}
	}

	@Test
	public void testOrder() {
		WebRouter<String> router = new WebRouter<>();
		router.get("/", "root");
		router.get("/", "root2");

		RoutingResult<String> match = router.match("HEAD", "/");
		assertNotNull(match);
		assertEquals("root", match.getDestination());
	}

	@Test
	public void testSecondPost() {
		WebRouter<String> router = new WebRouter<>();
		router.get("/", "getRoot");
		router.post("/", "postRoot");

		{
			RoutingResult<String> match = router.match("POST", "/");
			assertNotNull(match);
			assertEquals("postRoot", match.getDestination());
		}

		{
			RoutingResult<String> match = router.match("GET", "/");
			assertNotNull(match);
			assertEquals("getRoot", match.getDestination());
		}
	}
}
