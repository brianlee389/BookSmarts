
package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import play.db.DB;
import utils.DBUtils;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import play.db.ebean.Model;

@Entity
@Table(name = "Books")
public class Book extends Model {
	@Id
	public String isbn;
	public String name;

	 public static Finder<String, Book> find = new Model.Finder<>(String.class, Book.class);

	 public ArrayList<HashMap<String, String>> getVendors() {
			Connection conn = DB.getConnection();
			ArrayList<HashMap<String, String>> res = new ArrayList<HashMap<String, String>>();
			String sql = "select vendor_name, price, quoted_at, url from bookquotes bq join quotes q on q.id = bq.quote_id where book_isbn = '" + this.isbn + "'";
			try {
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(sql);
				while (rs.next()) {
					HashMap<String,String> map = new HashMap<String, String>(); 
					map.put("vendor", rs.getString(1));
					map.put("price", Double.toString(rs.getDouble(2)));
					map.put("quoted_at", rs.getString(3));
					map.put("url", rs.getString(4));
					res.add(map);
				}
				rs.close();
				st.close();

				DBUtils.closeDBConnection(conn);
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
			return res;
	 }

	 public ArrayList<HashMap<String, String>> getAuthors() {
			Connection conn = DB.getConnection();
			ArrayList<HashMap<String, String>> res = new ArrayList<HashMap<String, String>>();
			String sql = "select author_id, name from Authored join Authors on Authored.author_id = Authors.id where book_isbn = '" + this.isbn + "'";
			try {
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(sql);
				while (rs.next()) {
					HashMap<String,String> map = new HashMap<String, String>(); 
					map.put("id", rs.getString(1));
					map.put("name", rs.getString(2));
					res.add(map);
				}
				rs.close();
				st.close();

				DBUtils.closeDBConnection(conn);
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
			return res;
	 }

}
