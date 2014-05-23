package smp_fx;

import java.sql.*;
import java.util.Calendar;
import java.util.Timer;

/**
 * Created by Afzalul Haque on 28-Mar-14.
 */
public class user {

    protected Connection conn = null;
    protected PreparedStatement ps = null;



    protected int user_id;
    protected String username;
    protected String full_name;
    protected int net_votes;
    protected String shadow;
    protected int fing;
    protected int fwers;
    protected userfeed u_f;
    protected String email;
    protected userprofile u_p;
    protected ResultSet rs;
    protected chat c;


    void connectDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/tmd?" + "user=root&password=");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void closeConn()    {
        try {
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    user() {
        user_id = 0;
        username = "";
        net_votes = 0;
        shadow = "";
        fing = 0;
        fwers = 0;
        u_f = new userfeed(this);
    }

    user(int u_id) {
        //System.out.println("in user(int) constructor");
        user_id = u_id;
        try {
            connectDB();
            ps = conn.prepareStatement("SELECT * FROM users WHERE user_id = ? ; ");
            ps.setInt(1, user_id);
            rs = ps.executeQuery();
            //System.out.println("user constructor after query");
            rs.next();
            //System.out.println("if rs.next() contains stuff");
            username = rs.getString("username");
            net_votes = rs.getInt("net_votes");
            shadow = rs.getString("shadow");
            fing = rs.getInt("following_no");
            fwers = rs.getInt("followers_no");
            //System.out.println(username + "following: " + fing + "followers: " + fwers);
            full_name = rs.getString("full_name");
            //System.out.println("user constructor 1");
            u_f = new userfeed(this);
            //System.out.println("user constructor 2");
            u_p = new userprofile(this);

            c = new chat(this, rs.getTimestamp("timestamp"));
            Timer timer = new Timer();
            Calendar date = Calendar.getInstance();
            timer.schedule(c, date.getTime(), 1000*2);

            closeConn();
            //System.out.println("user constructor completed ID: "+this.user_id);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    protected post[] getPosts()throws Exception {
        try {
            connectDB();
            ps = conn.prepareStatement("SELECT post_id FROM posts, count(*) as c WHERE user_id = ? ; ");
            ps.setInt(1, user_id);
            rs = ps.executeQuery();
            rs.next();
            int num = rs.getInt("c");
            int i1 = 0;
            post p[] = new post[num];
            do {
                p[i1++] = warehouse.getPostInstance(rs.getInt("post_id"));
            } while(rs.next());
            closeConn();
            return p;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    protected ResultSet getFollowers()throws Exception {
        try {
            if(fwers>0) {
                connectDB();
                ps = conn.prepareStatement("SELECT follower_id FROM dogear WHERE followed_id = ? ;");
                ps.setInt(1, user_id);
                rs = ps.executeQuery();
                closeConn();
                return rs;
            }
        } catch(Exception e) {
            System.out.println(e);
        }
        return null;
    }

    protected ResultSet getFollowing()throws Exception {
        try {
            if(fing>0) {
                connectDB();
                ps = conn.prepareStatement("SELECT follower_id dogear WHERE dogear.follower_id = ? ;");
                ps.setInt(1, user_id);
                rs = ps.executeQuery();
                closeConn();
                return rs;
            }
        } catch(Exception e) {
            System.out.println(e);
        }
        return null;
    }

    protected void addPost(String content, thread t, forum f, boolean q)throws Exception {
        try {
            connectDB();
            System.out.println("INSIDE addPost");
            ps = conn.prepareStatement("SELECT word FROM mod_words ;");
            rs = ps.executeQuery();
            while(rs.next())
                content = content.replaceAll(rs.getString("word"), "*");
            ps = conn.prepareStatement("INSERT INTO posts (user_id, content, type, thread_id, forum_id, timestamp) VALUES(?,?,?,?,?,?)");
            ps.setInt(1, user_id);
            ps.setString(2, content);
            ps.setBoolean(3, q);
            ps.setInt(4, t.thread_id);
            ps.setInt(5, f.forum_id);
            Timestamp x = new Timestamp(System.currentTimeMillis());
            x.setNanos(0);
            ps.setTimestamp(6, x);
            ps.executeUpdate();
            System.out.println("post added");
            t.total_ans += 1;

            ps = conn.prepareStatement("SELECT post_id FROM posts WHERE timestamp = ? ;");
            ps.setTimestamp(1, x);
            rs = ps.executeQuery();
            rs.next();
            post p = new post(rs.getInt("post_id"));
            closeConn();
            warehouse.addInstance(p);
            //return p;
        }
        catch (Exception e) {
            System.out.println(e);
        }
        //return null;
    }

    protected thread addQues(String title, String content, forum f) {
        try {
            connectDB();
            System.out.println("addQues() started");
            ps = conn.prepareStatement("SELECT word FROM mod_words ;");
            rs = ps.executeQuery();
            System.out.println("after selecting all words");
            while(rs.next()) {
                content = content.replaceAll(rs.getString("word"), "*");
                title = title.replaceAll(rs.getString("word"), "*");
            }

            ps = conn.prepareStatement("INSERT INTO threads (title, forum_id, timestamp, user_id) VALUES(?,?,?,?) ;");
            ps.setString(1, title);
            ps.setInt(2, f.forum_id);
            Timestamp x = new Timestamp(System.currentTimeMillis());
            x.setNanos(0);
            System.out.println(x);
            ps.setTimestamp(3, x);
            ps.setInt(4, this.user_id);
            ps.executeUpdate();
            System.out.println("insert thread creation");
            ps = conn.prepareStatement("SELECT thread_id FROM threads WHERE timestamp = ? ;");
            ps.setTimestamp(1, x);
            rs = ps.executeQuery();
            System.out.println("select from thread completed");
            rs.next();
            System.out.println(rs.getInt("thread_id"));
            thread t = new thread(rs.getInt("thread_id"));
            System.out.println("thread object creation");
            warehouse.addInstance(t);
            ps = conn.prepareStatement("INSERT INTO posts (user_id, content, type, thread_id, forum_id, timestamp) VALUES(?,?,?,?,?,?)");
            ps.setInt(1, this.user_id);
            ps.setString(2, content);
            ps.setBoolean(3, true);
            ps.setInt(4, t.thread_id);
            ps.setInt(5, t.f.forum_id);
            ps.setTimestamp(6, x);


            ps.executeUpdate();
            System.out.println("insert posts completed creation");
            ps = conn.prepareStatement("SELECT post_id FROM posts WHERE timestamp = ? ;");
            ps.setTimestamp(1, x);
            rs = ps.executeQuery();
            rs.next();
            int id = rs.getInt("post_id");
            t.p = warehouse.getPostInstance(id);
            System.out.println("post object creation");
            //post p = t.p;
            ps = conn.prepareStatement("UPDATE threads SET post_id = ? WHERE thread_id = ? ;");
            ps.setInt(1, id);
            ps.setInt(2, t.thread_id);
            ps.executeUpdate();
            closeConn();
            //warehouse.addInstance(p);
            System.out.println("creation of question complete: " + t.title);
            //t.p = p;
            return t;
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
/*
    protected boolean changePassword(String password)throws Exception {
        String gen = BCrypt.hashpw(password, BCrypt.gensalt(12));
        try {
            connectDB();
            ps = conn.prepareStatement("UPDATE table users SET password = ? WHERE user_id = ? ;");
            ps.setString(1, gen);
            ps.setInt(2, user_id);
            ps.executeUpdate();
            closeConn();
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
*/
    protected void bookmark(user u) {
        try {
            connectDB();
            System.out.println("Old fwers of " + u.full_name +" : " + u.fwers);
            u.fwers += 1;
            System.out.println("New fwers of " + u.full_name +" : " + u.fwers);
            System.out.println("Old fing of " + full_name +" : " + fing);
            this.fing += 1;
            System.out.println("New following of " + full_name +" : " + fing);
            ps = conn.prepareStatement("INSERT INTO bookmark VALUES(?,?) ;");                         //follower followed by following
            ps.setInt(1, user_id);
            ps.setInt(2, u.user_id);
            ps.executeUpdate();
            ps = conn.prepareStatement("UPDATE users SET following_no = ? WHERE user_id = ? ;");
            ps.setInt(1, this.fing);
            ps.setInt(2, user_id);
            ps.executeUpdate();
            ps = conn.prepareStatement("UPDATE users SET followers_no = ? WHERE user_id = ? ;");
            ps.setInt(1, u.fwers);
            ps.setInt(2, u.user_id);
            ps.executeUpdate();
            closeConn();
            //return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        //return false;
    }

    protected void dogear(thread t) {
        try {
            connectDB();
            ps = conn.prepareStatement("INSERT INTO dogear VALUES(?,?) ;");                         //follower followed by thread
            ps.setInt(1, user_id);
            ps.setInt(2, t.thread_id);
            ps.executeUpdate();
            closeConn();
          //  return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        //return false;
    }

    protected void vote(boolean u, thread t) {
        try {
            connectDB();

            t.net_votes -= ( u == true ) ? -1 : 1;
            t.p.u.net_votes -= ( u == true ) ? -1 : 1;
            //t.net_votes++;

            ps = conn.prepareStatement("UPDATE threads SET net_votes = ? WHERE thread_id = ? ;");
            ps.setInt(1, t.net_votes);
            ps.setInt(2, t.thread_id);
            ps.executeUpdate();

            ps = conn.prepareStatement("UPDATE users SET net_votes = ? WHERE user_id = ? ;");
            ps.setInt(1, t.p.u.net_votes);
            ps.setInt(2, t.p.u.user_id);
            ps.executeUpdate();

            closeConn();
        } catch(Exception e) {
            System.out.println(e);
        }
    }
    /*
    protected void chat(user u) {
        try {
            Timer timer = new Timer();
            Calendar date = Calendar.getInstance();
            //add

            Timestamp t = new Timestamp(System.currentTimeMillis());
            connectDB();
            ps = conn.prepareStatement("INSERT INTO chat (user1, user2, time) VALUES (?,?,?) ;");
            ps.setInt(1, user_id);
            ps.setInt(2, u.user_id);
            ps.setTimestamp(3, t);

            chat c = new chat(u);
            warehouse.addInstance(c);
            timer.schedule(
                    c,
                    date.getTime(),
                    1000 * 2
            );
            closeConn();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    */

    protected void sendMessage(String message, user u) {
        try {
            System.out.println("Inside send message:"+message+"\tsending to: "+u.full_name);
            connectDB();
            ps = conn.prepareStatement("INSERT INTO messages (timestamp, sender_id, rcvr_id, message) VALUES (?,?,?,?) ;");
            ps.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            ps.setInt(2, user_id);
            ps.setInt(3, u.user_id);
            ps.setString(4, message);
            ps.executeUpdate();
            System.out.println("sending completed");
            closeConn();
        } catch(Exception e) {
            System.out.println(e);
        }

    }

    protected void addReview(place p, String review, int rating) throws Exception {
        try {
            connectDB();
            ps = conn.prepareStatement("INSERT INTO reviews (user_id, review, place_id, rating, timestamp) VALUES (?,?,?,?,?) ;");
            ps.setInt(1, user_id);
            ps.setString(2, review);
            ps.setInt(3, p.place_id);
            ps.setInt(4, rating);
            Timestamp x = new Timestamp(System.currentTimeMillis());
            ps.setTimestamp(5, x);
            ps.executeUpdate();

            ps = conn.prepareStatement("SELECT review_id FROM reviews WHERE timestamp = ? ;");
            ps.setTimestamp(1, x);
            rs = ps.executeQuery();
            rs.next();
            int id = rs.getInt("review_id");
            closeConn();
            review rob = new review(id, p);
            warehouse.addInstance(rob);
            //return rob;

        } catch(Exception e) {
            System.out.println(e);
        }
//        /return null;
    }

    protected void addPlace(category cat, String location, String name) throws Exception {
        try {
            connectDB();
            System.out.println("addPlace started. for category: " + cat.cat_id + "\t name: " + cat.name);

            ps = conn.prepareStatement("INSERT INTO places (user_id, name, location, category_id, timestamp) VALUES (?,?,?,?,?) ;");
            ps.setInt(1, user_id);
            ps.setString(2, name);
            ps.setString(3, location);
            ps.setInt(4, cat.cat_id);
            Timestamp x = new Timestamp(System.currentTimeMillis());
            x.setNanos(0);
            ps.setTimestamp(5, x);
            ps.executeUpdate();
            System.out.println("INSERT completed");
            ps = conn.prepareStatement("SELECT place_id FROM places WHERE timestamp = ? ;");
            ps.setTimestamp(1, x);
            rs = ps.executeQuery();
            rs.next();
            int id = rs.getInt("place_id");
            closeConn();
            place pob = new place(id);
            warehouse.addInstance(pob);
            //return pob;
        } catch(Exception e) {
            System.out.println(e);
        }
//        /return null;
    }

    protected void logout() {
        try {
            connectDB();
            ps = conn.prepareStatement("UPDATE users SET timestamp = ? WHERE user_id = ? ;");
            ps.setInt(2,user_id);
            c.x.setNanos(0);
            ps.setTimestamp(1, c.x);
            ps.executeUpdate();
        } catch(Exception e) {
            System.out.println(e);
        }
        closeConn();
    }

    protected user search(String full_name) {
        try {
            connectDB();
            ps = conn.prepareStatement("SELECT user_id FROM users WHERE full_name LIKE ? ;");
            ps.setString(1, "%" + full_name + "%");
            rs = ps.executeQuery();
            rs.next();
            int id = rs.getInt("user_id");
            closeConn();
            return warehouse.getUserInstance(id);
        } catch(Exception e) {
            System.out.println("error in chat: " + e);
        }
        closeConn();
        return null;
    }
}