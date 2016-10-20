package commands.container.tree;

import commands.ICommand;
import exceptions.CommandNotFoundException;
import org.junit.Test;
import testutils.TestUtils;

import static org.junit.Assert.*;

public class TestPathTree {
	
	@Test
	public void testConstruct() {
		TreePath tree = new TreePath();

		assertTrue(tree.isEmpty());
	}
	
	@Test
	public void testAddOne() throws Exception {
		TreePath tree = new TreePath();
		tree.add(TestUtils.getCommand("POST", "/test", null));

		assertEquals(1, tree.getSize());
	}

	@Test
	public void testAddRootDuplicated() throws Exception {
		TreePath tree = new TreePath();

		tree.add(TestUtils.getCommand("POST", "/", null));
		tree.add(TestUtils.getCommand("post", "/", null));

		assertEquals(1, tree.getSize());
	}

	@Test
	public void testAddTwoMethods() throws Exception {
		TreePath tree = new TreePath();

		ICommand cmd1 = TestUtils.getCommand("POST", "/test", null);
		ICommand cmd2 = TestUtils.getCommand("GET", "/", null);

		tree.add(cmd1);
		tree.add(cmd2);

		assertEquals(2, tree.getSize());
		assertEquals("/test", tree.get("post", "/test/").getRoute());
		assertEquals("/", tree.get("get", "/").getRoute());
	}

    @Test
    public void testAddWithBinds() throws Exception {
        TreePath tree = new TreePath();

        ICommand cmd2 = TestUtils.getCommand("GET", "/test/{id}/{year}", null);
        ICommand cmd1 = TestUtils.getCommand("GET", "/test/{id}/rented", null);

        tree.add(cmd1);
        tree.add(cmd2);

        assertEquals(2, tree.getSize());
        assertEquals("/test/{id}/rented", tree.get("get", "/test/2/rented").getRoute());
        assertEquals("/test/{id}/{year}", tree.get("get", "/test/3/1231").getRoute());
    }

	@Test
	public void testGetWithWildCard() throws Exception {
		TreePath tree = new TreePath();

		ICommand cmd1 = TestUtils.getCommand("POST", "/test", null);
		ICommand cmd2 = TestUtils.getCommand("GET", "/hey", null);
		ICommand cmd3 = TestUtils.getCommand("get", "/user/{username}", null);

		tree.add(cmd1);
		tree.add(cmd2);
		tree.add(cmd3);

		assertEquals(3, tree.getSize());
		assertEquals("/user/{username}", tree.get("get", "/user/iamfreee").getRoute());
	}

    @Test
    public void testCommandNotFound() {
        TreePath tree = new TreePath();

        assertEquals(0, tree.getSize());

        try {
            tree.get("get", "/user/iamfreee");
            fail("should throw exception");
        } catch (CommandNotFoundException e) {

        }
    }

	@Test
	public void testComplexWildCard() throws Exception {
		TreePath tree = new TreePath();

		ICommand cmd1 = TestUtils.getCommand("get", "/{test}", null);
		ICommand cmd2 = TestUtils.getCommand("get", "/user/{username}", null);
		ICommand cmd3 = TestUtils.getCommand("get", "/user/{username}/details", null);
		ICommand cmd4 = TestUtils.getCommand("post", "/", null);
		ICommand cmd5 = TestUtils.getCommand("get", "/", null);

		tree.add(cmd1);
		tree.add(cmd2);
		tree.add(cmd3);
		tree.add(cmd4);
		tree.add(cmd5);

		assertEquals(5, tree.getSize());
		assertEquals("/user/{username}", tree.get("get", "/user/iamfreee").getRoute());
		assertEquals("/user/{username}/details", tree.get("get", "/user/iamfreee/details").getRoute());
		assertEquals("/{test}", tree.get("get", "/olaaaa").getRoute());
		assertEquals("/", tree.get("post", "/").getRoute());
		assertEquals("/", tree.get("get", "/").getRoute());
	}

	@Test
	public void testWithSplat() throws Exception {
		TreePath tree = new TreePath();

		ICommand cmd1 = TestUtils.getCommand("get", "/hey", null);
		ICommand cmd2 = TestUtils.getCommand("get", "/user/*", null);
		
		tree.add(cmd1);
		tree.add(cmd2);
		
		assertEquals(2, tree.getSize());
		assertEquals("/user/*", tree.get("get", "/user/whatever/wow").getRoute());
		assertEquals("/hey", tree.get("get", "/hey").getRoute());
	}

}
