package smp_fx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by Afzalul Haque on 01-Apr-14.
 */
public class userfeed {

    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet feed;

    user u;
    int limit_threads;
    int limit_users;


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
            feed.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    userfeed(user u) {
        this.u = u;
        limit_threads = 20;
        limit_users = 20;
    }

    userfeed(user u, int lim) {
        this.u =u;
        limit_threads = lim;
        limit_users = lim;
    }




    /*
    protected post getFeedRS()throws Exception {
        //TODO
        try {
            connectDB();
            String q1 = "SELECT posts.id, posts.title, posts.content, posts.forum_id, posts.net_votes, posts.user_id FROM posts JOIN dogear USING (posts.thread_id = dogear.thread_id) WHERE dogear.user_id = ? ORDER BY net_votes DESC LIMIT 5";
            String q2 = "SELECT posts.id, posts.title, posts.content, posts.forum_id, posts.net_votes, posts.user_id FROM posts JOIN dogear USING (posts.user_id = bookmark.followed_id) WHERE bookmark.follower_id = ? ORDER BY net_votes DESC LIMIT 5";
            ps = conn.prepareStatement(q1 + " UNION " + q2 + " ORDER BY net_votes DESC LIMIT 5 ;");


            ps.setInt(1, u.user_id);
            ps.setInt(2, limit_threads);
            ps.setInt(3, u.user_id);
            ps.setInt(4, limit_users);

            feed = ps.executeQuery();
            closeConn();
            return feed;
        } catch(Exception e) {
            System.out.println(e);
        }
        return null;
    }
    */
    protected post[] getFeed() {
        try {
            connectDB();
            String q1 = "SELECT posts.post_id as p_id, threads.net_votes as n_v FROM (posts NATURAL JOIN threads) JOIN dogear USING (thread_id) WHERE dogear.user_id = ? ORDER BY threads.net_votes DESC LIMIT 5";
            String q2 = "SELECT posts.post_id as p_id, threads.net_votes as n_v FROM (posts NATURAL JOIN threads) JOIN bookmark ON (posts.user_id = bookmark.following_id) WHERE bookmark.follower_id = ? ORDER BY threads.net_votes DESC LIMIT 5";

            ps = conn.prepareStatement("(" + q1 + ") UNION (" + q2 + ")");
            ps.setInt(1, u.user_id);
            ps.setInt(2, u.user_id);
            int[][] idnv = new int[10][2];
            feed = ps.executeQuery();
            //System.out.println("q1 executed");
            int i1 = 0;
            if (feed.next()) {
                do {
                    idnv[i1][0] = feed.getInt("p_id");
                    idnv[i1][1] = feed.getInt("n_v");
                    //System.out.println("Value of idnv: POST_ID:" + idnv[i1][0] + "\tNET VOTES" + idnv[i1][1]);
                    i1++;
                } while (feed.next());
            }
            closeConn();
            //connectDB();
            //System.out.println("going for next query");
            //ps = conn.prepareStatement(q1 + " UNION " + q2 + " ORDER BY net_votes DESC ;");
            /*
            ps = conn.prepareStatement(q2);
            ps.setInt(1, u.user_id);
            feed = ps.executeQuery();
            //System.out.println("q2 executed value of i1 is " + i1);
            if (feed.next()) {
                do {
                    //System.out.println("assigning to idnv: " + feed.getInt("post_id"));
                    idnv[i1][0] = feed.getInt("p_id");
                    idnv[i1][1] = feed.getInt("n_v");
                    //System.out.println("Value of idnv: POST_ID:" + idnv[i1][0] + "\tNET VOTES" + idnv[i1][1]);
                    i1++;
                } while (feed.next());
            }
            */
            //System.out.println("q2 assigned to idnv");
            int j;
            for (j = 1; j < 10; j++) {
                if (idnv[j][0] == 0) {
                    //System.out.println("j at the time of the break: " + j);
                    break;
                }
                for (int i = j - 1; i >= 0; i++) {
                    if (idnv[i][1] > idnv[i + 1][1])
                        break;

                    //swap otherwise
                    int key = idnv[j][1];
                    int key2 = idnv[j][0];
                    idnv[i + 1][1] = idnv[i][1];
                    idnv[i + 1][0] = idnv[i][0];
                    idnv[i][0] = key2;
                    idnv[i][1] = key;
                }
            }
            post[] arr = null;
            if (idnv[0][0] != 0) {
                arr = new post[j];
                //System.out.println("Size of ar in getFeed: " + arr.length + "value of idnv" + idnv[j][0]);
                int i = 0;
                //System.out.println("getting post array");
                while (i < j) {
                    //System.out.println("abcd");
                    //System.out.println("abcd" + idnv[i][0]);
                    arr[i] = warehouse.getPostInstance(idnv[i][0]);
                    //System.out.println(idnv[i][0]+ " \t" + arr[i].post_id);
                    i++;
                }

            }
            //closeConn();
            //System.out.println("Ending getFeed");
            return arr;
        } catch(Exception e) {
            System.out.println(e);
        }
        closeConn();
        return null;
    }
}
