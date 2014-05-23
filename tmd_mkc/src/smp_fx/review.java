package smp_fx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by Afzalul Haque on 16-Apr-14.
 */
public class review {

    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

    int rev_id;
    String user_name;
    String review;
    int rating;
    place p;
    user u;


    void connectDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/tmd?" + "user=root&password=");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void closeConn() {
        try {
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    review(int id, place p) throws Exception {
        try {
            connectDB();
            ps = conn.prepareStatement("SELECT * FROM reviews WHERE review_id = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            rev_id = id;
            rs.next();
            review = rs.getString("review");
            rating = rs.getInt("rating");
            u = warehouse.getUserInstance(rs.getInt("user_id"));
            this.p = p;
        } catch (Exception e) {
            System.out.println(e);
        }
        closeConn();
    }

    review(int id) {
        try {
            connectDB();
            ps = conn.prepareStatement("SELECT * FROM reviews WHERE review_id = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            rev_id = id;
            rs.next();
            review = rs.getString("review");
            rating = rs.getInt("rating");
            u = warehouse.getUserInstance(rs.getInt("user_id"));
            this.p = warehouse.getPlaceInstance(rs.getInt("place_id"));
        } catch (Exception e) {
            System.out.println(e);
        }
        closeConn();
    }
}
