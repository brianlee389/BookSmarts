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
import java.util.*;

import static play.libs.Json.toJson;

public class Notifications extends Controller {

    public static Result index() {
    	/*List<Notification> notifs = new Model.Finder(String.class, Notification.class)
				.where().like("username", username).findList();

        return ok(notifications.render(notifs));*/
			String username = session("booksmart_username");
    	String sql = "select B.name, B.isbn, N.username, N.price, N.date_notified, N.date_set "
			+ "from Books B, userbooknotifications N "
			+ "where B.isbn = N.book_isbn "
			+ "and N.username = '" + username + "'";

    	Connection conn = DB.getConnection();
    	List<HashMap<String, String>> notifs = new ArrayList<HashMap<String, String>>();
    	try {
    	 	Statement st = conn.createStatement();
    	  	ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				HashMap<String, String> b = new HashMap<String, String>();
				b.put("name", rs.getString(1));
				b.put("isbn", rs.getString(2));
				b.put("username", rs.getString(3));
				b.put("price", rs.getString(4));
				b.put("date_notified", rs.getString(5));
				b.put("date_set", rs.getString(6));
				notifs.add(b);
            }
            rs.close();
    		st.close();

	    	DBUtils.closeDBConnection(conn);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		return ok(notifications.render(notifs));
    }

    public static Result createPage(String isbn, String bookname) {
				String username = session("booksmart_username");
        return ok(createnotification.render(isbn, bookname, username));
    }

    public static Result create() {
        Notification notification = Form.form(Notification.class).bindFromRequest().get();
        notification.save();

        return redirect(routes.Notifications.index());
    }

}
