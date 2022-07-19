package com.soriani.securewebapp.dao.condivisi;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.soriani.securewebapp.utility.ApplicationException;
import com.soriani.securewebapp.utility.DBProperties;

public class Dao {
	
	//COSTANTI PER PRELEVARE LA CONFIGURAZIONE DA FILE
	protected static final String INSERT = "insert";
	protected static final String SELECT = "select";
	protected static final String UPDATE = "update";
	protected static final String DELETE = "delete";

	/**
	 * metodo che consente di connetersi al db
	 * @return
	 */
	public static final Connection getConnection(String table, String operazione) throws ApplicationException {
		
		DBProperties dbProperties = new DBProperties();
		try {
			dbProperties.getPropertiesFromFile(table, operazione);
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
			throw new ApplicationException("Errore di connessione!");
		} catch (IOException e2) {
			e2.printStackTrace();
			throw new ApplicationException("Errore di connessione!");
		}
		
		Connection connessione = null;
		
		try { 
			Class.forName("com.mysql.cj.jdbc.Driver");
			String jdbc = (new StringBuilder("jdbc:mysql://")).append(dbProperties.getSource()).append("/").append(dbProperties.getSchema()).append(dbProperties.getConnectionParameters()).toString();
			connessione = DriverManager.getConnection(jdbc, dbProperties.getUser(), dbProperties.getPassword());
		} catch (SQLException e) {
			e.printStackTrace();
			if (e.getErrorCode() == 1045) {
				System.out.println("Username o password del DB errati! \n Controllare il file di configurazione e riprovare." + e);
				throw new ApplicationException("Errore di connessione!");
			} else {
				System.out.println("Errore nella connessione al database!.");
				throw new ApplicationException("Errore di connessione!");
			}
		} catch (Exception e1) {
			System.out.println("Errore nella connessione al database!." + e1.getMessage()); 
			throw new ApplicationException("Errore di connessione!");
		}
		
		return connessione;
	}
	
	/**
	 * metodo che consente di chiudere le connessioni
	 * @param resultSet
	 * @param ps
	 * @param connection
	 */
	public static final void closeConnection(ResultSet resultSet, PreparedStatement ps, Connection connection ) {
		
		if(resultSet != null) try { resultSet.close(); } catch(Exception e) {}
		if(ps != null) try { ps.close(); } catch(Exception e) {}
		if(connection != null) try { connection.close(); } catch(Exception e) {}
		
	}
	

}


