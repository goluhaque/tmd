package smp_fx;

import java.sql.*;

/**
 * Created by Afzalul Haque on 14-Apr-14.
 */
public class GuestUser {
    protected static String salt = "$2a$12$ykvOVHAvuCHDwRycINkKde";
    protected static Connection conn = null;
    protected static PreparedStatement ps = null;
    protected static ResultSet rs;

    static void connectDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/tmd?" + "user=root&password=");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    static void closeConn() {
        try {
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    //name, email,shadow,username,password
    static user register(String n, String email, String s, String u, String p) throws Exception {                //return user object
        try {
            connectDB();
            String gen = BCrypt.hashpw(p, salt);
            ps = conn.prepareStatement("INSERT INTO users (full_name, email, shadow, username, timestamp, password) VALUES (?,?,?,?,?,?) ;");
            ps.setString(1,n);
            ps.setString(2,email);
            ps.setString(3,s);
            ps.setString(4,u);
            Timestamp x = new Timestamp(System.currentTimeMillis());
            ps.setTimestamp(5, x);
            ps.setString(6, gen);
            ps.executeUpdate();

            ps = conn.prepareStatement("SELECT user_id FROM users WHERE username = ? ;");
            ps.setString(1, u);
            rs = ps.executeQuery();
            rs.next();
            int id = rs.getInt("user_id");
            closeConn();
            user uob = new user(id);
            warehouse.addInstance(uob);
            return uob;

        } catch(Exception e) {
            System.out.println(e);
        }
        return null;
    }

    static user login(String username, String password) throws Exception {
        try {
            connectDB();
            String gen = BCrypt.hashpw(password, salt);
            ps = conn.prepareStatement("SELECT user_id, password, type FROM users WHERE username = ? ;");
            ps.setString(1, username);
            rs = ps.executeQuery();
            if(rs.next()) {
                if (!rs.getString("password").equals(gen))
                    return null;
                int id = rs.getInt("user_id");
                Boolean type = rs.getBoolean("type");
                System.out.println(type);
                closeConn();
                if(type == true) {
                    //System.out.println(warehouse.getUserInstance(id).full_name + "is not a moderator");
                    return warehouse.getUserInstance(id);
                }
                else {
                    //System.out.println(warehouse.getUserInstance(id).full_name + "is a moderator");
                    ProfiletabsController.m = warehouse.getModeratorInstance(id);
                    return ProfiletabsController.m;
                }
            }
        } catch(Exception e) {
            System.out.println(e);
        }
        closeConn();
        return null;
    }
    /*
    review addReview(place p, String name, String review, int rating) throws IOException {
        try {
            connectDB();
            ps = conn.prepareStatement("INSERT INTO reviews (user_name, review, place_id, rating, time) VALUES (?,?,?,?,?) ;");
            ps.setString(1,name);
            ps.setString(2, review);
            ps.setInt(3, p.place_id);
            ps.setInt(4, rating);
            Timestamp x = new Timestamp(System.currentTimeMillis());
            ps.setTimestamp(5, x);
            ps.executeUpdate();

            ps = conn.prepareStatement("SELECT id FROM reviews WHERE timestamp = ? ;");
            ps.setTimestamp(1, x);
            rs = ps.executeQuery();
            int id = rs.getInt("id");
            closeConn();
            review rob = new review(id, p);
            warehouse.addInstance(rob);
            return rob;

        } catch(Exception e) {
            System.out.println(e);
        }
        return null;
    }

    place addPlace(category cat, String name, String location, String user_name) throws IOException {
        try {
            connectDB();
            ps = conn.prepareStatement("INSERT INTO places (user_name, name, location, cat_id, time) VALUES (?,?,?,?,?) ;");
            ps.setString(1,user_name);
            ps.setString(2, name);
            ps.setString(3, location);
            ps.setInt(4, cat.cat_id);
            Timestamp x = new Timestamp(System.currentTimeMillis());
            ps.setTimestamp(5, x);
            ps.executeUpdate();

            ps = conn.prepareStatement("SELECT id FROM places WHERE timestamp = ? ;");
            ps.setTimestamp(1, x);
            rs = ps.executeQuery();
            int id = rs.getInt("id");
            closeConn();
            place pob = new place(id);
            warehouse.addInstance(pob);
            return pob;

        } catch(Exception e) {
            System.out.println(e);
        }
        return null;
    }
*/
}
