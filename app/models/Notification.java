package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;

import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import play.db.ebean.Model;

@Entity
@Table(name = "UserBookNotifications")
public class Notification extends Model {

	public String username;
	public String book_isbn;

	@Column(name = "date_notified")
  	public Date date_notified;

	@Column(name = "date_set")
  	public Date date_set;

	public double price;

	@Override
	public void save() {
		dateSet();
		super.save();
	}

	@Override
	public void update() {
		dateNotified();
		super.update();
	}

	@PrePersist
	void dateNotified() {
		this.date_notified = new Date();
	}

	@PreUpdate
	void dateSet() {
		this.date_set = new Date();
	}
}
