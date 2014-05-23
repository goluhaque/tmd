package smp_fx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by Afzalul Haque on 16-Apr-14.
 */
public class userprofile {

    Connection conn;
    PreparedStatement ps;
    protected ResultSet rs;

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

    userprofile(user u) {
        this.u = u;
    }

    protected post[] getPosts() {
        try {
            connectDB();
            ps = conn.prepareStatement("SELECT count(*) as count FROM posts NATURAL JOIN threads WHERE user_id = ? ORDER BY net_votes DESC LIMIT 5 ;");
            ps.setInt(1, u.user_id);
            rs = ps.executeQuery();
            rs.next();
            int num = rs.getInt("count");
            post[] p = null;
            if(num > 0) {
                ps = conn.prepareStatement("SELECT post_id FROM posts NATURAL JOIN threads WHERE user_id = ? ORDER BY net_votes DESC LIMIT 5 ;");
                ps.setInt(1, u.user_id);
                rs = ps.executeQuery();
                p = new post[(num < 5) ? num : 5];
                int i1 = 0;
                while (rs.next())
                    p[i1++] = warehouse.getPostInstance(rs.getInt("post_id"));
            }
            closeConn();
            return p;
        } catch(Exception e) {
            System.out.println(e);
        }
        return null;
    }

    protected post[] getDogearedPosts() {
        try {
            connectDB();
            //System.out.println("started getDogearedPosts");
            ps = conn.prepareStatement("SELECT count(*) as c FROM threads JOIN dogear USING(thread_id) WHERE dogear.user_id = ? LIMIT 6 ;");
            ps.setInt(1, u.user_id);
            rs = ps.executeQuery();
            rs.next();
            int num = rs.getInt("c");
            //System.out.println("executed and got num: "+num);
            post[] p = null;
            if(num > 0) {
                ps = conn.prepareStatement("SELECT threads.post_id FROM threads JOIN dogear USING(thread_id) WHERE dogear.user_id = ? LIMIT 6 ;");
                ps.setInt(1, u.user_id);
                rs = ps.executeQuery();
                //System.out.println("execute inner query");
                int i1 = 0;
                p = new post[(num<6) ? num:6];
                while (rs.next())
                    p[i1++] = warehouse.getPostInstance(rs.getInt("post_id"));
               // System.out.println("got array p");
            }
            closeConn();
            return p;
        } catch (Exception e) {
            System.out.println(e);
        }
        closeConn();
        return null;
    }
}
