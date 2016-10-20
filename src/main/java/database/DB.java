package database;

import app.ImoProject;
import app.config.Configuration;
import database.lib.SqlConnection;

import java.io.IOException;
import java.sql.SQLException;

public class DB {
	
	public static SqlConnection getConnection() throws SQLException {
        if(ImoProject.getEnvironment().equals("test"))
            return getTestConnection();

		Configuration config = ImoProject.getConfig();
		return new SqlConnection(	config.load("db.host"),
									Integer.parseInt(config.load("db.port")),
									config.load("db.name"), config.load("db.user"),
									config.load("db.password")
								);
	}


	protected static SqlConnection getTestConnection() throws SQLException {

        try {
            Configuration config = new Configuration();
            return new SqlConnection(	config.load("dbtest.host"),
                                         Integer.parseInt(config.load("dbtest.port")),
                                         config.load("dbtest.name"), config.load("dbtest.user"),
                                         config.load("dbtest.password")
            );
        } catch (IOException e) {
            return null; //TODO:this is ugly
        } catch (SQLException e) {
            throw e;
        }
	}

}
