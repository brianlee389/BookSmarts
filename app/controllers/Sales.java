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
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import static play.libs.Json.toJson;

public class Sales extends Controller {
		//public static Result index() {
		//		List <BookSale> book_sales = new Model.Finder(int.class, BookSale.class).all();
		//		return ok(sales.render("drewbanin", book_sales));
		//}

		public static Result index() {
			Connection conn = DB.getConnection();

			ArrayList<HashMap<String, String>> res = new ArrayList<HashMap<String, String>>();
			String logged_in_user = "drewbanin";
			//book name, book isbn, vendor name, vendor link, price, sold at
			String sql = "select bq.vendor_name, b.name, b.isbn, bs.price, bs.sold_at "
				         + "from booksale bs join books b on bs.book_isbn = b.isbn "
								 + "join bookquotes bq on bq.quote_id = bs.quote_id "
								 + "where bs.username = '" + logged_in_user + "'";
			try {
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(sql);
				while (rs.next()) {
					HashMap<String,String> map = new HashMap<String, String>(); 
					map.put("vendor", rs.getString(1));
					map.put("book", rs.getString(2));
					map.put("isbn", rs.getString(3));
					map.put("price", Double.toString(rs.getDouble(4)));
					map.put("sold_at", rs.getString(5));
					res.add(map);
				}
				rs.close();
				st.close();

				DBUtils.closeDBConnection(conn);
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
			return ok(sales.render(logged_in_user, res));
		}
}
