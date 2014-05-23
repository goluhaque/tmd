package smp_fx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * Created by Afzalul Haque on 28-Mar-14.
 */
public class post {
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs;


    int post_id;
    String content;
    user u;
    Timestamp x;
    boolean type;
    thread t;

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

    post(int p_id)throws Exception {
        post_id = p_id;
        try {
            connectDB();
            ps = conn.prepareStatement("SELECT * FROM posts WHERE post_id = ? ;");
            ps.setInt(1, p_id);

            rs = ps.executeQuery();
            rs.next();
            //System.out.println("select in post constructor completed");
            content = rs.getString("content");
            type = rs.getBoolean("type");
            x = rs.getTimestamp("timestamp");
            u = warehouse.getUserInstance(rs.getInt("user_id"));
            t = warehouse.getThreadInstance(rs.getInt("thread_id"));
            closeConn();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void modify(String content) {
        this.content = content;
        try {
            connectDB();
            ps = conn.prepareStatement("UPDATE posts SET content = ? WHERE post_id = ? ;");
            ps.setString(1, content);
            ps.setInt(2, post_id);
            ps.executeUpdate();
            closeConn();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
