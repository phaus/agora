package controllers;

import java.util.Date;
import java.util.List;
import models.Section;
import models.User;
import play.Logger;
import play.mvc.Before;
import play.mvc.Controller;

public class Application extends Controller {

    public static User activeUser;

    @Before
    public static void updateUser() {
        activeUser = User.find("username = ?", "admin").first();
        if (activeUser != null) {
            activeUser.lastVisit = new Date();
            activeUser.save();
        }
    }

    public static void index() {
        List<Section> sections = Section.all().fetch();
        render(sections);
    }
}