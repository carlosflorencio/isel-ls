package matcher.matcher;

import commands.ICommand;
import utils.RouteUtils;

import java.util.List;

public class RouteMatcher {

	public static boolean matches(ICommand cmd, String path, String method) {
		if(cmd == null) return false;
		
        if (cmd.getMethod().equalsIgnoreCase(method)) { //compare method
        	
            return matchPaths(cmd.getRoute(), path);
        }
        return false;
	}
	
	private static boolean matchPaths(String route, String path) {
        if (route.equalsIgnoreCase(path)) {// Paths are the same
            return true;
        }

        return matchWildCards(route, path);
    }
	
	private static boolean matchWildCards(String route, String path) {
    	// check params
        List<String> thisPathList = RouteUtils.convertPathToList(route);
        List<String> pathList = RouteUtils.convertPathToList(path);

        
        int thisPathSize = thisPathList.size();
        int pathSize = pathList.size();
        
        if (thisPathSize == pathSize) {
            for (int i = 0; i < thisPathSize; i++) {
                String thisPathPart = thisPathList.get(i);
                String pathPart = pathList.get(i);
                
                if ((i == thisPathSize -1) && (thisPathPart.equals("*") && route.endsWith("*"))) {
                    // wildcard match
                    return true;
                }
                
                if ((!thisPathPart.startsWith("{"))
                        && !thisPathPart.equalsIgnoreCase(pathPart)
                        && !thisPathPart.equals("*")) {
                    return false;
                }
            }
            // All parts matched
            return true;
        } else {
        	if(route.equals("/*") && path.equals("/")) return false; //specific case
            // Number of "path parts" not the same
            // check wild card {}
            if (route.endsWith("*")) {
                if (pathSize == (thisPathSize - 1) && (path.endsWith("/"))) {
                    // Hack for making wildcards work with trailing slash
                    pathList.add("");
                    pathList.add("");
                    pathSize += 2;
                }

                if (thisPathSize < pathSize) {
                    for (int i = 0; i < thisPathSize; i++) {
                        String thisPathPart = thisPathList.get(i);
                        String pathPart = pathList.get(i);
                        if (thisPathPart.equals("*") && (i == thisPathSize -1) && route.endsWith("*")) {
                            // wildcard match
                            return true;
                        }
                        if (!thisPathPart.startsWith("{")
                                && !thisPathPart.equalsIgnoreCase(pathPart)
                                && !thisPathPart.equals("*")) {
                            return false;
                        }
                    }
                    // All parts matched
                    return true;
                }
                // End check wild card
            }
            return false;
        }
    }
	


        
    }
	

