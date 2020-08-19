package com.pg.acquirer.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date 22-May-2018 10:03:31 PM
 * @version 1.0
 */
public class AcquirerServicesTestProperties {

	private final static Properties prop = new Properties();

	static {
		loadProperties();
	}

	public static String getPropertyValue(String key) {
		return prop.getProperty(key);
	}

	public static String getPropertyValue(String key, String defultValue) {
		return prop.getProperty(key, defultValue);
	}
	
	public static String getJarLocation() {
		return prop.getProperty("jarLocation");
	}

	private static void loadProperties() {
		InputStream input = null;
		try {
			input = AcquirerServicesTestProperties.class.getClassLoader().getResourceAsStream("acquirer-services-tests.properties");

			// load a properties file
			prop.load(input);
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
