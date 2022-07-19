package com.soriani.securewebapp.dao.utenti;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.soriani.securewebapp.business.Utente;
import com.soriani.securewebapp.dao.condivisi.Dao;
import com.soriani.securewebapp.utility.ApplicationException;
import com.soriani.securewebapp.utility.Servizi;

public class UtentiDao extends Dao implements UtentiDaoQuery {
	
	/**
	 * costante per connetersi al db 
	 */
	private static final String TABLE = "utenti";
	
	
	private static UtentiDao utenteDao;

	public static UtentiDao getUtenteDao() {
		
		if(utenteDao == null) {
			utenteDao = new UtentiDao();
		}
		return utenteDao;
		
	}
	
	/**
	 * costrutture
	 */
	public UtentiDao() {
		
	}
	
	/**
	 * meotodo che ricerca l'utente per username, ritorna tr
	 * @param username
	 * @return
	 * @throws ApplicationException
	 * @throws SQLException
	 */
	public boolean readUtenteFromUsername(String username) throws ApplicationException, SQLException {
		
		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement ps = null;
		
		try {
			
			connection = getConnection(TABLE, SELECT);
			String statement = readUtenteFromUsernameStatement;
			ps = connection.prepareStatement(statement);
			ps.setString(1, username);
			resultSet = ps.executeQuery();
			boolean trovato = resultSet.next();
			return trovato;
			
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
			closeConnection(resultSet, ps, connection);
		}
		
	}
	
	/**
	 * metodo che provvede a registrare l'utente
	 * @param utente
	 * @param password
	 * @return
	 * @throws ApplicationException
	 * @throws SQLException
	 * @throws IOException 
	 */
	public void registraUtente(Utente utente) throws ApplicationException, SQLException, IOException {
		
		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement ps = null;
		
		try {
			
			connection = getConnection(TABLE, INSERT);
			String statement = insertUtenteStatement;
			ps = connection.prepareStatement(statement);
			int i = 1;
			ps.setString(i++, utente.getNome());
			ps.setString(i++, utente.getCognome());
			ps.setString(i++, utente.getUsername());
			ps.setBytes(i++, utente.getImmagineProfilo());
			ps.setBytes(i++, Servizi.readBytes("password" + utente.getUsername() + ".bin"));
			Servizi.deleteFile("password" + utente.getUsername() + ".bin");
			ps.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}catch(IOException e) {
			e.printStackTrace();
			throw e;
		}finally {
			closeConnection(resultSet, ps, connection);
		}
		
	}
	
	/**
	 * metodo per estrarr password in base all'username
	 * @param username
	 * @return
	 * @throws ApplicationException
	 * @throws SQLException
	 */
	public byte[] readPasswordFromUsername(String username) throws ApplicationException, SQLException {
		
		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement ps = null;
		
		try {
			
			connection = getConnection(TABLE, SELECT);
			String statement = readPasswordFromUsernameStatemant;
			ps = connection.prepareStatement(statement);
			int i = 1;
			ps.setString(i++, username);
			resultSet = ps.executeQuery();
			if(!resultSet.next()) {
				throw new ApplicationException("Username o password errati");
			}
			
			byte[] pwd = resultSet.getBytes("PASSWORD");
			return pwd;
			
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
			closeConnection(resultSet, ps, connection);
		}
		
		
		
	}
	
	/**
	 * metodo che restituisce l'oggetto utente in base all'username
	 * @param username
	 * @return
	 * @throws ApplicationException
	 * @throws SQLException
	 */
	public Utente getUtenteFromUsername(String username) throws ApplicationException, SQLException {
		
		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement ps = null;
		
		try {
			
			connection = getConnection(TABLE, SELECT);
			String statement = readUtenteFromUsernameStatement;
			ps = connection.prepareStatement(statement);
			int i = 1;
			ps.setString(i++, username);
			resultSet = ps.executeQuery();
			if(!resultSet.next()) {
				throw new ApplicationException("Username o password errati");
			}
			
			Utente utente = new Utente();
			utente.setNome(resultSet.getString("NOME"));
			utente.setCognome(resultSet.getString("COGNOME"));
			utente.setUsername(username);
			utente.setImmagineProfilo(resultSet.getBytes("IMMAGINE_PROFILO"));
			return utente;
			
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
			closeConnection(resultSet, ps, connection);
		}
		
		
		
		
	}
	
}
