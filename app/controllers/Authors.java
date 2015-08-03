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
import java.util.HashMap;
import java.util.Map;
/*import play.mvc.Http.RequestBody;*/
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import static play.libs.Json.toJson;

public class Authors extends Controller {

	public static Result index() {
		List <Author> authorList = new Model.Finder(String.class, Author.class).all();
		return ok(authors.render("", authorList));
	}

	public static Result get(String id) {
		Connection conn = DB.getConnection();

		Author author = Author.find.byId(Integer.parseInt(id));
		ArrayList<HashMap<String, String>> books = author.getBooks();

		return ok(author_view.render(author, books));
	}

}
