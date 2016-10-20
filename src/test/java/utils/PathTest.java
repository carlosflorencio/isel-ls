package utils;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PathTest {

	@Test
	public void testParseWithTrailingSlash() {
		Path p = new Path("/users/");
		
		assertEquals("/users", p.get());
	}
	
	@Test
	public void testParseWithoutBeginingSlash() throws Exception {
		Path p = new Path("users/details");
		
		assertEquals("/users/details", p.get());
	}
	
	@Test
	public void testParseWithEmptyPath() throws Exception {
		Path p = new Path("");
		
		assertEquals("/", p.get());
	}
	
	@Test
	public void testEquals() throws Exception {
		Path p = new Path("/users");
		Path p2 = new Path("users/");
		
		assertTrue(p.equals(p2));
	}
	
	@Test
	public void testPathToList() throws Exception {
		Path p = new Path("/users/test");
		List<String> list = p.toList();
		
		assertEquals(2, list.size());
		assertEquals("users", list.get(0));
		assertEquals("test", list.get(1));
		
	}

}
