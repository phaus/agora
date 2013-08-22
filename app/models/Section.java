/**
 * Section 22.08.2013
 *
 * @author Philipp Haussleiter
 *
 */
package models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import play.db.jpa.Model;

@Entity
public class Section extends Model {

    public String name;
    @OneToMany(mappedBy = "section")
    public List<Forum> forums;

    public Section(String name) {
        this.name = name;
        this.forums = new ArrayList<Forum>();
    }

    public List<Forum> getForums() {
        return Forum.find("section = ? ORDER BY sortorder DESC", this).fetch();
    }
    
    @Override
    public String toString(){
        return this.name;
    }
}
