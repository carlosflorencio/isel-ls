package commands.container;

import commands.ICommand;
import commands.container.tree.TreePath;
import exceptions.CommandNotFoundException;

public class TreeContainer implements IContainer {

	private TreePath tree;
	
	public TreeContainer() {
		this.tree = new TreePath();
	}
 	
	@Override
	public void add(ICommand cmd) {
		tree.add(cmd);
	}

	@Override
	public ICommand find(String method, String path) throws CommandNotFoundException{
		return tree.get(method, path);
	}

	@Override
	public int size() {
		return tree.getSize();
	}

}
