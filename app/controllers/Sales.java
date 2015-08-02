package controllers;

import models.BookSale;
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
import java.util.List;

import static play.libs.Json.toJson;

public class Sales extends Controller {
		public static Result index() {
				List <BookSale> book_sales = new Model.Finder(int.class, BookSale.class).all();
				return ok(sales.render("drewbanin", book_sales));
		}
}
