package tutorial;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;


public class DBConnection {
	public static Connection create(String url, String user, String pass){
		Connection conn = null;
		try{
			 try{
				 Class.forName("com.mysql.jdbc.Driver");
			 }catch (ClassNotFoundException e)
			 {
				 System.out.println("Class.forName Error");
				 e.printStackTrace();
			 }
			 conn = DriverManager.getConnection(url, user, pass);
			 
			}
			 catch (Exception e)
			 {
				 System.out.println("Connection Error");
				 e.printStackTrace();
			 }
		return conn;
	}
	
	public static void closeConnect(Connection conn)
	{
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void closePreparedStatement(PreparedStatement ps)
	{
		try {
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void closeStatement(Statement st)
	{
		try {
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
