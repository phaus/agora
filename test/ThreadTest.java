
import models.Forum;
import models.Thread;
import models.User;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import play.test.UnitTest;

/**
 * ThreadTest 17.08.2013
 *
 * @author Philipp Haussleiter
 *
 */
public class ThreadTest extends UnitTest {

    private String s;

    @Before
    public void setUp() {
        Utils.cleanAll();
    }

    @Test
    public void testCreateThread() {
        Forum f = Utils.getTestForum();
        Thread t = Utils.getTestThread(f);
        User u = User.findById(t.createdBy.id);
        assertTrue(f.threads.size() > 0);
        assertTrue(t.forum.equals(f));
        assertNotNull(u);

    }

    @Test
    public void testCreateThreadWithUser() {
        Forum f = Utils.getTestForum();
        User u = Utils.getTestUser();
        Thread t = Utils.getTestThread(f, u);
        assertTrue(f.threads.size() > 0);
        assertTrue(t.forum.equals(f));
        assertTrue(String.format("createdBy should be %s, was %s", u, t.createdBy), t.createdBy.equals(u));
        assertTrue(String.format("updatedBy should be %s, was %s", u, t.updatedBy), t.updatedBy.equals(u));
    }
}
