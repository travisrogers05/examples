// Credit to The Open Tutorials for this example
// http://theopentutorials.com/examples/java-ee/servlet/servlet-jndi-datasource-in-tomcat/

package com.example.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class EmployeeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public EmployeeServlet() {}

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {

        Context envContext = null;
        try {
            envContext = new InitialContext();
            DataSource ds = (DataSource)envContext.lookup("java:/jboss/datasources/test_mysql");
//            Context initContext  = (Context)envContext.lookup("java:/jboss/datasources/");
//            DataSource ds = (DataSource)initContext.lookup("test_mysql");
            Connection con = ds.getConnection();

            Statement stmt = con.createStatement();
            String query = "select * from employee";
            ResultSet rs = stmt.executeQuery(query);

            PrintWriter out = response.getWriter();
            response.setContentType("text/html");
            out.print("<center><h1>Employee Details</h1></center>");
            out.print("<html><body><table border=\"1\" cellspacing=10 cellpadding=5>");
            out.print("<th>Employee ID</th>");
            out.print("<th>Employee Name</th>");
            out.print("<th>Salary</th>");
            out.print("<th>Department</th>");

            while(rs.next())
            {
                out.print("<tr>");
                out.print("<td>" + rs.getInt("emp_id") + "</td>");
                out.print("<td>" + rs.getString("emp_name") + "</td>");
                out.print("<td>" + rs.getDouble("salary") + "</td>");
                out.print("<td>" + rs.getString("dept_name") + "</td>");
                out.print("</tr>");
            }
            out.print("</table></body></html>");
        }  catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
}
