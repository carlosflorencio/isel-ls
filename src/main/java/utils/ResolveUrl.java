package utils;

import app.RegisterCommands;
import app.console.commands.ListenConsoleCommand;

import java.util.Map;

public class ResolveUrl {

    private static Map<String, String> commandRoutes = RegisterCommands.getCommandsBinds();

    /**
     * @return Gets the app url
     */
	public static String ofHome(){
		return ListenConsoleCommand.getServerUrl();
	}

    /**
     * Gets a url from a command route without binds
     * @param route Command route
     * @return Final url from app url
     */
	public static String ofRoute(String route){
		String routeCommand = commandRoutes.get(route);
		return ListenConsoleCommand.getServerUrl()+routeCommand;
	}

    /**
     * Gets the route of a command route with his binds replaced
     * @param route Command route
     * @param parameters Bind parameters
     * @return Final route from app route
     */
    public static String of(String route, Object... parameters){
    	String routeCommand = commandRoutes.get(route);
        String[] binds = objectArrayToString(parameters);
        return ListenConsoleCommand.getServerUrl() + String.format(changeTo(routeCommand, "%s"), binds);
    }

    /**
     * Gets the app URI with that path for the asset
     * @param path Asset Path
     * @return URI of home + resources + Path
     */
    public static String asset(String path) {
        return ofHome() + "/resources/" + path;
    }

    /**
     * Converts a command route with binds to a printf similar string, ex: /test/{id} to /test/%s
     * @param routeCommand Command route to convert
     * @param to What to replace with route binds
     * @return Converted string
     */
	private static String changeTo(String routeCommand, String to) {
        if(routeCommand == null) return null;

        return routeCommand.replaceAll("\\{[a-zA-Z0-9-_]+\\}", to);
	}

    /**
     * Converts an object array to a string array
     * @param arr Object array to convert
     * @return String array from the object array
     */
    private static String[] objectArrayToString(Object[] arr) {
        String[] res = new String[arr.length];

        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i].toString();
        }
        return res;
    }

    /**
     * Sets the command binds container to this one
     * @param newmap Command binds mapper to set
     */
    public static void setCommandBinds(Map<String, String> newmap) {
        commandRoutes = newmap;
    }


}
