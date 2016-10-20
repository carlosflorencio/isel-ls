package commands.container;

import commands.ICommand;
import exceptions.CommandNotFoundException;

public interface IContainer {

	public void add(ICommand cmd);
	
	public ICommand find(String method, String path) throws CommandNotFoundException;
	
	public int size();
	
}
