package smp_fx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by Afzalul Haque on 16-Apr-14.
 */
public class category {

    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

    int cat_id;
    String name;

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

    category(int id) throws Exception {
        try {
            connectDB();
            ps = conn.prepareStatement("SELECT * FROM categories WHERE cat_id = ? ;");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            rs.next();
            cat_id = id;
            name = rs.getString("name");
            closeConn();
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    category(int id, String name) {
        cat_id = id;
        this.name = name;
    }

    place[] getPlaces() {
        try {
            connectDB();
            ps = conn.prepareStatement("SELECT count(*) as c FROM places WHERE category_id = ?");
            ps.setInt(1, cat_id);
            rs = ps.executeQuery();
            rs.next();
            int num = rs.getInt("c");
            place[] p = null;
            if(num > 0) {
                ps = conn.prepareStatement("SELECT place_id FROM places WHERE category_id = ? ORDER BY timestamp DESC LIMIT 6");
                ps.setInt(1, cat_id);
                rs = ps.executeQuery();
                p = new place[(num < 6) ? num : 6];
                int i1 = 0;
                while(rs.next())
                    p[i1++] = warehouse.getPlaceInstance(rs.getInt("place_id"));
            }
            closeConn();
            return p;
        } catch(Exception e) {
            System.out.println(e);
        }
        closeConn();
        return null;
    }
}
