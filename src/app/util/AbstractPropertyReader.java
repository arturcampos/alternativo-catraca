package app.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

import org.apache.log4j.Logger;

public abstract class AbstractPropertyReader {

	static Logger logger = Logger.getLogger(AbstractPropertyReader.class);

	public static Properties propertyReader() {

		Properties prop = new Properties();
		InputStream input = null;

		try {
			String filePath = "C:/CatracaProperties/catracaConfig.properties";

			input = new FileInputStream(filePath);
			logger.info("Arquivo de propriedades encontradao em '" + filePath + "'");
			// load a properties file
			prop.load(input);

		} catch (IOException ex) {
			logger.fatal(getStackTrace(ex));
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					logger.fatal(getStackTrace(e));
				}
			}
		}
		return prop;
	}

	private static	String getStackTrace(final Throwable e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
	}
}