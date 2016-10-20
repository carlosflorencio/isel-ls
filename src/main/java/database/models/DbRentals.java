package database.models;

import app.entities.RentalEntity;
import database.lib.SqlConnection;
import database.lib.SqlQuery;
import org.joda.time.DateTime;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

public class DbRentals extends DbModel {

    /*
    |--------------------------------------------------------------------------
    | Create a new rental
    |--------------------------------------------------------------------------
     */
    public static RentalEntity rental(SqlConnection con, int idProperty, String username, int year, int week) throws SQLException {
        int rows = new SqlQuery(con, "INSERT INTO Rentals (idProp,username, yearRen, weekRen, stateRen, dateRequest) VALUES (?, ?, ?, ?, ?, ?)")
                .addInt(idProperty)
                .addString(username)
                .addInt(year)
                .addInt(week)
                .addString("pending")
                .addDateTime(new DateTime().getMillis())
                .executeUpdate();

        return getRentalByPropYearWeek(con, idProperty, year, week);
    }


    /*
    |--------------------------------------------------------------------------
    | GET Rentals by Property
    |--------------------------------------------------------------------------
     */
    public static List<RentalEntity> getRentalsByPropId(SqlConnection con, int id) throws SQLException {
        ResultSet rs = new SqlQuery(con, "SELECT * FROM Rentals WHERE idProp = ? ORDER BY dateRequest DESC")
                .addInt(id)
                .executeQuery();
        return listRentals(rs);
    }

    /*
    |--------------------------------------------------------------------------
    | Update Rental
    |--------------------------------------------------------------------------
     */
    public static boolean updateRentalStateToRented(SqlConnection con, int id, int y, int w) throws SQLException {
        int i = new SqlQuery(con,
                             "UPDATE Rentals SET stateRen=?, dateAccept=? WHERE idProp=? AND yearRen=? AND weekRen=?")
                .addString("rented")
                .addDateTime(new DateTime().getMillis())
                .addInt(id)
                .addInt(y)
                .addInt(w)
                .executeUpdate();
        return i == 1;
    }


    /*
    |--------------------------------------------------------------------------
    | GET Rental by year and week
    |--------------------------------------------------------------------------
     */
    public static RentalEntity getRentalByPropYearWeek(SqlConnection con, int id, int y, int w) throws SQLException {
        ResultSet rs = new SqlQuery(con, "SELECT * FROM Rentals WHERE idProp=? AND yearRen=? AND weekRen=?")
                .addInt(id)
                .addInt(y)
                .addInt(w)
                .executeQuery();

        RentalEntity r = null;
        if (rs.next()) {
            Timestamp taccept = rs.getTimestamp("dateAccept");
            DateTime accept = taccept == null ? null : new DateTime(taccept.getTime());
            r = new RentalEntity(rs.getInt("idProp"),
                                 rs.getString("username"),
                                 rs.getInt("yearRen"),
                                 rs.getInt("weekRen"),
                                 rs.getString("stateRen"),
                                 new DateTime(rs.getTimestamp("dateRequest").getTime()),
                                 accept
            );
        }

        return r;
    }

    /*
    |--------------------------------------------------------------------------
    | Delete Rental
    |--------------------------------------------------------------------------
     */
    public static boolean deleteRental(SqlConnection con, int id, int y, int w) throws SQLException {
        int res = new SqlQuery(con, "DELETE FROM Rentals WHERE idProp=? AND yearRen=? AND weekRen=? AND stateRen=?")
                .addInt(id)
                .addInt(y)
                .addInt(w)
                .addString("pending")
                .executeUpdate();
        return res == 1;
    }


    /*
    |--------------------------------------------------------------------------
    | GET Rentals by Username
    |--------------------------------------------------------------------------
     */
    public static List<RentalEntity> getRentalsByUsername(SqlConnection con, String user) throws SQLException {
        ResultSet rs = new SqlQuery(con, "SELECT * FROM Rentals WHERE username=? ORDER BY dateRequest DESC")
                .addString(user)
                .executeQuery();
        return listRentals(rs);
    }

    public static List<RentalEntity> getRentalsByPropYear(SqlConnection con, int id, int y) throws SQLException {
        ResultSet rs = new SqlQuery(con, "SELECT * FROM Rentals WHERE idProp=? AND yearRen=?")
                .addInt(id)
                .addInt(y)
                .executeQuery();

        return listRentals(rs);
    }

    /*
    |--------------------------------------------------------------------------
    | Convert to list
    |--------------------------------------------------------------------------
     */
    private static List<RentalEntity> listRentals(ResultSet rs) throws SQLException {
        List<RentalEntity> rentals = new LinkedList<RentalEntity>();

        while (rs.next()) {
            Timestamp taccept = rs.getTimestamp("dateAccept");
            DateTime accept = taccept == null ? null : new DateTime(taccept.getTime());
            RentalEntity r = new RentalEntity(rs.getInt("idProp"),
                                              rs.getString("username"),
                                              rs.getInt("yearRen"),
                                              rs.getInt("weekRen"),
                                              rs.getString("stateRen"),
                                              new DateTime(rs.getTimestamp("dateRequest").getTime()),
                                              accept
            );
            rentals.add(r);
        }
        return rentals;
    }


}
