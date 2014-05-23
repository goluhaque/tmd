package smp_fx;

import java.sql.Timestamp;

/**
 * Created by Afzalul Haque on 12-Apr-14.
 */
public class message {

    int message_id;
//    /chat c;
    user sender;
    user receiver;
    Timestamp t;
    String message;

    message(user r, user s, String m, Timestamp x) {
        //this.c = c;
        sender = s;
        receiver = r;
        message = m;
        t = x;
    }
}
