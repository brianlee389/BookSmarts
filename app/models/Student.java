package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import play.db.ebean.Model;

@Entity
@Table(name = "Student")
public class Student extends Model {

	@Id
	@SequenceGenerator(name="student_seq", sequenceName="student_sid_seq",
			allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_seq")
	public int sid;

	public String name;

	public double gpa;
}
