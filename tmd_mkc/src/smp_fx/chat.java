package smp_fx;

import java.sql.*;
import java.util.TimerTask;

/**
 * Created by Afzalul Haque on 11-Apr-14.
 */

public class chat extends TimerTask {

    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs;

    user u;
    Timestamp x;


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

    chat(user u, Timestamp x) throws Exception {
        this.u = u;
        this.x = x;
    }

    public void run() {
        getNewMessages();
    }

    void getNewMessages() {
        try {
//            /System.out.println("getNewMEsssge() executed");
            connectDB();
            ps = conn.prepareStatement("SELECT count(*) as c FROM messages WHERE rcvr_id = ? AND timestamp > ? ORDER BY timestamp ASC;");
            ps.setInt(1, u.user_id);
            ps.setTimestamp(2, x);
            rs = ps.executeQuery();

            rs.next();
            int num = rs.getInt("c");
//            /System.out.println("getNewmessage select executed, c:"+num);
            if(num > 0) {
                ps = conn.prepareStatement("SELECT msg_id, message, timestamp, sender_id FROM messages WHERE rcvr_id = ? AND timestamp > ? ORDER BY timestamp ASC;");
                ps.setInt(1, u.user_id);
                ps.setTimestamp(2, x);
                rs = ps.executeQuery();
                message[] m1 = new message[num];
                int i = 0;
                while (rs.next()) {
                        user us = warehouse.getUserInstance(rs.getInt("sender_id"));
                        m1[i] = new message(u, us, rs.getString("message"), rs.getTimestamp("timestamp"));
                        warehouse.addInstance(m1[i++]);
                        x = rs.getTimestamp("timestamp");
                }
                ProfiletabsController.ptcob.setTXA(m1);
            }
        } catch(Exception e) {
            System.out.println(e);
        }
        closeConn();
    }
}

