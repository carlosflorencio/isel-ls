package database.lib;


import java.math.BigDecimal;
import java.sql.*;

public class SqlQuery {

	private SqlConnection sqlcon;
	private PreparedStatement statement;
	private int index = 1;
	private boolean getID;
	
	/*
	|--------------------------------------------------------------------------
	| Constructors
	|--------------------------------------------------------------------------
	 */
	public SqlQuery(SqlConnection con, String sql, boolean getID) throws SQLException {
		this.create(con, sql, getID);
	}
	
	public SqlQuery(SqlConnection con, String sql) throws SQLException {
		this.create(con, sql, false);
	}
	
	private void create(SqlConnection con, String sql, boolean getID) throws SQLException {
		this.sqlcon = con;
		this.getID = getID;
		
		if (getID) {
			this.statement = this.sqlcon.connection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		} else {
			this.statement = this.sqlcon.connection().prepareStatement(sql);
		}
		
		this.sqlcon.addStatement(statement);
	}

	/*
	|--------------------------------------------------------------------------
	| Add parameters
	|--------------------------------------------------------------------------
	 */
	public SqlQuery addString(String value) throws SQLException {

		statement.setString(index++, value);
		
		return this;
	}
	
	public SqlQuery addInt(int value) throws SQLException {

		statement.setInt(index++, value);
		
		return this;
	}
	
    public SqlQuery addDateTime(long timestamp) throws SQLException {
        statement.setTimestamp(index++, new Timestamp(timestamp));
        return this;
    }

    public SqlQuery addDouble(double value) throws SQLException {
        statement.setDouble(index++, value);
        return this;
    }

	/*
	|--------------------------------------------------------------------------
	| Executes
	|--------------------------------------------------------------------------
	 */
	
	//true if the first result is a ResultSet object; false if the first result is an update count or there is no result
	public boolean execute() throws SQLException {
		return statement.execute();
	}
	
	//List of Results
	public ResultSet executeQuery() throws SQLException {
        return statement.executeQuery();
    }
	
	public int executeUpdate() throws SQLException {
		int res = statement.executeUpdate();
		
		if( this.getID ) { //return last inserted id
			ResultSet rs = statement.getGeneratedKeys();
			while (rs.next()) {
				BigDecimal id = (BigDecimal) rs.getObject(1);
				
				if(id == null) throw new SQLException("Last inserted id is null");
				return id.intValueExact();
			}
		}
		
        return res;
    }

    public int count(String column) throws SQLException {
        ResultSet rs = this.statement.executeQuery();
        if (rs.next()){

            return rs.getInt(column);
        }
        throw new SQLException("That column does not exist to count");
    }
	
	/*
	|--------------------------------------------------------------------------
	| Close
	|--------------------------------------------------------------------------
	 */
	public void close() throws SQLException {
        this.sqlcon.removeStatement(statement);
        
        if(!statement.isClosed()) 
        	statement.close();
    }
}
