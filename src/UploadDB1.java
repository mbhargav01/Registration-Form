import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

@MultipartConfig
public class UploadDB1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	 {
	PrintWriter  out=res.getWriter();
	String path=getServletContext().getRealPath("image");
	MultipartRequest mpr=new MultipartRequest(req,path,500*1024*1024);

	String fname=mpr.getParameter("fname");
	String lname=mpr.getParameter("lname");
	long mobile=Long.parseLong(mpr.getParameter("mobile"));
	String email=mpr.getParameter("email");
	String gender=mpr.getParameter("gender");
	String address=mpr.getParameter("address");

	String path1=mpr.getOriginalFileName("file");
	String path2=path+"/"+path1;
	FileInputStream  fin=new FileInputStream(path2);
	try
	{
	Class.forName("oracle.jdbc.driver.OracleDriver");
	Connection c=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","manish123","manish123");
	/*Statement s=c.createStatement();
	s.executeQuery("CREATE TABLE register(first_name varchar2(20),last_name varchar2(20),mobile number(10),mail varchar2(50),gender varchar2(10),address varchar2(50),img  BLOB)");
	s.close();*/
	PreparedStatement ps=c.prepareStatement("insert into  register values(?,?,?,?,?,?,?)");
	ps.setString(1, fname);
			ps.setString(2, lname);
			ps.setLong(3, mobile);
			ps.setString(4, email);
			ps.setString(5, gender);
			ps.setString(6, address);
			ps.setBinaryStream(7,fin,fin.available());
	ps.executeUpdate();
	c.close();
	}
	catch(Exception e)
	{
		System.out.println(e);
	 }

	out.println("<html><body>");
			out.println("<h1>Hi "+fname+lname+"!<h1><h2> your registration successfully");

			out.println("<br>");
			out.println("If you want to show your registered details <a  href='show?mobile="+mobile+"'>click here</a>");
			out.println("<h2></body></html>");
	}

	}
