
package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import play.db.ebean.Model;

@Entity
@Table(name = "UserBookNotifications")
public class Notification extends Model {

	public String username;
	public String book_isbn;
	public String date_notified;
	public double price;
	public String date_set;
}
