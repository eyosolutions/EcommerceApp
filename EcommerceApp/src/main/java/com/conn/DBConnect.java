package com.conn;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnect 
{

	private static Connection conn = null;
	
	public static Connection getConn()
	{
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
		//	conn = DriverManager.getConnection("jdbc:sqlite:mydatabase.db");
		//	conn = DriverManager.getConnection("jdbc:sqlite:C:/Users/Swapnil/eclipse-workspace/Online Electronic Shopping/mydatabase.db");
			conn = DriverManager.getConnection("jdbc:mysql://172.17.0.2:3306/ecommerce","root","password");
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		return conn;
	}
	
}
