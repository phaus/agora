/**
 * Forums 18.08.2013
 *
 * @author Philipp Haussleiter
 *
 */
package controllers;

import java.util.List;
import models.Forum;
import models.Section;
import models.Thread;

public class Forums extends Application {

    private final static int THREADS_PER_PAGE = 100;

    public static void index(Long sectionId) {
        List<Forum> forums;
        if (sectionId == null) {
            forums = Forum.all().fetch();
            render(forums);
        } else {
            Section section = Section.findById(sectionId);
            forums = Forum.find("section = ?", section).fetch();
            render(section, forums);
        }

    }

    public static void show(Long sectionId, Long id, Integer page) {
        if (page == null) {
            page = 1;
        }
        Section section = Section.findById(sectionId);
        Forum forum = Forum.find("section = ? and id = ?", section, id).first();
        List<Thread> threads = forum.getThreads(page.intValue() - 1, THREADS_PER_PAGE);
        render(section, forum, threads, page);
    }

    public static void update() {
    }

    public static void create() {
    }

    public static void delete() {
    }
}
