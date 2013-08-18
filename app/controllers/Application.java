package controllers;

import models.User;
import play.mvc.*;


public class Application extends Controller {
    public static User activeUser;
    public static void index() {
        render();
    }

}