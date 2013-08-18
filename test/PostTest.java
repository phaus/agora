
import models.Post;
import models.Thread;
import models.Forum;
import models.User;
import org.junit.Before;
import org.junit.Test;

import play.test.UnitTest;

/**
 * PostTest 17.08.2013
 *
 * @author Philipp Haussleiter
 *
 */
public class PostTest extends UnitTest {

    private String s;

    @Before
    public void setUp() {
        Utils.cleanAll();
    }

    @Test
    public void testCreatePost() {
        Post p = Utils.getTestPost();
        User u = User.findById(p.createdBy.id);
        Thread t = Thread.findById(p.thread.id);
        Forum f = Forum.findById(p.forum.id);
        assertNotNull(u);
        assertNotNull(t);
        assertNotNull(f);
    }
    
    @Test
    public void testPostContent(){
        Post p = Utils.getTestPost();
        s = "C-"+System.currentTimeMillis()+" ";
        p.content = Utils.getContentTimes(s, 1024);
        p.save();
        p = Post.findById(p.id);
        assertNotNull(p);
        assertTrue(p.content.length() > 0);
    }
}
