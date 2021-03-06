
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
@Table(name = "Publishers")
public class Publisher extends Model {
	@Id
	public String name;

	 public static Finder<String, Publisher> find = new Model.Finder<>(String.class, Publisher.class);

 public static ArrayList<HashMap<String, String>> getStats() {
		Connection conn = DB.getConnection();
		ArrayList<HashMap<String, String>> res = new ArrayList<HashMap<String, String>>();
		String sql = "select name, count(distinct(book_isbn)) from publishers join published on published.publisher_name = publishers.name group by 1;";
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				HashMap<String,String> map = new HashMap<String, String>(); 
				map.put("publisher", rs.getString(1));
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

		String safe_name;
		try {
			safe_name = java.net.URLDecoder.decode(this.name, "UTF-8");
		} catch (java.io.UnsupportedEncodingException e) {
			safe_name = this.name;
		}

		ArrayList<HashMap<String, String>> res = new ArrayList<HashMap<String, String>>();
		String sql = "select isbn, b.name, string_agg(a.name, ', ') from books b "
							+ "join authored ad on ad.book_isbn = b.isbn join authors a on a.id = ad.author_id "
							+ "join published pd on pd.book_isbn = b.isbn join publishers p on p.name = pd.publisher_name "
							+ "where p.name = '" + safe_name +  "' group by 1,2 order by 2 desc";
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				HashMap<String,String> map = new HashMap<String, String>(); 
				map.put("isbn", rs.getString(1));
				map.put("book_name", rs.getString(2));
				map.put("authors", rs.getString(3));
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


//select isbn, b.name, string_agg(a.name, ', ') from books b join authored ad on ad.book_isbn = b.isbn join authors a on a.id = ad.author_id join published pd on pd.book_isbn = b.isbn join publishers p on p.name = pd.publisher_name where p.name = 'Prentice Hall' group by 1,2 order by 2 desc
