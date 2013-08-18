/**
 * User 17.08.2013
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
import play.libs.Codec;

@Entity
public class User extends Model {

    @OneToMany(mappedBy = "createdBy")
    List<Entry> createdEntries;
    @OneToMany(mappedBy = "updatedBy")
    List<Entry> updatedEntries;
    public String username;
    public String password;
    public String salt;
    public String email;
    public Date created;
    public Date updated;

    public User(String username) {
        created = new Date();
        updated = new Date();
        createdEntries = new ArrayList<Entry>();
        updatedEntries = new ArrayList<Entry>();
        this.username = username;
    }

    public void setPassword(String password) {
        this.salt = Codec.UUID().substring(0, 4);
        this.password = Codec.hexSHA1(salt + password);
    }

    public static boolean validate(String username, String password) {
        User u = User.find("username = ?", username.trim()).first();
        if (u == null) {
            return false;
        }
        String hash = Codec.hexSHA1(u.salt + password.trim());
        return hash.equals(u.password);
    }
}
