package controllers;

import play.*;
import models.User;
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

public class LoginController extends Controller {

    public static Result index(String message) {
        return ok(login.render(message));
    }

    public static Result signup() {
        return ok(signup.render());
    }

    public static Result createUser() {
        User user = Form.form(User.class).bindFromRequest().get();
        user.save();
				session("booksmart_username", user.username);
        return redirect(routes.Application.index());
    }
}
