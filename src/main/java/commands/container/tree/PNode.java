package commands.container.tree;

import commands.ICommand;

import java.util.LinkedList;
import java.util.List;

public class PNode {
	private String segment;
	private ICommand cmd;
	private List<PNode> children;

	public PNode(String route, ICommand cmd) {
		this.segment = route;
		this.cmd = cmd;
		children = new LinkedList<PNode>();
	}

	public PNode getChild(String path, boolean add) {
		for (PNode n : children) { //first find if there is equals
            if (n.segment.equalsIgnoreCase(path))
                return n;
        }

        for (PNode n : children) { //if not, lets find a bind if we are finding a command
            if (!add && (n.segment.equalsIgnoreCase("*") || n.segment.startsWith("{")))
                return n;
        }

		return null;
	}
	
	/*
	|--------------------------------------------------------------------------
	| Getters
	|--------------------------------------------------------------------------
	 */
	public String getSegment() {
		return segment;
	}

	public ICommand getCmd() {
		return cmd;
	}

	public List<PNode> getChildren() {
		return children;
	}
	
	/*
	|--------------------------------------------------------------------------
	| Setters
	|--------------------------------------------------------------------------
	 */
	public void setCmd(ICommand cmd) {
		this.cmd = cmd;
	}
	
	public String toString() {
		return "/" + this.segment + (this.cmd == null ? "" : "(CMD)");
	}
	
}
