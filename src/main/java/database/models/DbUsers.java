package database.models;

import app.entities.UserEntity;
import database.lib.SqlConnection;
import database.lib.SqlQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class DbUsers extends DbModel {

    /*
    |--------------------------------------------------------------------------
    | GET All users
    |--------------------------------------------------------------------------
     */
    public static List<UserEntity> getUsers(SqlConnection con) throws SQLException {
        ResultSet rs = new SqlQuery(con, "SELECT * FROM Users ORDER BY id DESC").executeQuery();

        return listUsers(rs);
    }

    /*
    |--------------------------------------------------------------------------
    | GET User by userName
    |--------------------------------------------------------------------------
     */
    public static UserEntity getUserByUserName(SqlConnection con, String userName) throws SQLException {
        ResultSet rs = new SqlQuery(con, "SELECT * FROM Users WHERE username=?")
                .addString(userName)
                .executeQuery();

        UserEntity user = null;
        if(rs.next()) {
            user = new UserEntity(rs.getInt("id"),
                                             rs.getString("username"),
                                             rs.getString("passW"),
                                             rs.getString("email"),
                                             rs.getString("name")
            );
        }

        return user;
    }

    /*
    |--------------------------------------------------------------------------
    | Verify Auth
    |--------------------------------------------------------------------------
     */
    public static boolean getAuth(SqlConnection con, String authUser, String authPass) throws SQLException {
        int users = new SqlQuery(con, "SELECT COUNT(username) AS users FROM Users WHERE username=? AND passW=?")
                .addString(authUser)
                .addString(authPass)
                .count("users");
        return users == 1;
    }

    /*
    |--------------------------------------------------------------------------
	| POST insert user
	|--------------------------------------------------------------------------
	 */
    public static int insertUser(SqlConnection con, UserEntity u) throws SQLException {
        int id = new SqlQuery(con, "INSERT INTO Users (userName, passW, email, name) VALUES (?,?,?,?)", true)
                .addString(u.getUserName())
                .addString(u.getPassW())
                .addString(u.getEmail())
                .addString(u.getName())
                .executeUpdate();
        return id;
    }

    /*
    |--------------------------------------------------------------------------
    | Convert to list
    |--------------------------------------------------------------------------
     */
    private static List<UserEntity> listUsers(ResultSet rs) throws SQLException {
        List<UserEntity> users = new LinkedList<UserEntity>();

        while (rs.next()) {
            UserEntity user = new UserEntity(rs.getString("userName"),
                                             rs.getString("passW"),
                                             rs.getString("email"),
                                             rs.getString("name")
            );
            users.add(user);
        }
        return users;
    }
}
