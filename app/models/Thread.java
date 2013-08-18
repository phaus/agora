/**
 * Thread 17.08.2013
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
import play.db.jpa.Model;

@Entity
public class Thread extends Model {

    @ManyToOne
    public User createdBy;
    @ManyToOne
    public User updatedBy;
    @ManyToOne
    public Forum forum;
    @OneToMany(mappedBy = "thread")
    public List<Post> posts;
    public boolean sticky;
    public String subject;
    public Date created;
    public Date updated;

    public Thread(String subject, Forum forum, User user) {
        this.subject = subject;
        if (forum != null && user != null) {
            this.forum = forum;
            this.forum.addThread(this);
            created = new Date();
            updated = new Date();
            createdBy = user;
            updatedBy = user;
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
    public String toString(){
        return this.subject+" P: "+posts.size();
    }    
}
