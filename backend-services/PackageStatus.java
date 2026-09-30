package com.shruthi.lpasri.project1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.*;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/getPackageStatus")
public class PackageStatus {


	@GET
	@Path("/{username}")
	@Produces(MediaType.TEXT_PLAIN)
	
	public static String show(@PathParam("username") String username) throws Exception
	//public static String show() throws Exception
	{
		String un=username;
		Connection conn=getConnection();
		ResultSet rs=getDetails(conn,un);
		StringBuffer sb=new StringBuffer();
		while(rs.next())
		{
			sb.append("Status of the package is: "+rs.getString(10)+"\n");	
		}
		return sb.toString();
	}
	public static Connection getConnection() throws Exception
	{
		try
		{
		String driver="com.mysql.jdbc.Driver";
		String url="jdbc:mysql://localhost/newdb";
		String username="root";
		String password="";
		Class.forName(driver);
		Connection conn=DriverManager.getConnection(url,username,password);
		return conn;
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return null;
	}
	
	public static ResultSet getDetails(Connection conn,String un) throws Exception
	{
		return getResultSet(conn,"select * from shippingdetails where username=\""+un+"\"");
	}
	
	public static ResultSet getResultSet(Connection conn,String sql) throws Exception
	{
		Class.forName("com.mysql.jdbc.Driver");
		Statement stmt=conn.createStatement(java.sql.ResultSet.CONCUR_READ_ONLY,java.sql.ResultSet.TYPE_FORWARD_ONLY);
		return stmt.executeQuery(sql);
	}
}
