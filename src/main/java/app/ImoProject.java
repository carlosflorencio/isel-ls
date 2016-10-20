package app;

import app.config.Configuration;
import app.console.ConsoleCommandFactory;
import commands.container.IContainer;
import commands.container.TreeContainer;

import java.io.IOException;
import java.util.Scanner;

public class ImoProject {
	
	private static IContainer container = new TreeContainer();
	private static Configuration config;
    private static String environment = "test";

    /**
     * Main program method
     * @param args String[] parameters
     */
	public static void main(String[] args) {
		try {
            environment = "production";
			config = new Configuration();
            RegisterCommands.register(container);

			if(validArgs(args)) {
                String params = args.length == 3 ? args[2] : "";
                ConsoleCommandFactory.parse(args[0] + args[1] + params);
            } else
                interactiveMode();

		} catch (IOException e) {
			System.out.println("Error loading config file.");
		}
	}

    /**
     * Interactive console mode
     */
    private static void interactiveMode() {
        Scanner input = new Scanner(System.in);
        String line = null, iMode = "interactive mode> ";

        while(true) {
            System.out.print(iMode);
            line = input.nextLine();

            if(line.equalsIgnoreCase("exit")) break;

            ConsoleCommandFactory.parse(line);
        }
    }

    /**
     * Simple lenght validation of the parameters array
     * @param args String[] parameters
     * @return boolean
     */
	private static boolean validArgs(String[] args) {
        return args.length >= 1;
    }

    public static void trace(String msg) {
        System.out.println(msg);
    }

    /*
    |--------------------------------------------------------------------------
    | Getters
    |--------------------------------------------------------------------------
    */
    /**
     * Getter for the config
     * @return Configuration
     */
	public static Configuration getConfig() {
		return config;
	}

    /**
     * Gets the right environment
     * @return String
     */
    public static String getEnvironment() {
        return environment;
    }

    /**
     * @return Gets the default commands container
     */
    public static IContainer getContainer() {
        return container;
    }

}
