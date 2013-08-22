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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Forum extends Model {

    @OneToMany(mappedBy = "forum")
    public List<Thread> threads = new ArrayList<Thread>();
    @OneToMany(mappedBy = "forum")
    public List<Post> posts = new ArrayList<Post>();
    @ManyToOne
    public Section section;
    @Required
    public String name;
    public int sortOrder;

    public Forum(String name) {
        this.name = name;
    }

    public long getPostCount() {
        return Post.count("forum = ?", this);
    }

    public long getThreadCount() {
        return Thread.count("forum = ?", this);
    }

    public long getAuthorCount() {
        return getAuthors().size();
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

    public List<models.User> getAuthors(){
        return Post.find("select distinct p.createdBy as user, p.updatedBy as user from Post p where p.forum = ?", this).fetch();
    }
    
    public List<models.Thread> getThreadsSince(Date lastVisit, int page, int count) {
        return Thread.find("forum = ? and (created > ? or updated > ?) ORDER BY created DESC LIMIT ?,?", this, lastVisit, lastVisit, page, count).fetch();
    }

    public List<models.Thread> getPostssSince(Date lastVisit, int page, int count) {
        return Post.find("forum = ? and  (created > ? or updated > ?) ORDER BY created DESC LIMIT ?,?", this, lastVisit, lastVisit, page, count).fetch();
    }

    public List<models.Thread> getThreads(int page, int count) {
        return Thread.find("forum = ? ORDER BY created DESC", this).fetch(page, count);
    }

    public List<models.Post> getPosts(int page, int count) {
        return Post.find("forum = ? ORDER BY created DESC", this).fetch(page, count);
    }

    @Override
    public String toString() {
        return this.name + " T:" + getThreadCount() + " P:" + getPostCount();
    }
}
