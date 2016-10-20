package matcher.matcher;

import commands.ICommand;
import org.junit.Test;
import testutils.TestUtils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestRouteMatcher {
	

	@Test
	public void testMatchRoot() {
		ICommand cmd = TestUtils.getCommand("post", "/", null);
		
		assertTrue( RouteMatcher.matches(cmd, "/", "POST") );
	}
	
	@Test
	public void testMatchWithDifferentMethod() {
		ICommand cmd = TestUtils.getCommand("get", "/", null);
		
		assertFalse( RouteMatcher.matches(cmd, "/", "POST") );
	}
	
	@Test
	public void testSimpleRoute() {
		ICommand cmd = TestUtils.getCommand("get", "/users/details", null);
		
		assertTrue( RouteMatcher.matches(cmd, "/users/details", "GET") );
	}
	
	@Test
	public void testMatchRouteWithIgnoreCase() {
		ICommand cmd = TestUtils.getCommand("post", "/hello", null);
		
		assertTrue( RouteMatcher.matches(cmd, "/Hello", "post") );
	}
	
	@Test
	public void testMatchWithWildCards() {
		ICommand cmd = TestUtils.getCommand("get", "/user/{username}", null);
		
		assertTrue( RouteMatcher.matches(cmd, "/user/iamfreee", "get") );
	}
	
	@Test
	public void testMatchWithComplexWildCard() {
		ICommand cmd = TestUtils.getCommand("get", "/user/{username}/{local}/city", null);
		
		assertTrue( RouteMatcher.matches(cmd, "/user/iamfreee/lisbon/city", "get") );
	}
	
	@Test
	public void testMatchWithSplat() {
		ICommand cmd = TestUtils.getCommand("get", "/user/*", null);
		
		assertTrue( RouteMatcher.matches(cmd, "/user/iamfreee/hello", "get") );
		assertFalse( RouteMatcher.matches(cmd, "/user", "get") );
	}
	
	@Test
	public void testMatchWithComplexSplat() {
		ICommand cmd = TestUtils.getCommand("get", "/*", null);
		
		assertTrue( RouteMatcher.matches(cmd, "/whatever/whatever", "get") );
		assertFalse( RouteMatcher.matches(cmd, "/", "get") );
	}

}
