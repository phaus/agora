/**
 * Thread 17.08.2013
 *
 * @author Philipp Haussleiter
 *
 */
package models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Thread extends Entry {

    @ManyToOne
    public Forum forum;
    @OneToMany(mappedBy = "thread")
    public List<Post> posts;
    public boolean sticky;
    public String subject;

    public Thread(String subject, Forum forum, User user) {
        super(user);
        this.subject = subject;
        if (forum != null) {
            this.forum = forum;
            this.forum.addThread(this);
            posts = new ArrayList<Post>();
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

    public List<Post> getPosts(int page, int count) {
        return Post.find("thread = ? ORDER BY created DESC LIMIT ?,?", this, page, count).fetch();
    }

    @Override
    public String toString() {
        return this.subject + " P: " + posts.size();
    }
}
