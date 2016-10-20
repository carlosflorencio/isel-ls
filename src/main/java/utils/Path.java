package utils;

import java.util.ArrayList;
import java.util.List;

public class Path {

	private String path;
	
	public Path(String path) {
		this.path = path;
		this.parse();
	}
	
	private void parse() {
		if(path.isEmpty()) {
			this.path = "/";
			return;
		}
		
		if(this.path.startsWith("/") == false) { //add / 
			this.path = "/" + this.path;
		}
		
		if(this.path.endsWith("/")) { //remove last /
			this.path = this.path.substring(0,this.path.length()-1);
		}
	}
	
	public String get() {
		return this.path;
	}
	
	public List<String> toList() {
        String[] pathArray = this.path.split("/");
        List<String> pathList = new ArrayList<String>();
        for (String p : pathArray) {
            if (p.length() > 0) {
            	pathList.add(p);
            }
        }
        return pathList;
    }
	
	public boolean equals(Object o) {
		if(o == null) {
			return false;
		}
		
		if(o instanceof Path) {
			return ((Path) o).get().equals(this.path);
		}
		
		return false;
	}
	
	public String toString() {
		return this.path;
	}
}
