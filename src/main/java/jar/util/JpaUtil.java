package jar.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class JpaUtil {

	private static final String DB_DRIVER = PropertyHandler.getInstance().getValue("db.driver");
	private static final String DB_CONNECTION =  PropertyHandler.getInstance().getValue("db.connection");
	private static final String DB_USER = PropertyHandler.getInstance().getValue("db.user");
	private static final String DB_PASSWORD = PropertyHandler.getInstance().getValue("db.password");

	static Logger LOGGER = Logger.getLogger(JpaUtil.class);

	public static Connection getDBConnection() {

		Connection dbConnection = null;

		try {
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e) {
			LOGGER.error("Erro ao instanciar classe", e);
		}
		try {
			dbConnection = DriverManager.getConnection(
                             DB_CONNECTION, DB_USER,DB_PASSWORD);
			return dbConnection;
		} catch (SQLException e) {
			LOGGER.error("Erro de SQL", e);
		}

		return dbConnection;

	}
}