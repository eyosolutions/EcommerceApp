package com.conn;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnect 
{

	private static Connection conn = null;
	
	public static Connection getConn()
	{
		try {
			String url = System.getenv("DB_URL");
            String username = System.getenv("DB_USERNAME");
            String password = System.getenv("DB_PASSWORD");
			
			Class.forName("com.mysql.cj.jdbc.Driver");
		//	conn = DriverManager.getConnection("jdbc:sqlite:mydatabase.db");
		//	conn = DriverManager.getConnection("jdbc:sqlite:C:/Users/Swapnil/eclipse-workspace/Online Electronic Shopping/mydatabase.db");
		//	conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce","root","password");
			conn = DriverManager.getConnection(url, username, password);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		return conn;
	}
	
}
