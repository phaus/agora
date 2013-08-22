/**
 * Users 23.08.2013
 *
 * @author Philipp Haussleiter
 *
 */
package controllers;

import java.util.List;
import models.User;

public class Users extends Application {

    private final static int USERS_PER_PAGE = 100;

    public static void index(Integer page) {
        if (page == null) {
            page = 1;
        }
        List<User> users = User.all().fetch(page.intValue() - 1, USERS_PER_PAGE);
        render(users, page);
    }

    public static void show(Long id) {
        User user = User.findById(id);
        if (activeUser.canEdit(user)) {
            render(user);
        } else {
            forbidden();
        }
    }
}
