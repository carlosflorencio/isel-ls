package request;

import utils.RouteUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Request implements IRequest {

    protected Map<String, String> queryString;

    /**
     * Converts the query string to a parameters map
     * @param querystr query string
     * @return map with parameters key = value
     */
    protected Map<String, String> parseQueryString(String querystr) {
        Map<String, String> map = new HashMap<String, String>();

        if(querystr == null || querystr.isEmpty()) return map;

        String[] separateByAnd = querystr.split("&");

        for(int i=0; i<separateByAnd.length; ++i) {
            String[] separateByEqual = separateByAnd[i].split("=");

            if(separateByEqual.length == 2)
                map.put(separateByEqual[0], separateByEqual[1]);
        }

        return map;
    }

    /**
     * Parses the command route and path to find the binds
     * @param routePath command route
     * @param path app path
     * @return Map with binds, key = value
     */
    protected Map<String, String> parseBinds(String commandRoute, String path) {
        Map<String, String> map = new HashMap<String, String>();

        List<String> routeList = RouteUtils.convertPathToList(commandRoute);
        List<String> pathList = RouteUtils.convertPathToList(path);

        for (int i = 0; (i < pathList.size()) && (i < routeList.size()); i++) {
            String matchedPart = routeList.get(i);
            if (RouteUtils.isBind(matchedPart)) {
                String bind = matchedPart.substring(1, matchedPart.length() - 1);
                map.put(bind.toLowerCase(), pathList.get(i));
            }
        }
        return map;
    }
}
