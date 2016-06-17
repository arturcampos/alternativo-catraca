package jar.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class JpaUtil {

	private static Properties prop;
	static{
		prop = AbstractPropertyReader.propertyReader();
	}
	
	private static final String DB_DRIVER = prop.getProperty("db.driver");
	private static final String DB_CONNECTION =  prop.getProperty("db.connection");
	private static final String DB_USER = prop.getProperty("db.user");
	private static final String DB_PASSWORD = prop.getProperty("db.password");
	
	static Logger logger = Logger.getLogger(JpaUtil.class);
	
	public static Connection getDBConnection() {

		Connection dbConnection = null;

		try {
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		try {
			dbConnection = DriverManager.getConnection(
                             DB_CONNECTION, DB_USER,DB_PASSWORD);
			return dbConnection;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return dbConnection;

	}
}