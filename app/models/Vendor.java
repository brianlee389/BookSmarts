
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
@Table(name = "Vendors")
public class Vendor extends Model {

	@Id
	public String name;
	public String website;

 public static Finder<String, Vendor> find = new Model.Finder<>(String.class, Vendor.class);

 public static ArrayList<HashMap<String, String>> getStats() {
		Connection conn = DB.getConnection();
		ArrayList<HashMap<String, String>> res = new ArrayList<HashMap<String, String>>();
		String sql = "select vendor_name, count(distinct(book_isbn)) from bookquotes group by 1;";
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				HashMap<String,String> map = new HashMap<String, String>(); 
				map.put("vendor", rs.getString(1));
				map.put("num_books", rs.getString(2));
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

 public ArrayList<HashMap<String, String>> getBooks() {
		Connection conn = DB.getConnection();
		ArrayList<HashMap<String, String>> res = new ArrayList<HashMap<String, String>>();
		String sql = "select price, b.isbn, b.name, string_agg(a.name, ', ') from bookquotes bq "
			         + "join quotes q on bq.quote_id = q.id join books b on b.isbn = book_isbn "
							 + "join authored on authored.book_isbn = b.isbn join authors a on a.id = authored.author_id "
							 + "where vendor_name = '" + this.name + "' group by 1,2,3 order by b.name desc";
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				HashMap<String,String> map = new HashMap<String, String>(); 
				map.put("price", String.valueOf(rs.getFloat(1)));
				map.put("isbn", rs.getString(2));
				map.put("book_name", rs.getString(3));
				map.put("authors", rs.getString(4));
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
