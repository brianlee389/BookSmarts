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

public class Publishers extends Controller {

	public static Result index() {
		List <Publisher> publisherList = new Model.Finder(String.class, Publisher.class).all();
		return ok(publishers.render(publisherList));
	}

	public static Result get(String name) {
		Publisher publisher = Publisher.find.byId(name);
		ArrayList<HashMap<String, String>> books = publisher.getBooks();

		return ok(publisher_view.render(publisher, books));
	}

}
