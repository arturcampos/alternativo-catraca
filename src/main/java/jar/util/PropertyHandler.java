package jar.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertyHandler {

	static Logger LOGGER = Logger.getLogger(PropertyHandler.class);
	private static PropertyHandler instance = null;
	private Properties props = null;

	private PropertyHandler() {
		InputStream input = null;

		try {
			String filePath = System.getenv("PROPERTIES_HOME") + "/catracaConfig.properties";

			input = new FileInputStream(filePath);
			LOGGER.info("Arquivo de propriedades encontradao em '" + filePath + "'");
			// load a properties file
			this.props = new Properties();
			this.props.load(input);

		} catch (IOException ex) {
			LOGGER.fatal("Fatal Erro de IO!", ex);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					LOGGER.fatal("Fatal Erro de IO ao fechar input!", e);
				}
			}
		}
	}

	public static synchronized PropertyHandler getInstance() {
		if (instance == null)
			instance = new PropertyHandler();
		return instance;
	}

	public String getValue(String propKey) {
		return this.props.getProperty(propKey);
	}
}