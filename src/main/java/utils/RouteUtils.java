package utils;

import java.util.ArrayList;
import java.util.List;

public class RouteUtils {
	
	public static List<String> convertPathToList(String path) {
        String[] pathArray = path.split("/");
        List<String> pathList = new ArrayList<String>();
        for (String p : pathArray) {
            if (p.length() > 0) {
            	pathList.add(p);
            }
        }
        return pathList;
    }
    
    public static boolean isBind(String routePart) {
        return routePart.startsWith("{") && routePart.endsWith("}");
    }

    public static boolean isSplat(String pathPart) {
        return pathPart.equals("*");
    }

}
