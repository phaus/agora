
import models.Forum;
import models.Thread;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import play.Logger;
import play.test.UnitTest;

/**
 * ForumTest 17.08.2013
 *
 * @author Philipp Haussleiter
 *
 */
public class ForumTest extends UnitTest {

    private String s;

    @Before
    public void setUp() {
        Utils.cleanAll();
    }

    @Test
    public void testCreateForum() {
        s = "F-" + System.currentTimeMillis();
        Forum f = new Forum(s);
        f.save();

        Forum f2 = Forum.find("name = ?", s).first();
        assertEquals(f.name, f2.name);

        f.delete();
        f2 = Forum.find("name = ?", s).first();
        assertNull(f2);
    }

    @Test
    public void testAddThreads() {
        s = "F-" + System.currentTimeMillis();
        Forum f = new Forum(s);
        f.save();
        Thread t = Utils.getTestThread(f);
        for(int i = 0; i < 20; i++){
            Utils.getTestPost(f, t);
        }
        Logger.info("forum:\n %s", f);
        assertTrue(String.format("should be 1, but was %d", f.getThreadCount()),f.getThreadCount() == 1);
        assertTrue(String.format("should be 20, but was %d", f.getPostCount()), f.getPostCount() == 20);
    }
    
}
