package mpd.mysql.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionFactory {

	private static ConnectionFactory instance = new ConnectionFactory();
	public static final String URL = "jdbc:mysql://localhost/jdbcdb";
	public static final String USER = "YOUR_DATABASE_USERNAME";
	public static final String PASSWORD = "YOUR_DATABASE_PASSWORD";
	public static final String DRIVER_CLASS = "com.mysql.jdbc.Driver"; 
	
	//private constructor
		private ConnectionFactory() {
			try {
				Class.forName(DRIVER_CLASS);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		public Connection createConnection() {
			Connection connection = null;
			try {
				connection = DriverManager.getConnection(URL, USER, PASSWORD);
			} catch (SQLException e) {
				System.out.println("ERROR: Unable to Connect to Database.");
			}
			return connection;
		}	
		
		public static Connection getConnection() {
			return instance.createConnection();
		}
}
