
import java.util.Date;
import models.DateDim;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import play.test.UnitTest;

/**
 * DateDimTest 18.08.2013
 *
 * @author Philipp Haussleiter
 *
 */
public class DateDimTest extends UnitTest {

    @Before
    public void setUp() {
        Utils.cleanAll();
    }

    @Test
    public void testCreateDateDim() {
        DateDim dd = DateDim.getDataDate(new Date());
        dd = DateDim.findById(dd.id);
        assertNotNull(dd);
    }
}
