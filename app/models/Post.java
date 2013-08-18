/**
 * Post 17.08.2013
 *
 * @author Philipp Haussleiter
 *
 */
package models;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class Post extends Entry {

    @ManyToOne
    public Thread thread;
    @ManyToOne
    public Forum forum;
    @Lob
    public String content;

    public Post(Forum forum, Thread thread, User user) {
        super(user);
        this.forum = forum;
        this.forum.addPost(this);
        this.thread = thread;
        this.thread.addPost(this);
    }

    public void setContent(String content){
        this.content = content.trim();
        this.tags.clear();
        this.setTags(Tag.findOrCreateTagsForText(content));
    }
    
    @Override
    public String toString() {
        return "F: " + forum + " T:" + thread + " C:" + content.length();
    }
}
