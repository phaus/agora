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
    List<Post> createdPosts;
    @OneToMany(mappedBy = "updatedBy")
    List<Post> updatedPosts;
    @OneToMany(mappedBy = "createdBy")
    List<Thread> createdThreads;
    @OneToMany(mappedBy = "updatedBy")
    List<Thread> updatedThreads;
    public String username;
    public String password;
    public String salt;
    public String email;
    public Date created;
    public Date updated;

    public User(String username) {
        created = new Date();
        updated = new Date();
        createdPosts = new ArrayList<Post>();
        updatedPosts = new ArrayList<Post>();
        createdThreads = new ArrayList<Thread>();
        updatedThreads = new ArrayList<Thread>();
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
