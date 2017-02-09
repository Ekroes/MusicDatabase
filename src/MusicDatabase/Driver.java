package MusicDatabase;

import java.sql.*;
import java.util.*;

public class Driver {

	ResourceBundle file = null;
	 String url = null;
	 String schema = null;
	 String user = null;
	 String password = null;
	 String driver = null;
	
	public Driver(){
		
		file = PropertyResourceBundle.getBundle("login");
		
		url = file.getString("url");
		schema = file.getString("schema");
		user = file.getString("user");
		password = file.getString("password");
		driver = file.getString("driver");
		
		try {
			Class.forName(driver);
		}

		catch (ClassNotFoundException e) {

			throw new RuntimeException(e);
			

		}
	}

	public  Connection openConnection() throws SQLException {

		
		return DriverManager.getConnection(url + schema, user, password);

	}

	public static void closeConnection(Connection conn) {

		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException se) {

			}
		}
	}

	static void closeStatement(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException se) {

			}
		}

	}

}
