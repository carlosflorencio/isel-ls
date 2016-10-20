package database.models;

import app.entities.PropertyEntity;
import database.lib.SqlConnection;
import database.lib.SqlQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class DbProperties extends DbModel {

	/*
	|--------------------------------------------------------------------------
	| GET All properties
	|--------------------------------------------------------------------------
	 */
	public static List<PropertyEntity> getProperties(SqlConnection con) throws SQLException {
		ResultSet rs = new SqlQuery(con, "SELECT * FROM Properties ORDER BY id DESC").executeQuery();
		
		return listProperty(rs);
	}

	/*
	|--------------------------------------------------------------------------
	| GET Properties filter by local
	|--------------------------------------------------------------------------
	 */
	public static List<PropertyEntity> getPropertiesByLocal(SqlConnection con, String loc) throws SQLException {
		ResultSet rs = new SqlQuery(con, "SELECT * FROM Properties WHERE location LIKE ? ORDER BY id DESC")
							.addString("%" + loc + "%")
							.executeQuery();
		
		return listProperty(rs);
	}
	
	/*
	|--------------------------------------------------------------------------
	| GET Properties filter by owner
	|--------------------------------------------------------------------------
	 */
	public static List<PropertyEntity> getPropertiesByOwner(SqlConnection con, String owner) throws SQLException {
		ResultSet rs = new SqlQuery(con, "Select * from Properties where ownerName = ? ORDER BY id DESC")
							.addString(owner)
							.executeQuery();
		
		return listProperty(rs);
	}

	/*
	|--------------------------------------------------------------------------
	| GET Properties filter by type
	|--------------------------------------------------------------------------
	 */
	public static List<PropertyEntity> getPropertiesByType(SqlConnection con, String type) throws SQLException {
		ResultSet rs = new SqlQuery(con, "Select * from Properties where imoType = ? ORDER BY id DESC")
							.addString(type)
							.executeQuery();
		
		return listProperty(rs);
	}

	/*
	|--------------------------------------------------------------------------
	| GET Property by id
	|--------------------------------------------------------------------------
	 */
	public static PropertyEntity getPropertiesById(SqlConnection con, int id) throws SQLException {
		ResultSet rs = new SqlQuery(con, "SELECT * FROM Properties WHERE id = ?")
							.addInt(id)
							.executeQuery();
        PropertyEntity prop = null;
        if(rs.next()) {
            prop = new PropertyEntity(	    rs.getInt("id"),
                                            rs.getString("imoType"),
                                            rs.getString("imoDesc"),
                                            rs.getDouble("price"),
                                            rs.getString("location"),
                                            rs.getString("ownerName")
            );
        }

		return prop;
	}

	/*
	|--------------------------------------------------------------------------
	| POST insert property, return inserted id
	|--------------------------------------------------------------------------
	 */
	public static int insertProperty(SqlConnection con, PropertyEntity prop) throws SQLException {
		int lastInsertedID = new SqlQuery(con, "INSERT INTO Properties (imoType, imoDesc, price, location, ownerName) VALUES (?,?,?,?,?)", true)
							.addString(prop.getType())
							.addString(prop.getDesc())
							.addString(prop.getPrice() + "")
							.addString(prop.getLocalization())
							.addString(prop.getOwnerName())
							.executeUpdate();
		
		return lastInsertedID;
	}
    
    /*
    |--------------------------------------------------------------------------
    | Is owner of that property?
    |--------------------------------------------------------------------------
    */
    public static boolean isOwnerOfThisProperty(SqlConnection sqlcon, int id, String username) throws SQLException {
        int count = new SqlQuery(sqlcon, "SELECT COUNT(id) AS count FROM Properties WHERE id = ? and ownerName=?")
                .addInt(id)
                .addString(username)
                .count("count");
        return count == 1;
    }
    
    /*
    |--------------------------------------------------------------------------
    | Delete Propertie
    |--------------------------------------------------------------------------
    */
    public static boolean deletePropertyAndRentals(SqlConnection sqlcon, int id) throws SQLException {
        try {
            sqlcon.beginTransaction();


            int res = new SqlQuery(sqlcon, "DELETE FROM Rentals WHERE idProp=?")
                    .addInt(id)
                    .executeUpdate();

            int res2 = new SqlQuery(sqlcon, "DELETE FROM Properties WHERE idProp=?")
                    .addInt(id)
                    .executeUpdate();

            sqlcon.commit();
            sqlcon.endTransaction();
        } catch (SQLException e) {
            sqlcon.rollback();
            sqlcon.endTransaction();
        }

        return true;
    }
	
	/*
	|--------------------------------------------------------------------------
	| Convert to list
	|--------------------------------------------------------------------------
	 */
	private static List<PropertyEntity> listProperty(ResultSet rs) throws SQLException {
		List<PropertyEntity> prop = new LinkedList<PropertyEntity>();
		
		while (rs.next()) {
			PropertyEntity p = new PropertyEntity(	rs.getInt("id"),
										rs.getString("imoType"),
										rs.getString("imoDesc"), 
										rs.getDouble("price"),
										rs.getString("location"), 
										rs.getString("ownerName")
									);
			prop.add(p);
		}
		
		return prop;
	}

}
