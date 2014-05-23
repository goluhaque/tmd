package smp_fx;

import java.util.HashMap;

/**
 * Created by Afzalul Haque on 17-Apr-14.
 */
public class warehouse {

    private static HashMap <Integer, post> posts = new HashMap<Integer, post>();
    private static HashMap <Integer, user> users = new HashMap<Integer, user>();
    private static HashMap <Integer, thread> threads = new HashMap<Integer, thread>();
    private static HashMap <Integer, forum> forums = new HashMap<Integer, forum>();
    private static HashMap <Integer, chat> chats = new HashMap<Integer, chat>();
    private static HashMap <Integer, message> messages = new HashMap<Integer, message>();
    private static HashMap <Integer, place> places = new HashMap<Integer, place>();
    private static HashMap <Integer, review> reviews = new HashMap<Integer, review>();
    private static HashMap <Integer, category> categories = new HashMap<Integer, category>();
    private static HashMap <Integer, moderator> moderators = new HashMap<Integer, moderator>();

    static void addInstance(post p) {
        posts.put(p.post_id, p);
    }
    static void addInstance(user u) {
        users.put(u.user_id, u);
    }
    static void addInstance(thread t) {
        threads.put(t.thread_id, t);
    }
    static void addInstance(forum f) {
        forums.put(f.forum_id, f);
    }
    static void addInstance(message m) {
        messages.put(m.message_id, m);
    }
    static void addInstance(place p) {
        places.put(p.place_id, p);
    }
    static void addInstance(review r) {
        reviews.put(r.rev_id, r);
    }
    static void addInstance(category c) {
        categories.put(c.cat_id, c);
    }
    static void addInstance(moderator m) {
        moderators.put(m.user_id, m);
    }

    static post getPostInstance(int id) throws Exception {
        post p = posts.get(id);
        if(p == null) {
            p = new post(id);
            posts.put(id, p);
        }
        return p;
    }
    static user getUserInstance(int id) {
        user u = users.get(id);
        if(u == null) {
            u = new user(id);
            users.put(id, u);
        }
        return u;
    }
    static forum getForumInstance(int id) throws Exception {
        forum f = forums.get(id);
        if(f == null) {
            f = new forum(id);
            forums.put(id, f);
        }
        return f;
    }
    static place getPlaceInstance(int id) throws Exception {
        place p = places.get(id);
        if(p == null) {
            p = new place(id);
            places.put(id, p);
        }
        return p;
    }
    static thread getThreadInstance(int id) {
        //System.out.println("inside warehouse.getThreadInstance");
        thread t2 = threads.get(id);

        if(t2 == null) {
            //System.out.println("t isn't present in the hash");
            //System.out.println("id: " + id);
            t2 = new thread(id);
            //System.out.println("problem with thread (): " + e);
            threads.put(id, t2);
            t2.setQPost();
        }
        //System.out.println("returning object with title id of: " + t2.thread_id);
        return t2;
    }
    static category getCategoryInstance(int id) throws Exception {
        category cat = categories.get(id);
        if(cat == null) {
            cat = new category(id);
            categories.put(id, cat);
        }
        return cat;
    }
    static moderator getModeratorInstance(int id) {
        moderator m = moderators.get(id);
        //System.out.println("inside warehouse");
        if(m == null) {
            try {
                //System.out.println("getModeratorInstance() error. Before new moderator(id) constructor");
                m = new moderator(id);
               // System.out.println("getModeratorInstance() error. Before users.put");
                users.put(id, m);
                moderators.put(id, m);
            } catch(Exception e) {
                System.out.println("moderators warehouse error" + e);
            }

        }
        return m;
    }
    static review getReviewInstance(int id) {
        review rev = reviews.get(id);
        if(rev == null) {
            rev = new review(id);
            reviews.put(id, rev);
        }
        return rev;
    }
}
