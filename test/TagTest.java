
import java.util.Set;
import play.test.UnitTest;
import models.Tag;
import org.junit.Before;
import org.junit.Test;

/**
 * TagTest 18.08.2013
 *
 * @author Philipp Haussleiter
 *
 */
public class TagTest extends UnitTest {

    private final static String[] TAGS = {"one", "two", "three", "four", "five"};
    private final static String TAGS_STRING = "one two three four five";

    @Before
    public void setUp() {
        Utils.cleanAll();
    }

    @Test
    public void testCreateTags() {
        Set<Tag> tags = Tag.findOrCreateTagsForText(TAGS_STRING);
        assertTrue(tags.size() == TAGS.length);
        Tag t;
        for (String tag : TAGS) {
            t = Tag.find("name = ?", tag).first();
            assertNotNull(t);
        }
    }
}
