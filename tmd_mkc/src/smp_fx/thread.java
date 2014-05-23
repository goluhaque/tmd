package smp_fx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by Afzalul Haque on 31-Mar-14.
 */
public class thread {

    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs;

    int thread_id;
    forum f;                                            //Forum where the thread is located
    user u;                                             //User creating and posting the original question
    post p;                                             //Original question post
    String title;
    int net_votes;
    int total_ans;


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

    thread(int t_id, user u, post p, forum f) {
        thread_id = t_id;
        this.f = f;
        this.u = u;
        this.p = p;
     }

    thread(int t_id)  {
        thread_id = t_id;
        //System.out.println("Received constructor for: " + t_id);
        try {
            connectDB();
            ps = conn.prepareStatement("SELECT * FROM threads WHERE thread_id = ? ;");
            ps.setInt(1, t_id);

            rs = ps.executeQuery();
            rs.next();
            //System.out.println("select in thread constructor completed");
            f = warehouse.getForumInstance(rs.getInt("forum_id"));
            //System.out.println("select in thread constructor completed. FORUM successful\t "+rs.getInt("forum_id"));
            u = warehouse.getUserInstance(rs.getInt("user_id"));
            //System.out.println("select in thread constructor completed. USER successful\t "+rs.getInt("user_id"));
            title = rs.getString("title");
            net_votes = rs.getInt("net_votes");
            //System.out.println("net_votes for thread name:" + title + "is: " + net_votes);
           // System.out.println("select in thread constructor completed. TITLE successful\t "+rs.getString("title"));
            ps = conn.prepareStatement("SELECT count(*) as c FROM posts WHERE thread_id = ? ;");
            ps.setInt(1, t_id);
            rs = ps.executeQuery();
            //System.out.println("count in thread constructor completed");
            rs.next();
            total_ans = rs.getInt("c");
            //System.out.println("thread constructor completed");
            p = null;
            closeConn();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void setQPost(post p) {
        this.p = p;
    }

    void setQPost() {
        if(this.p != null)
            return;
        try {
            connectDB();
            //System.out.println(thread_id);
            ps = conn.prepareStatement("SELECT post_id FROM posts WHERE type = ? AND thread_id = ? ;");
            ps.setBoolean(1, true);
            ps.setInt(2, thread_id);

            rs = ps.executeQuery();
            if(rs.next()) {
               //System.out.println("in setQPost(). Value of first rs.next(): " + b);
               //System.out.println(rs.getInt("post_id"));
               p = warehouse.getPostInstance(rs.getInt("post_id"));
                /*
                if(p != null)
                    System.out.println("setting of Q post is complete, post_id is:"+this.p.post_id);
                    */
            }
           closeConn();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    /*
    protected ResultSet getPosts()throws Exception {
        try {
            connectDB();
            ps = conn.prepareStatement("SELECT * FROM posts WHERE thread_id  = ? ;");
            ps.setInt(1, thread_id);
            rs = ps.executeQuery();
            closeConn();
			return rs;
        }
        catch(Exception e) {
            System.out.println(e);
        }
        return null;
    }
    */
    protected post[] getPosts()throws Exception {
        try {
            //System.out.println("starting of getPosts:" + total_ans);

            connectDB();
            //System.out.println("Starting of getPosts() of " + thread_id + " with the title of :" + title);
            ps = conn.prepareStatement("SELECT count(*) as c FROM posts WHERE thread_id  = ? AND type = ? ORDER BY timestamp ASC ;");
            ps.setInt(1, thread_id);
            ps.setBoolean(2, false);
            rs = ps.executeQuery();
            rs.next();
            int num = rs.getInt("c");
            ps = conn.prepareStatement("SELECT post_id FROM posts WHERE thread_id  = ? AND type = ? ORDER BY timestamp ASC ;");
            ps.setInt(1, thread_id);
            ps.setBoolean(2, false);
            rs = ps.executeQuery();
            //System.out.println("Inside getPosts(). Query executed.");
            post[] p = null;

            if(num != 0) {

                //System.out.println("Inside getPosts(). rs mein next hai. Value of num:" + num);
                p = new post[num];
                //System.out.println("P[] array length: " +p.length);
                int i1 = 0;
                while(rs.next()) {
                    int id = rs.getInt("post_id");
                    //System.out.println(id);
                    p[i1++] = warehouse.getPostInstance(id);
                    //System.out.println("p current index received:  "+p[i1-1].post_id +"\t i1:"+i1);
                }
            }
            closeConn();
            return p;
        }
        catch(Exception e) {
            System.out.println(e);
        }
        closeConn();
        return null;
    }
    /*
    protected ResultSet getPosts(post p[])throws Exception {
        try {
            //constructing the query string
            String s = "SELECT * FROM posts WHERE thread_id  = ?";

            for(int i = 0 ; i < p.length ; i++)
                s += " AND post_id <> ?";
            s += " ; ";



            connectDB();
            ps = conn.prepareStatement(s);
            ps.setInt(1, thread_id);
            for(int i = 2 ; i < p.length+2 ; i++)
                ps.setInt(i, p[i].post_id);

            rs = ps.executeQuery();
            closeConn();
			return rs;
        }
        catch(Exception e) {
            System.out.println(e);
        }
        return null;
    }

    protected ResultSet getFollowers(user u[])throws Exception {
        try {
               //constructing the query string
            String s = "SELECT * FROM dogear JOIN users WHERE thread_id  = ?";
            for(int i = 0 ; i < u.length ; i++)
                s += " AND user_id <> ?" ;
            s += " ; ";


            connectDB();
            ps = conn.prepareStatement(s);
            ps.setInt(1, thread_id);
            for(int i = 2 ; i < u.length+2 ; i++)
                ps.setInt(i, u[i].user_id);
            rs = ps.executeQuery();
            closeConn();
			return rs;
        }
        catch(Exception e){
            System.out.println(e);
        }
        return null;
    }

    protected ResultSet getFollowers()throws Exception {
        try {
            connectDB();
            ps = conn.prepareStatement("SELECT * FROM dogear JOIN users WHERE thread_id  = ? ;");
            ps.setInt(1, thread_id);
            rs = ps.executeQuery();
            closeConn();
			return rs;
        }
        catch(Exception e){
            System.out.println(e);
        }
        return null;
    }
    */
}
