package utils.mongodb.beans;

import java.io.File;
import java.io.IOException;

import org.mongodb.morphia.annotations.Indexed;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DBConfigBean {
	
	public static class Config {
		
		@Indexed(unique=true)
		private String dbName;
		private String userName;
		private String password;
		private String serverAndPort;
		
		public Config() {}
		
		public void setDbName(String dbName) { this.dbName = dbName; }
		public void setUserName(String userName) { this.userName = userName; }
		public void setPassword(String password) { this.password = password; }
		public void setServerAndPort(String serverAndPort) {this.serverAndPort = serverAndPort; }
		
		public String getDbName() { return this.dbName; }
		public String getUserName() { return this.userName; }
		public String getPassword() { return this.password; }
		public String getServerAndPort() { return this.serverAndPort; }
		
		public String toString() {
			String str = this.dbName + "\n"
					   + this.userName + "\n"
					   + this.password + "\n"
					   + this.serverAndPort;
			return str;
		}
	}
	
	private Config qa1Config;
	private Config localConfig;
	private Config automationConfig;
	private String defaultDB;
	
	public DBConfigBean() { }
	
	public void setQa1Config(Config qa1Config) { this.qa1Config = qa1Config; }
	public void setLocalConfig(Config localConfig) { this.localConfig = localConfig; }
	public void setAutomationConfig(Config automationConfig) {this.automationConfig = automationConfig; }
	public void setDefaultDB(String defaultDB) { this.defaultDB = defaultDB; }
	
	public Config getQa1Config() { return this.qa1Config; }
	public Config getLocalConfig() { return this.localConfig; }
	public Config getAutomationConfig() { return this.automationConfig; }
	public String getDefaultDB() { return this.defaultDB; }

	// get path for "MongoDBConfig.json"
	private String getFilePath() {
		ClassLoader CLDR = this.getClass().getClassLoader();
		String filePath = CLDR.getResource("src/main/java/utils/mongodb/files/MongoDBConfig.json").getPath();
//		String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") 
//				+ "src" + System.getProperty("file.separator") 
//				+ "com" + System.getProperty("file.separator") 
//				+ "everbridge" + System.getProperty("file.separator") 
//				+ "mongodb" + System.getProperty("file.separator") 
//				+ "ebsuite" + System.getProperty("file.separator") 
//				+ "files" + System.getProperty("file.separator") 
//				+ "MongoDBConfig.json";
		return filePath;
	}
	
	public DBConfigBean read() {
		ObjectMapper mapper = new ObjectMapper();
		DBConfigBean configBean = null;
		try {
			configBean = mapper.readValue(new File(getFilePath()), DBConfigBean.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return configBean;
	}
	
	public Config getDBConfig(String configName) {
		if (configName.equals("qa1Config")) {
			return getQa1Config();
		} else if (configName.equals("localConfig")) {
			return getLocalConfig();
		} else if (configName.equals("automationConfig")) {
			return getAutomationConfig();
		} else {
			System.out.println("no config for DB: " + configName);
			return null;
		}
	}

	public Config getDefaultDBConfig() {
		return getDBConfig(this.defaultDB);
	}
	
}
