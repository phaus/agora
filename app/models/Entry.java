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
    public DateDim createdDim;
    @ManyToOne
    public DateDim updatedDim;
    @ManyToMany
    public Set<Tag> tags;
    public Date created;
    public Date updated;

    public Entry(User user) {
        Date date = new Date();
        DateDim dateDim = DateDim.getDataDate(date);
        createdDim = dateDim;
        updatedDim = dateDim;
        created = date;
        updated = date;
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
        updatedDim = DateDim.getDataDate(new Date());
        updated = updatedDim.fullDate;
        save();
    }
}
