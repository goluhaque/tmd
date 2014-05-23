package smp_fx;

/**
 * Created by Afzalul Haque on 14-Apr-14.
 */
public class moderator extends user {
    int test = 1;

    moderator(int u_id) {
        super(u_id);
        //System.out.println("after super(id) call moderator constructor");
    }

    void moderatePosts(String word) {            //p is the modified (only in the title and content) post
        try {
            connectDB();
            ps = conn.prepareStatement("INSERT INTO mod_words (word) VALUES(?)");
            ps.setString(1, word);
            ps.executeUpdate();

            ps = conn.prepareStatement("SELECT post_id FROM posts");
            rs = ps.executeQuery();

            while(rs.next()) {
                post p = warehouse.getPostInstance(rs.getInt("post_id"));
                p.modify(p.content.replaceAll(word, "*"));
            }

            closeConn();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
