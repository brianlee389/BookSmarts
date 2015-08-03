
package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import play.db.ebean.Model;
import java.util.Date;
import java.util.HashMap;

import play.db.DB;
import utils.DBUtils;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;


@Entity
@Table(name = "BookSale")
public class BookSale extends Model {
	@Id
	@SequenceGenerator(name="booksale_seq", sequenceName="booksale_id_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booksale_seq")

	public int id;
	public String username;
	public String book_isbn;
	public Date sold_at;
	public double price;
	public int quote_id;


	 public static HashMap<String, Double> saleStats(String username) {
			Connection conn = DB.getConnection();
			String sql = "select username, sum(price), avg(price), min(price), max(price) from booksale where username = '" + username + "' group by 1;";
			HashMap<String,Double> map = new HashMap<String, Double>(); 
			try {
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(sql);
				while (rs.next()) {
					map.put("sum", rs.getDouble(2));
					map.put("avg", rs.getDouble(3));
					map.put("min", rs.getDouble(4));
					map.put("max", rs.getDouble(5));
				}
				rs.close();
				st.close();

				DBUtils.closeDBConnection(conn);
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
			return map;
	 }
}
