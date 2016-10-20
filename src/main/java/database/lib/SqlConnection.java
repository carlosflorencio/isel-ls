package database.lib;


import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class SqlConnection {

	private SQLServerDataSource ds = null;
	private Connection con = null;
	private List<Statement> statements;
	
	public SqlConnection(String host, int port, String database, String user, String password) throws SQLException {
		this.ds= new SQLServerDataSource();
		this.statements = new LinkedList<Statement>();
		
		ds.setServerName(host);
		ds.setPortNumber(port);
		ds.setDatabaseName(database);
		ds.setUser(user);
		ds.setPassword(password);
		
		this.con = ds.getConnection();
	}
	
	/*
	|--------------------------------------------------------------------------
	| Transactions
	|--------------------------------------------------------------------------
	 */
	public void beginTransaction() throws SQLException {
		this.con.setAutoCommit(false);
	}
	
	public void endTransaction() throws SQLException {
		this.con.setAutoCommit(true);
	}
	
	public void commit() throws SQLException {
		this.con.commit();
	}
	
	public void rollback() throws SQLException {
		this.con.rollback();
	}
	
	/*
	|--------------------------------------------------------------------------
	| Connection
	|--------------------------------------------------------------------------
	 */
	public Connection connection() {
		return this.con;
	}
	
	public void close() throws SQLException {
		boolean connectionIsClosed = this.con.isClosed();

        if (!connectionIsClosed) {

            for (Statement statement : this.statements) {
            	if(!statement.isClosed()) statement.close();
            }
            this.statements.clear();

            boolean autoCommit = this.con.getAutoCommit();

            // if in transaction, rollback, otherwise just close
            if (autoCommit) {
                this.con.close();
            }
            else {
                this.con.rollback();
                this.con.close();
            }
        }
	}
	
	/*
	|--------------------------------------------------------------------------
	| Statements
	|--------------------------------------------------------------------------
	 */
	public void addStatement(Statement stmt) {
		this.statements.add(stmt);
	}
	
	public void removeStatement(Statement statement){
        this.statements.remove(statement);
    }

    public List<Statement> getStatements() {
        return this.statements;
    }
}
