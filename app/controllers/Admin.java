/**
 * Admin 18.08.2013
 *
 * @author Philipp Haussleiter
 *
 */
package controllers;

import java.util.Map;
import java.util.TreeMap;
import models.DateDim;
import models.Entry;
import models.Post;
import models.Thread;
import models.Forum;
import models.Section;
import models.Tag;
import models.User;

public class Admin extends Application {

    public static void index() {
        if (activeUser.isAdmin()) {
            Map<String, Long> stats = new TreeMap<String, Long>();
            stats.put("Dates", DateDim.count());
            stats.put("Sections", Section.count());
            stats.put("Forums", Forum.count());
            stats.put("Entries", Entry.count());
            stats.put("Threads", Thread.count());
            stats.put("Posts", Post.count());
            stats.put("Users", User.count());
            stats.put("Tags", Tag.count());
            render(stats);
        } else {
            forbidden();
        }
    }
}
