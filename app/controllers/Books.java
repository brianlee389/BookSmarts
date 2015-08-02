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

public class Books extends Controller {

    public static Result index() {
    	List <Book> bookList = new Model.Finder(String.class, Book.class).all();
        String names = "1";
        for(Book b : bookList ) {
        	names = names + b.name;
        }
        return ok(books.render("", bookList));
    }

}
