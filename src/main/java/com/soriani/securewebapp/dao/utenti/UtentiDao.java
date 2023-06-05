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

public final class UtentiDao extends Dao implements UtentiDaoQuery {
	
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
	 * metodo che consente l'update della password in base all'username
	 * @param username
	 * @throws ApplicationException
	 * @throws SQLException
	 */
	public void updatePasswordUtente(String username) throws ApplicationException, SQLException, IOException {

		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement ps = null;

		try {

			connection = getConnection(TABLE, UPDATE);
			ps = connection.prepareStatement(updatePasswordFromUsernameStatement);
			int i = 1;
			ps.setBytes(i++, Servizi.readBytes("password" + username + ".bin"));
			ps.setString(i, username);
			ps.executeUpdate();

		}catch(SQLException | ApplicationException | IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeConnection(resultSet, ps, connection);
			Servizi.deleteFile("password" + username + ".bin");
		}

	}

	/**
	 * meotodo che consente l'update delle informazioni dell'utente loggato tranne password
	 * @param newUtente
	 * @throws ApplicationException
	 * @throws SQLException
	 */
	public void updateInfoUtente(Utente newUtente, String oldUsername) throws ApplicationException, SQLException {

		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement ps = null;

		try {

			connection = getConnection(TABLE, UPDATE);
			ps = connection.prepareStatement(updateInfoFromUsernameStatement);
			int i = 1;
			ps.setString(i++, newUtente.getEmail());
			ps.setString(i++, newUtente.getUsername());
			ps.setString(i++, newUtente.getNome());
			ps.setString(i++, newUtente.getCognome());
			ps.setString(i, oldUsername);
			ps.executeUpdate();

		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
			closeConnection(resultSet, ps, connection);
		}

	}

	/**
	 * metodo che consente di aggiornare la foto dell'utente loggato
	 * @param newPhoto
	 * @param username
	 * @throws ApplicationException
	 * @throws SQLException
	 */
	public void updatePhoto(byte[] newPhoto, String username) throws ApplicationException, SQLException {

		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement ps = null;

		try {

			connection = getConnection(TABLE, UPDATE);
			ps = connection.prepareStatement(updatePhotoFromUsernameStatement);
			int i = 1;
			ps.setBytes(i++, newPhoto);
			ps.setString(i, username);
			ps.executeUpdate();

		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
			closeConnection(resultSet, ps, connection);
		}

	}
	
	/**
	 * meotodo che ricerca l'utente per username
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
			ps = connection.prepareStatement(readUtenteFromUsernameStatement);
			ps.setString(1, username);
			resultSet = ps.executeQuery();
			return resultSet.next();
			
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
	 * @return
	 * @throws ApplicationException
	 * @throws SQLException
	 * @throws IOException 
	 */
	public void registraUtente(Utente utente) throws ApplicationException, SQLException, IOException {
		
		Connection connection = null;
		PreparedStatement ps = null;
		
		try {
			
			connection = getConnection(TABLE, INSERT);
			ps = connection.prepareStatement(insertUtenteStatement);
			int i = 1;
			ps.setString(i++, utente.getNome());
			ps.setString(i++, utente.getCognome());
			ps.setString(i++, utente.getUsername());
			ps.setBytes(i++, utente.getImmagineProfilo());
			ps.setBytes(i++, Servizi.readBytes("password" + utente.getUsername() + ".bin"));
			ps.setString(i, utente.getEmail());
			ps.executeUpdate();
			
		}catch(SQLException | IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeConnection(null, ps, connection);
			Servizi.deleteFile("password" + utente.getUsername() + ".bin");
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
			ps = connection.prepareStatement(readUtenteFromUsernameStatement);
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
			utente.setEmail(resultSet.getString("EMAIL"));
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
