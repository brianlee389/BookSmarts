
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
@Table(name = "Authored")
public class Authored extends Model {
	public String author_id;
	@Id
	public String book_isbn;
	public String authored_at;

	 public static Finder<String, Authored> find = new Model.Finder<>(String.class, Authored.class);
}

