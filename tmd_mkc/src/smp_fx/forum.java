package smp_fx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by Afzalul Haque on 10-Apr-14.
 */
public class forum {
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs;

    protected int forum_id;
    protected String name;
//   protected forum f;              //parent


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

    forum(int id, String n) {
        forum_id = id;
        name = n;
    }
/*
    forum(int id, String n, forum f) {
        forum_id = id;
        name = n;
        this.f = f;
    }
*/
    forum(int id) throws Exception {
        try {
            forum_id = id;
            connectDB();
            ps = conn.prepareStatement("SELECT * FROM forums WHERE forum_id = ?");
            ps.setInt(1, id);
            //System.out.println("IN FORUMS Contructor. Before select execution.");
            rs = ps.executeQuery();
            rs.next();
            //System.out.println("IN FORUMS Contructor. Select completed.");

            name = rs.getString("name");
            /*
            int f_id = rs.getInt("parent_id");
            if(!rs.wasNull())
                setParent(f_id);
             */
            closeConn();
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    /*
    protected void setParent()throws Exception {
        try {
            connectDB();
            ps = conn.prepareStatement("SELECT parent_id FROM forums WHERE forum_id  = ? AND parent_id <> 0 ;");
            ps.setInt(1, forum_id);
            rs = ps.executeQuery();
            if(rs.next())
                f = warehouse.getForumInstance(rs.getInt("parent_id"));
            closeConn();
        } catch(Exception e) {
            System.out.println(e);
        }
        closeConn();
    }

    protected void setParent(int parent_id)throws Exception {
        try {
            f = warehouse.getForumInstance(parent_id);
        } catch(Exception e) {
            System.out.println(e);
        }
    }
    */
    protected thread[] getThreads() {
        try {
            connectDB();
            ps = conn.prepareStatement("SELECT count(*) as c FROM threads WHERE forum_id  = ? ;");
            ps.setInt(1, forum_id);
            rs = ps.executeQuery();
            rs.next();
            int num = rs.getInt("c");
            thread[] arr = null;
            if(num > 0) {
                ps = conn.prepareStatement("SELECT thread_id FROM threads WHERE forum_id  = ? ORDER BY net_votes DESC LIMIT 6 ;");
                ps.setInt(1, forum_id);
                rs = ps.executeQuery();

                arr = new thread[(num < 6) ? num : 6];
                //getting number of records
                int i1 = 0;
                while (rs.next())
                        arr[i1++] = warehouse.getThreadInstance(rs.getInt("thread_id"));
            }
            closeConn();
            return arr;
        }
        catch(Exception e) {
            System.out.println("error is getThreads(): " + e);
        }
        closeConn();
        return null;
    }

}
