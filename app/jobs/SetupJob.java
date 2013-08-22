/**
 * SetupJob 22.08.2013
 *
 * @author Philipp Haussleiter
 *
 */
package jobs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import models.DateDim;
import models.Forum;
import models.User;
import models.Thread;
import models.Post;
import models.Section;
import play.jobs.Job;
import play.jobs.OnApplicationStart;

@OnApplicationStart
public class SetupJob extends Job {

    private final static String LOCAL_USER = System.getProperty("user.name");

    @Override
    public void doJob() {
        User admin = User.find("username = ?", "admin").first();
        if (admin == null) {
            admin = new User("admin");
            admin.password = "admin";
            admin.save();
        }
        if (Section.count() == 0) {
            Section section = new Section("Main");
            section.save();
            if (Forum.count() == 0) {
                createDemoForum(section, admin);
                List<User> users = new ArrayList<User>();
                users.add(admin);
                users.add(addUser(LOCAL_USER));
                users.add(addUser("Agora"));
                createUsersForum(section, users);
            }
            section.save();
            createDemoSection(admin);
            createAdminSection(admin);
        }
    }

    private User addUser(String name) {
        User user = new User(name);
        user.email = name + "@agora";
        return user.save();
    }

    private void createDemoSection(User admin) {
        Section section = new Section("Operation Systems");
        section.save();
        String[] oss = {"Linux", "Mac OS", "Windows", "iOS", "Android", "BeOS/Haiku", "Windows Phone 8"};
        for (String os : oss) {
            Forum forum = new Forum(os + " Forum");
            forum.section = section;
            forum.save();
            Thread thread = new Thread(os + " is THE BEST Os ever!", forum, admin);
            thread.sticky = true;
            thread.save();
            Post post = new Post(forum, thread, admin);
            post.content = os + " rulez!<br />No Text.";
            post.save();
        }
    }

    private void createAdminSection(User admin) {
        Section section = new Section("Administration");
        section.save();
        Forum forum = new Forum("Board");
        forum.section = section;
        forum.save();
        Thread thread = new Thread("Important Infos!", forum, admin);
        thread.sticky = true;
        thread.save();
        Post post = new Post(forum, thread, admin);
        post.content = LOCAL_USER + " just installed this new Forum " + new DateDim(new Date()).toString();
        post.save();
    }

    private void createUsersForum(Section section, List<User> users) {
        Forum forum = new Forum("Users");
        forum.section = section;
        forum.save();
        for (User user : users) {
            Thread thread = new Thread(user.username, forum, user);
            thread.save();
            Post p = new Post(forum, thread, user);
            p.content = "Hello, i am " + user.username + "!";
            p.save();
        }
    }

    private void createDemoForum(Section section, User admin) {
        Forum forum = new Forum("Global");
        forum.section = section;
        forum.save();
        if (Thread.count() == 0) {
            Thread thread = new Thread("Welcome to Agora!", forum, admin);
            thread.save();
            createDemoPosts(forum, thread, admin);
        }
    }

    private void createDemoPosts(Forum forum, Thread thread, User admin) {
        Post p1 = new Post(forum, thread, admin);
        p1.content = "This is your fist Post!<br />You can keep it or delete it!";
        p1.save();
        Post p2 = new Post(forum, thread, admin);
        p2.content = "<pre>"
                + "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. \n"
                + "\n"
                + "Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. "
                + "</pre>";
        p2.save();

        Post p3 = new Post(forum, thread, admin);
        p3.content = "<table class=\"table\">\n"
                + "        <tbody>\n"
                + "          <tr>\n"
                + "            <td><h1>h1. Bootstrap heading</h1></td>\n"
                + "            <td>Semibold 36px</td>\n"
                + "          </tr>\n"
                + "          <tr>\n"
                + "            <td><h2>h2. Bootstrap heading</h2></td>\n"
                + "            <td>Semibold 30px</td>\n"
                + "          </tr>\n"
                + "          <tr>\n"
                + "            <td><h3>h3. Bootstrap heading</h3></td>\n"
                + "            <td>Semibold 24px</td>\n"
                + "          </tr>\n"
                + "          <tr>\n"
                + "            <td><h4>h4. Bootstrap heading</h4></td>\n"
                + "            <td>Semibold 18px</td>\n"
                + "          </tr>\n"
                + "          <tr>\n"
                + "            <td><h5>h5. Bootstrap heading</h5></td>\n"
                + "            <td>Semibold 14px</td>\n"
                + "          </tr>\n"
                + "          <tr>\n"
                + "            <td><h6>h6. Bootstrap heading</h6></td>\n"
                + "            <td>Semibold 12px</td>\n"
                + "          </tr>\n"
                + "        </tbody>\n"
                + "      </table>";
        p3.save();
    }
}
