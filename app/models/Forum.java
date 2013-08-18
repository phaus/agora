/**
 * Forum 17.08.2013
 *
 * @author Philipp Haussleiter
 *
 */
package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import play.db.jpa.Model;

@Entity
public class Forum extends Model {

    @OneToMany(mappedBy = "forum")
    public List<Thread> threads;
    @OneToMany(mappedBy = "forum")
    public List<Post> posts;
    public String name;

    public Forum(String name) {
        this.name = name;
        threads = new ArrayList<Thread>();
        posts = new ArrayList<Post>();
    }

    public int getPostCount() {
        return posts.size();
    }

    public int getThreadCount() {
        return threads.size();
    }

    public void addThread(Thread t) {
        if (t != null) {
            threads.add(t);
            save();
        }
    }

    public void removeThread(Thread t) {
        if (t != null) {
            threads.remove(t);
            save();
        }
    }

    public void addPost(Post p) {
        if (p != null) {
            posts.add(p);
            save();
        }
    }

    public void removePost(Post p) {
        if (p != null) {
            posts.remove(p);
            save();
        }
    }

    public List<Thread> getThreadsSince(Date lastVisit, int page, int count) {
        return Thread.find("forum = ? and (created > ? or updated > ?) ORDER BY created DESC LIMIT ?,?", this, lastVisit, lastVisit, page, count).fetch();
    }

    public List<Thread> getPostssSince(Date lastVisit, int page, int count) {
        return Post.find("forum = ? and  (created > ? or updated > ?) ORDER BY created DESC LIMIT ?,?", this, lastVisit, lastVisit, page, count).fetch();
    }

    public List<Thread> getThreads(int page, int count) {
        return Thread.find("forum = ? ORDER BY created DESC LIMIT ?,?", this, page, count).fetch();
    }

    public List<Post> getPosts(int page, int count) {
        return Post.find("forum = ? ORDER BY created DESC LIMIT ?,?", this, page, count).fetch();
    }

    @Override
    public String toString() {
        return this.name+" T:"+getThreadCount()+" P:"+getPostCount();
    }
}
