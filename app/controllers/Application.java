package controllers;

import models.*;
import play.*;
import play.data.DynamicForm;
import play.data.Form;
import play.db.DB;
import play.db.ebean.Model;
import play.mvc.*;
import utils.DBUtils;
import views.html.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static play.libs.Json.toJson;

public class Application extends Controller {

		public static Result index() {
			ArrayList<HashMap<String, String>> author_stats = Author.getStats();
			ArrayList<HashMap<String, String>> vendor_stats = Vendor.getStats();
			ArrayList<HashMap<String, String>> publisher_stats = Publisher.getStats();
			return ok(index.render(author_stats, vendor_stats, publisher_stats));
		}
}
