package controllers;

import models.Student;
import play.*;
import play.data.DynamicForm;
import play.data.Form;
import play.db.DB;
import play.db.ebean.Model;
import play.mvc.*;
import utils.DBUtils;
import views.html.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static play.libs.Json.toJson;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render(""));
    }
    
    public static Result dashboard() {
        return ok(dashboard.render(""));
    }

    public static Result search() {
        return ok(booksearch.render(""));
    }

    // Adding a student to your model/table
    public static Result addStudent() {
    	Student student = Form.form(Student.class).bindFromRequest().get();
    	student.save();
        return redirect(routes.Application.index());
    }
    
    // Getting all students from database
    public static Result getStudents() {
    	List <Student> students = new Model.Finder(int.class, Student.class).all();
        return ok(toJson(students));
    }
    
    
    // Getting table data using model
    public static Result studTab() {
    	List <Student> students = new Model.Finder(int.class, Student.class).all();
    	String table = "<table border=1px solid>"
    			+ "<tr>"
    			+ "<th>Name</th>"
    			+ "<th>GPA</th>"
    			+ "</tr>";
    	for(Student student: students) {
    		table += "<tr>";
    		table += "<td>" + student.name + "</td>";
    		table += "<td>" + student.gpa + "</td>";
    		table += "</tr>";    		
    	}
    	table += "</table>";
    	return ok(table);
    }
    
    
    // Getting table data using a SQL query
    public static Result termTab() {
    	
    	String sql = "select * from Terms";
    	Connection conn = DB.getConnection();
    	
    	ArrayList <String> names = new ArrayList<String>();
    	
    	try {
    	 	Statement st = conn.createStatement();
    	  	ResultSet rs = st.executeQuery(sql);
    		while (rs.next()) {
    			names.add(rs.getString(1));
            }
    		rs.close();
    		st.close();
    		
	    	DBUtils.closeDBConnection(conn);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
    	
    	String table = "<table border=1px solid>"
    			+ "<tr>"
    			+ "<th>Terms</th>"
    			+ "</tr>";

    	for (String name: names) {
	    	table += "<tr>";
			table += "<td>" + name + "</td>";
			table += "</tr>";    	
    	}
    	
    	table += "</table>";
    	
    	return ok(table);
    }
    
    // Adding a list of terms to your model
    public static Result addTerms() {
    	
    	// Getting a specific form element
    	DynamicForm requestData = Form.form().bindFromRequest();
    	String value = requestData.get("names");

    	// This is shown on command line
    	System.out.println("Value from form - " + value);

    	// form data validation
    	if (value != null) {
	    	String [] terms = value.split(",");
	    	
	    	Connection conn = DB.getConnection();
	    	String sql = "insert into Terms values ( ? )";
	    	
	    	try {
				DBUtils.executeUpdate(conn, sql, terms);
				DBUtils.closeDBConnection(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    	return redirect(routes.Application.index());
    }
    
    // Querying database and use a variable element in the query
/*    public static Result queryGPA(String minGPA) {

    	Double gpa = Double.parseDouble(minGPA);
    	String sql = "select Student.sid, count(*) "
    			+ "from Student, Enrollment "
    			+ "where Student.sid = Enrollment.sid "
    			+ "and Student.gpa > " + gpa + " "
    			+ "group by Student.sid";
    	
    	Connection conn = DB.getConnection();
    	
    	String table = "<table border=1px solid>"
    			+ "<tr>"
    			+ "<th>Student ID</th>"
    			+ "<th>Count</th>"
    			+ "</tr>";
    	
    	try {
    	 	Statement st = conn.createStatement();
    	  	ResultSet rs = st.executeQuery(sql);
    		while (rs.next()) {
                table += "<tr>";
        		table += "<td>" + rs.getString(1) + "</td>";
        		table += "<td>" + rs.getDouble(2) + "</td>";
        		table += "</tr>";    	
            }
    		rs.close();
    		st.close();
    		
	    	DBUtils.closeDBConnection(conn);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
    	
    	// create an html page on the fly
    	response().setContentType("text/html");
    	return ok(table);
    	
    }*/
    

}
