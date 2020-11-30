package cnltoolchain;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Reads the config.properties.
 */
public class CNLToolchainProperties {
    private String server =  "http://localhost:5820";
    private String projectPath = "/home/user/study/shk-swk/code/OnionArchitectureDemo/";
    private String rulesFile = projectPath + "architecture-documentation-onion.adoc";
	
    private final String propFileName = "config.properties";
    
    /**
     * Reads the config.properties file. The results can be queried afterwards with the getters.
     * @throws IOException when the file cannot be accessed
     */
	public void readPropValues() throws IOException {
		try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName)){
			Properties prop = new Properties();
			
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found");
			}
 
			server = prop.getProperty("server");
			projectPath = prop.getProperty("projectPath");
			rulesFile = prop.getProperty("rulesFile");
			
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
	}

	/**
	 * @return the database server to use
	 */
	public String getServer() {
		return server;
	}

	/**
	 * @return the path of the project to check
	 */
	public String getProjectPath() {
		return projectPath;
	}

	/**
	 * @return the name of the file containing the architecture rules and architecture-to-code mappings
	 */
	public String getRulesFile() {
		return rulesFile;
	}
	
	
}
