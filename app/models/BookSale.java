
package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import play.db.ebean.Model;
import java.util.Date;

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
}
