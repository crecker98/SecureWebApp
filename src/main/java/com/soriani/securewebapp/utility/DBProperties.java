package com.soriani.securewebapp.utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;

public final class DBProperties implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7270275437706346917L;
	
	private String source; 
	private String schema; 
	private String connectionParameters;
	private String user;
	private String password;
	
	//costanti per il file di configurazione per il db
	private static final String CONFIG_PATH = "config.properties";
	private static final String SOURCE = "source";
	private static final String SCHEMA = "schema"; 
	private static final String CONNECTION_PARAMETERS = "connectionParameters";
	private static final String USER = "User";
	private static final String PASSWORD = "Password";
	
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getSchema() {
		return schema;
	}
	public void setSchema(String schema) {
		this.schema = schema;
	}
	public String getConnectionParameters() {
		return connectionParameters;
	}
	public void setConnectionParameters(String connectionParameters) {
		this.connectionParameters = connectionParameters;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * metodo che restituisce la configurazione da file per collegarsi al db
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void getPropertiesFromFile(String table, String operazione) throws FileNotFoundException, IOException {
		
		//leggo i parametri dal file di configurazione
		Properties properties = new Properties();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();           
		String path = classLoader.getResource("/").getPath() + CONFIG_PATH;
		properties.load(new FileInputStream(path));
		
		this.setSource(properties.getProperty(SOURCE));
		this.setSchema(properties.getProperty(SCHEMA));
		this.setConnectionParameters(properties.getProperty(CONNECTION_PARAMETERS));
		
		//leggo i parametri dal file in base alla tabella e all'operazione
		properties = new Properties();
		classLoader = Thread.currentThread().getContextClassLoader();           
		path = classLoader.getResource("/").getPath() + table + ".properties";
		properties.load(new FileInputStream(path));
		
		this.setUser(properties.getProperty(operazione  + USER));
		this.setPassword(properties.getProperty(operazione + PASSWORD));
	
	}
	
	
}
