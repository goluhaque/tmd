package smp_fx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by Afzalul Haque on 22-Apr-14.
 */
public class random {

    protected static Connection conn = null;
    protected static PreparedStatement ps = null;
    protected static ResultSet rs = null;

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

    static user[] getTop4User() {
        try{
            //System.out.println("Inside getTop4USer");
            connectDB();
            ps = conn.prepareStatement("SELECT count(*) as c FROM users ORDER BY followers_no DESC ;");
            rs = ps.executeQuery();
            rs.next();
            int num = rs.getInt("c");
            user[] u = null;
            //System.out.println("num: " + num);
            if(num > 0) {
                //System.out.println("num > 0");
                ps = conn.prepareStatement("SELECT user_id FROM users ORDER BY followers_no DESC LIMIT 4 ;");
                rs = ps.executeQuery();
               // System.out.println("query executed");
                u = new user[(num < 4) ? num : 4];
                int i1 = 0;
                while (rs.next())
                    u[i1++] = warehouse.getUserInstance(rs.getInt("user_id"));
                //System.out.println("after while loop of getTop4USer");
            }
            closeConn();
            //sSystem.out.println("exiting getTop4USer");
            return u;
        } catch(Exception e) {
            System.out.println(e);
        }
        return null;
    }

    static post getTopQues() {
        try {
            connectDB();;
            ps = conn.prepareStatement("SELECT post_id FROM threads WHERE net_votes = (SELECT MAX(net_votes) FROM threads) ;");
            rs = ps.executeQuery();
            int id = 0;
            post p = null;
            if(rs.next()) {
                id = rs.getInt("post_id");
                p = warehouse.getPostInstance(id);
            }
            closeConn();
            return p;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    static thread[] getTop6Ques() {
        try{
            connectDB();
            ps = conn.prepareStatement("SELECT count(*) as c FROM threads ORDER BY net_votes DESC LIMIT 6 ;");
            rs = ps.executeQuery();
            rs.next();
            int num = rs.getInt("c");
            thread[] t = null;
            //System.out.println("num: " + num);
            if(num > 0) {
                ps = conn.prepareStatement("SELECT thread_id FROM threads ORDER BY net_votes DESC LIMIT 6 ;");
                rs = ps.executeQuery();
                t = new thread[(num < 6) ? num : 6];
                int i1 = 0;
                while (rs.next()) {
                //    System.out.println("Sending for initialization: " + rs.getInt("thread_id"));
                      t[i1++] = warehouse.getThreadInstance(rs.getInt("thread_id"));
                }
            }
            closeConn();
            return t;
        } catch(Exception e) {
            System.out.println(e);
        }
        closeConn();
        return null;
    }

    static void setThreads() throws Exception {
        try{
            connectDB();
            ps = conn.prepareStatement("SELECT forum_id FROM forums ORDER BY forum_id ASC");
            rs = ps.executeQuery();
            ProfiletabsController.f = new forum[6];
            int i1 = 0;
            while(rs.next()) {
                ProfiletabsController.f[i1] = new forum(rs.getInt("forum_id"));
                warehouse.addInstance(ProfiletabsController.f[i1++]);
            }
            ps = conn.prepareStatement("SELECT cat_id FROM categories ORDER BY cat_id ASC");
            rs = ps.executeQuery();
            ProfiletabsController.c = new category[6];
            i1 = 0;
            while(rs.next()) {

                ProfiletabsController.c[i1] = new category(rs.getInt("cat_id"));
                //System.out.println("Setting category table. Value of cat_id:" + ProfiletabsController.c[i1].cat_id);
                warehouse.addInstance(ProfiletabsController.c[i1++]);
            }
            closeConn();
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    static user getUOB(String username) {                //get user object for chat
        //System.out.println("inside getUOB1");
        try {
            //System.out.println("inside getUOB");
            if(username.equals(null) || username.equals("")) {
                System.out.println("username is null");
                throw (new Exception());
            }
            connectDB();
            ps = conn.prepareStatement("SELECT user_id FROM users WHERE username = ? ; ");
            ps.setString(1, username);
            rs = ps.executeQuery();
            rs.next();
            //System.out.println("query in getUOB executed successfully");
            user u = warehouse.getUserInstance(rs.getInt("user_id"));
            closeConn();
            return u;
        } catch (Exception e) {
            System.out.println(e);
        }
        closeConn();
        return null;
    }
}
