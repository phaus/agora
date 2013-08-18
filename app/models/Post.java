/**
 * Post 17.08.2013
 *
 * @author Philipp Haussleiter
 *
 */
package models;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import play.db.jpa.Model;

@Entity
public class Post extends Model {

    @ManyToOne
    public User createdBy;
    @ManyToOne
    public User updatedBy;
    @ManyToOne
    public Thread thread;
    @ManyToOne
    public Forum forum;
    public Date created;
    public Date updated;
    @Lob
    public String content;

    public Post(Forum forum, Thread thread, User user) {
        created = new Date();
        updated = new Date();
        createdBy = user;
        updatedBy = user;
        this.forum = forum;
        this.forum.addPost(this);
        this.thread = thread;
        this.thread.addPost(this);
    }

    @Override
    public String toString() {
        return "F: " + forum + " T:" + thread + " C:" + content.length();
    }
}
