
import java.util.ArrayList;
import java.util.List;
import models.Forum;
import models.Post;
import models.User;
import models.Thread;

/**
 * Utils 17.08.2013
 *
 * @author Philipp Haussleiter
 *
 */
public class Utils {

    public static List<Long> users = new ArrayList<Long>();
    public static List<Long> forums = new ArrayList<Long>();
    public static List<Long> threads = new ArrayList<Long>();
    public static List<Long> posts = new ArrayList<Long>();

    public static void cleanAll() {
        cleanPosts();
        cleanThreads();
        cleanForums();
        cleanUsers();
    }

    public static User getTestUser() {
        String s = "U-" + System.currentTimeMillis();
        return getTestUser(s);
    }

    public static User getTestUser(String username) {
        User u = new User(username);
        u.save();
        Long id = u.id;
        users.add(id);
        return u;
    }

    public static void cleanUsers() {
        User u;
        for (Long id : users) {
            u = User.findById(id);
            if (u != null) {
                u.delete();
            }
        }
    }

    public static Forum getTestForum() {
        String s = "F-" + System.currentTimeMillis();
        Forum f = new Forum(s);
        f.save();
        Long id = f.id;
        forums.add(id);
        return f;
    }

    public static void cleanForums() {
        Forum f;
        for (Long id : forums) {
            f = Forum.findById(id);
            if (f != null) {
                f.delete();
            }
        }
    }

    public static Post getTestPost() {
        return getTestPost(getTestForum(), getTestThread(), getTestUser());
    }

    public static Post getTestPost(Forum f) {
        return getTestPost(f, getTestThread(), getTestUser());
    }

    public static Post getTestPost(Forum f, Thread t) {
        return getTestPost(f, t, getTestUser());
    }

    public static Post getTestPost(Forum f, Thread t, User u) {
        String s = "P-" + System.currentTimeMillis();
        Post p = new Post(f, t, u);
        p.content = s.trim();
        p.save();
        Long id = p.id;
        posts.add(id);
        return p;
    }

    public static void cleanPosts() {
        Post p;
        for (Long id : posts) {
            p = Post.findById(id);
            if (p != null) {
                p.delete();
            }
        }
    }

    public static Thread getTestThread() {
        return getTestThread(getTestForum(), getTestUser());
    }

    public static Thread getTestThread(Forum f) {
        return getTestThread(f, getTestUser());
    }

    public static Thread getTestThread(Forum f, User u) {
        String s = "T-" + System.currentTimeMillis();
        Thread t = new Thread(s, f, u);
        t.save();
        Long id = t.id;
        threads.add(id);
        return t;
    }

    public static void cleanThreads() {
        Thread t;
        for (Long id : threads) {
            t = Thread.findById(id);
            if (t != null) {
                t.delete();
            }
        }
    }

    public static String getContentTimes(String content, int times) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < times; i++) {
            sb.append(content);
        }
        return sb.toString();
    }
}
