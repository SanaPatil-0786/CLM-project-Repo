package resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

	private static Properties property = new Properties();
	
	static {
		try {
			FileInputStream input = new FileInputStream("src/test/java/resources/global.properties");
			property.load(input);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
public static String get(String key)
{
	return property.getProperty(key);
}
	
}
