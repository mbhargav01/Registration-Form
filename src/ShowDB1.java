import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class ShowDB1 extends GenericServlet {
	private static final long serialVersionUID = 1L;
       
	InputStream f=null;
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException
	 {
	res.setContentType("text/html");
	long mob=Long.parseLong(req.getParameter("mobile"));
	PrintWriter  out=res.getWriter();
	try
	{
	Class.forName("oracle.jdbc.driver.OracleDriver");
	Connection c=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","manish123","manish123");
	PreparedStatement ps=c.prepareStatement("select * from register where mobile=? ");
	ps.setLong(1,mob);
	ResultSet rs=ps.executeQuery();
	String path=getServletContext().getRealPath("/");
	rs.next();
	 f=rs.getBinaryStream("img");
	FileOutputStream f1=new FileOutputStream(path+"\\"+"abc12"+".jpg");
	int i=0;
	while((i=f.read())!=-1)
	{
	f1.write(i);
	  }
	String fname=rs.getString(1);
	String lname=rs.getString(2);
	String mail=rs.getString(4);
	String gender=rs.getString(5);
	String address=rs.getString(6);
	out.println("<html><style>"
			+ "table, td, th {"
			+ "  border: 1px solid;"
			+ "}table {"
			+ "  width: 60%;"
			+ "  border-collapse: collapse;"
			+ "}img{margin-left:20%}"
			+ "</style><body>");
	out.println("<img src='abc12.jpg'  width='160'  height='170'>");
	out.println("<table>"
			+ "<tr><th>First name</th>"
			+ "<td>"+fname+"</td></tr>"
			+ "<tr><th>Surname name</th>"
			+ "<td>"+lname+"</td></tr>"
			+ "<tr><th>Mobile No.</th>"
			+ "<td>"+mob+"</td></tr>"
			+ "<tr><th>Mail Id</th>"
			+ "<td>"+mail+"</td></tr>"
			+ "<tr><th>Gender</th>"
			+ "<td>"+gender+"</td></tr>"
			+ "<tr><th>Address</th>"
			+ "<td>"+address+"</td></tr>"
			+ "</table></body></html>");
	
	}catch(Exception e){
	out.println(e);
	}

	}
}
