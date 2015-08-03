
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
@Table(name = "Authors")
public class Author extends Model {

	@Id
	public int id;

	public String name;
	public String birthday;

 public static Finder<Integer, Author> find = new Model.Finder<>(Integer.class, Author.class);

 public static ArrayList<HashMap<String, String>> getStats() {
		Connection conn = DB.getConnection();
		ArrayList<HashMap<String, String>> res = new ArrayList<HashMap<String, String>>();
		String sql = "select a.name, count(distinct(ad.book_isbn)) from authors a join authored ad on ad.author_id = a.id group by 1";
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				HashMap<String,String> map = new HashMap<String, String>(); 
				map.put("author", rs.getString(1));
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
		String sql = "select book_isbn, name, authored_at from authored join books on book_isbn = isbn where author_id = '" + this.id + "'";
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				HashMap<String,String> map = new HashMap<String, String>(); 
				map.put("isbn", rs.getString(1));
				map.put("name", rs.getString(2));
				map.put("authored_at", rs.getString(3));
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
