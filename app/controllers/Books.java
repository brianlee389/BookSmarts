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

public class Books extends Controller {

    public static Result index() {
    	List <Book> bookList = new Model.Finder(String.class, Book.class).all();

        return ok(books.render("", bookList));
    }

		/*
    public static Result search() {
    	String sql = "select B.name, B.isbn, string_agg(A.name, ', '), P.name"
			+ "from Books B, Authors A, Authored Ad, Publishers P, Published Pd"
			+ "where B.isbn = Ad.book_isbn"
			+ "and A.id = Ad.author_id"
			+ "and B.isbn = Pd.book_isbn"
			+ "and Pd.publisher_name = P.name"
			+ "group by B.name, B.isbn, P.name";
    	Connection conn = DB.getConnection();
    	List<String> booklist = new ArrayList<String>();
    	String json = "";
    	try {
    	 	Statement st = conn.createStatement();
    	  	ResultSet rs = st.executeQuery(sql);
    	  	ObjectMapper mapper = new ObjectMapper();
			while (rs.next()) {
				HashMap<String, String> b = new HashMap<String, String>();
				b.put("book_name", rs.getString(1));
				b.put("isbn", rs.getString(2));
				b.put("authors", rs.getString(3));
				b.put("publisher", rs.getString(4));
            }
            json = mapper.writeValueAsString(map);
			booklist.add(json);
    		rs.close();
    		st.close();
    		
	    	DBUtils.closeDBConnection(conn);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		return ok(toJson(bookList));
    }
		*/

	public static Result get(String isbn) {
		Connection conn = DB.getConnection();

		Book book = Book.find.byId(isbn);
		ArrayList<HashMap<String, String>> vendors = book.getVendors();
		Published published_info = Published.find.byId(isbn);
		return ok(book_view.render(book, vendors, published_info));
	}

}
