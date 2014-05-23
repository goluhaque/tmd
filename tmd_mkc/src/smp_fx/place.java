package smp_fx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by Afzalul Haque on 14-Apr-14.
 */
public class place {

    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

    int place_id;
    category cat;
    user u;
    String location;
    String name;            //name of the place

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

    place(int id) throws Exception {
        try {
            connectDB();
            ps = conn.prepareStatement("SELECT * FROM places WHERE place_id = ? ;");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            rs.next();
            place_id = id;
            u = warehouse.getUserInstance(rs.getInt("user_id"));
            location = rs.getString("location");
            name = rs.getString("name");            //name of the place
            cat = warehouse.getCategoryInstance(rs.getInt("category_id"));
            closeConn();
        } catch(Exception e) {
            System.out.println(e);
        }
        closeConn();
    }

    review[] getReviews() {
        try {
            connectDB();
            ps = conn.prepareStatement("SELECT count(*) as c FROM reviews WHERE place_id = ? ;");
            ps.setInt(1, place_id);
            rs = ps.executeQuery();
            rs.next();
            int num = rs.getInt("c");
            review[] r = null;
            if(num > 0) {
                r = new review[num];
                ps = conn.prepareStatement("SELECT review_id FROM reviews WHERE place_id = ? ;");
                ps.setInt(1, place_id);
                rs = ps.executeQuery();
                int i1 = 0;
                while(rs.next())
                    r[i1++] = warehouse.getReviewInstance(rs.getInt("review_id"));
            }
            closeConn();
            return r;
        } catch(Exception e) {
            System.out.println(e);
        }
        closeConn();
        return null;
    }
}
