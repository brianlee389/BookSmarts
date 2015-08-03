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

public class Vendors extends Controller {

	public static Result index() {
		List <Vendor> vendorList = new Model.Finder(String.class, Vendor.class).all();
		return ok(vendors.render(vendorList));
	}

	public static Result get(String name) {
		Vendor vendor = Vendor.find.byId(name);
		ArrayList<HashMap<String, String>> books = vendor.getBooks();

		return ok(vendor_view.render(vendor, books));
	}

}
