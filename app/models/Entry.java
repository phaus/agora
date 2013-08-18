/**
 * Entry 18.08.2013
 *
 * @author Philipp Haussleiter
 *
 */
package models;

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import play.db.jpa.Model;

@Entity
public class Entry extends Model {

    @ManyToOne
    public User createdBy;
    @ManyToOne
    public User updatedBy;
    @ManyToOne
    public DateDim created;
    @ManyToOne
    public DateDim updated;
    @ManyToMany
    public Set<Tag> tags;

    public Entry(User user) {
        created = DateDim.getDataDate(new Date());
        updated = DateDim.getDataDate(new Date());
        createdBy = user;
        updatedBy = user;
        tags = new TreeSet<Tag>();
    }

    public void setTags(Set<Tag> tags) {
        if (this.tags == null) {
            this.tags = new TreeSet<Tag>();
        }
        for (Tag t : tags) {
            this.tags.add(t);
        }
    }

    public void update() {
        updated = DateDim.getDataDate(new Date());
    }
}
