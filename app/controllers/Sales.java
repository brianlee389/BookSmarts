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

import java.util.Date;

import static play.libs.Json.toJson;

public class Sales extends Controller {

		public static Result index() {
			Connection conn = DB.getConnection();

			
			String logged_in_user = session("booksmart_username");

			HashMap<String, Double> stats = BookSale.saleStats(logged_in_user);

			ArrayList<HashMap<String, String>> res = new ArrayList<HashMap<String, String>>();
			String sql = "select bq.vendor_name, b.name, b.isbn, bs.price, bs.sold_at "
				         + "from booksale bs join books b on bs.book_isbn = b.isbn "
								 + "join bookquotes bq on bq.quote_id = bs.quote_id "
								 + "where bs.username = '" + logged_in_user + "' order by bs.sold_at desc";
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
			return ok(sales.render(logged_in_user, res, stats));
		}

		public static Result create(String username, String isbn, String vendor) {

			Connection conn = DB.getConnection();

			double price = 0.0;
			int quote_id = 0;

			String sql = "select q.id, price from quotes q join bookquotes bq on bq.quote_id = q.id "
				         + "where vendor_name = '" + vendor + "' and book_isbn = '" + isbn + "' limit 1";
			try {
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(sql);
				rs.next();
				quote_id = rs.getInt(1);
				price = rs.getDouble(2);
				rs.close();
				st.close();

				DBUtils.closeDBConnection(conn);
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}

			BookSale book_sale = new BookSale();
			book_sale.username = username;
			book_sale.book_isbn = isbn;
			book_sale.price = price;
			book_sale.quote_id = quote_id;
			book_sale.sold_at = new Date();
			book_sale.save();

			return redirect(routes.Sales.index());
		}
}
