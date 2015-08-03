package controllers;

import play.*;
import models.*;
import play.data.DynamicForm;
import play.data.Form;
import play.db.DB;
import play.db.ebean.Model;
import play.mvc.*;
import utils.DBUtils;
import views.html.*;
/*import play.mvc.Http.RequestBody;*/
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static play.libs.Json.toJson;

public class Notifications extends Controller {

    public static Result index(String username) {
    	List<Notification> notifs = new Model.Finder(String.class, Notification.class)
				.where().like("username", username).findList();

        return ok(notifications.render(notifs));
    }

    public static Result createPage(String isbn, String bookname, String username) {
        return ok(createnotification.render(isbn, bookname, username));
    }

    public static Result create(String username) {
        Notification notification = Form.form(Notification.class).bindFromRequest().get();
        notification.save();

        List<Notification> notifs = new Model.Finder(String.class, Notification.class)
				.where().like("username", username).findList();
        return ok(notifications.render(notifs));
    }

}
