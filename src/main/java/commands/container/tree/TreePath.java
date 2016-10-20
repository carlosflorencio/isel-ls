package commands.container.tree;

import commands.ICommand;
import exceptions.CommandNotFoundException;
import matcher.matcher.RouteMatcher;
import utils.RouteUtils;

import java.util.List;
import java.util.StringTokenizer;

public class TreePath {
	
	private PNode root;
	private int count;
	
	public TreePath() {
		this.root = new PNode("root", null); //entry point, will never count and be found
		this.count = 0;
	}

	public boolean isEmpty() {
		return root.getChildren().size() == 0;
	}
	
	/*
	|--------------------------------------------------------------------------
	| Add (does not replace duplicate entrys)
	|--------------------------------------------------------------------------
	 */
	public void add(ICommand cmd) {
		if(cmd == null) return;
		
		PNode current = this.root;
		
		if(cmd.getRoute().equals("/")) { //if root, add command
			addRoot(current, cmd);
			return;
		}
		
		String join = cmd.getMethod() + cmd.getRoute();
		
		List<String> pathList = RouteUtils.convertPathToList(join);
		
		for (int i = 0; i < pathList.size(); i++) {
			if((i == pathList.size() -1)) { //add command to last path substring
				current = addChildren(current, pathList.get(i), cmd);
				this.count++;
			} else { //add middle subPaths
				current = addChildren(current, pathList.get(i), null);
			}
		}
	}
	
	private PNode addChildren(PNode parent, String subPath, ICommand cmd) {
		PNode child = parent.getChild(subPath, true);
		if (child == null) {
			child = new PNode(subPath, cmd);
			parent.getChildren().add(child);
		} else {
            if(child.getCmd() == null) {
                child.setCmd(cmd);
            }
        }
		return child;
	}
	
	private void addRoot(PNode parent, ICommand cmd) {
		PNode child = parent.getChild(cmd.getMethod(), true);
		if (child == null) {
			child = new PNode(cmd.getMethod(), cmd);
			parent.getChildren().add(child);
			this.count++;
		} else {
			if(child.getCmd() == null) {
				child.setCmd(cmd);
				this.count++;
			}
		}
	}
	
	/*
	|--------------------------------------------------------------------------
	| Get
	|--------------------------------------------------------------------------
	 */
	public ICommand get(String method, String path) throws CommandNotFoundException{
		PNode current = this.root;
		
		String join = method + path;
		
		StringTokenizer s = new StringTokenizer(join, "/");
		String str;
		while (s.hasMoreElements()) {
			str = (String) s.nextElement();
			PNode child = current.getChild(str, false);
			
			if (child == null) { //no more childs
				break;
			} else {
				if(RouteMatcher.matches(child.getCmd(), path, method)) {
					return child.getCmd();
				}
			}
			
			current = child;
		}
		
		throw new CommandNotFoundException();
	}
	
	/*
	|--------------------------------------------------------------------------
	| Length
	|--------------------------------------------------------------------------
	 */
	public int getSize() {
		return this.count;
	}

	/*
	|--------------------------------------------------------------------------
	| Print
	|--------------------------------------------------------------------------
	 */
	public void print() {
		print(this.root);
	}

	private void print(PNode n) {
		if (n == null)
			return;
		for (PNode c : n.getChildren()) {
			System.out.print(c);
			print(c);
		}
		System.out.println();
	}
	
}
