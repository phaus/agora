
import models.User;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import play.libs.Codec;
import play.test.UnitTest;

/**
 * UserTest 17.08.2013
 *
 * @author Philipp Haussleiter
 *
 */
public class UserTest extends UnitTest {

    private String s;

    @Before
    public void setUp() {
        Utils.cleanAll();
    }

    @Test
    public void testCreateUser() {
        s = "U-" + System.currentTimeMillis();
        String password = "test1234";
        User u = new User(s);
        u.password = password;
        u.save();
        u = User.find("username = ?", s).first();
        assertNotNull(u);
        assertTrue(u.salt != null);
        assertTrue(u.salt.length() > 0);
        String hash = Codec.hexSHA1(u.salt + password);
        assertEquals(hash, u.password);
        u.delete();
    }

    @Test
    public void testValidateUser() {
        s = "U-" + System.currentTimeMillis();
        User u = Utils.getTestUser(s);
        String password = "test1234";
        u.password = password;
        assertTrue(User.validate(s, password));
    }
}
