/**
 * Threads 18.08.2013
 *
 * @author Philipp Haussleiter
 *
 */
package controllers;

import java.util.List;
import models.Forum;
import models.Post;
import models.Section;
import models.Thread;

public class Threads extends Application {

    private final static int POSTS_PER_PAGE = 100;

    public static void index(Long sectionId, Long forumId, Long id, Integer page) {
        if (page == null) {
            page = 1;
        }
        Thread thread = Thread.findById(id);
        if (thread == null) {
            notFound();
        }
        Forum forum = thread.forum;
        Section section = forum.section;
        List<Post> posts = thread.getPosts(page.intValue() - 1, POSTS_PER_PAGE);
        render(section, forum, thread, page, posts);
    }
}
