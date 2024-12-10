package pack1;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class EmployeeServices
{
	public static Connection getCon()
	{
		Connection con=null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3307/servlets","root","root");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}
	public static boolean readEmp(ServletRequest req,ServletResponse res)
	{
		int id=Integer.parseInt(req.getParameter("id"));
		String name=req.getParameter("name");
		String branch=req.getParameter("branch");
		Employee e=new Employee(id,name,branch);
		return insertEmp(e);
	}
	public static boolean insertEmp(Employee e)
	{
		Connection con=getCon();
		try
		{
			String qry="insert into student values(?,?,?)";
			PreparedStatement pst=con.prepareStatement(qry);
			pst.setInt(1, e.getId());
			pst.setString(2, e.getName());
			pst.setString(3, e.getBranch());
			pst.executeUpdate();
			
			
		}
		catch(Exception e1)
		{
			e1.printStackTrace();
			return false;
		}
		return true;
	}
	public static void retriveStudent(ServletRequest req,ServletResponse res) throws IOException, SQLException
	{
		PrintWriter out=res.getWriter();
		String qry="select * from student";
		PreparedStatement pst=getCon().prepareStatement(qry);
		ResultSet rst=pst.executeQuery();
		
		while(rst.next())
		{
			int id=rst.getInt(1);
			String name=rst.getString(2);
			String branch=rst.getString(3);
			out.println(id+" "+name+" "+branch);
		}
	   
	}
	public static boolean updateEmp(ServletRequest req,ServletResponse res)
	{
		try 
		{
			int id=Integer.parseInt(req.getParameter("id"));
			String name=req.getParameter("name");
			String branch=req.getParameter("branch");
			String qry="update student set name=?,branch=? where id=?";
			PreparedStatement pst=getCon().prepareStatement(qry);
			pst.setString(1,name);
			pst.setString(2, branch);
			pst.setInt(3, id);
			pst.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public static boolean delete(ServletRequest req,ServletResponse res)
	{
		int id=Integer.parseInt(req.getParameter("id"));
		String qry="delete from student where id=?";
		try {
			PreparedStatement pst=getCon().prepareStatement(qry);
			pst.setInt(1, id);
			int n=pst.executeUpdate();
			if(n<=0)
			{
				return false;
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		return true;
	}
}
