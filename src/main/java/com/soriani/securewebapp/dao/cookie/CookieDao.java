package com.soriani.securewebapp.dao.cookie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.soriani.securewebapp.dao.condivisi.Dao;
import com.soriani.securewebapp.utility.ApplicationException;

public class CookieDao extends Dao implements CookieDaoQuery {
	
	/**
	 * costante per connetersi al db 
	 */
	private static final String TABLE = "cookie";
	
	private static CookieDao cookieDao;

	public static CookieDao getCookieDao() {
		
		if(cookieDao == null) {
			cookieDao = new CookieDao();
		}
		return cookieDao;
		
	}
	
	/**
	 * metodo che registra il cookie associato ad un username
	 * @param username
	 * @param random
	 * @throws ApplicationException
	 * @throws SQLException
	 */
	public void registraCookie(String username, String random) throws ApplicationException, SQLException {
		
		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement ps = null;
		
		try {
			
			connection = getConnection(TABLE,INSERT);
			String statement = registraCookieStatement;
			ps = connection.prepareStatement(statement);
			int i = 1;
			ps.setString(i++, username);
			ps.setBytes(i++, random.getBytes());
			ps.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
			closeConnection(resultSet, ps, connection);
		}
		
	}
	
	/**
	 * metodo che restituisce il valore del cookie in base all'username
	 * @param username
	 * @return
	 * @throws ApplicationException
	 * @throws SQLException
	 */
	public String readCookieFromUsername(String username) throws ApplicationException, SQLException {
		
		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement ps = null;
		
		try {
			
			connection = getConnection(TABLE, SELECT);
			String statement = readCookieStatement;
			ps = connection.prepareStatement(statement);
			int i = 1;
			ps.setString(i++, username);
			resultSet = ps.executeQuery();
			if(!resultSet.next()) {
				throw new ApplicationException("Username o password errati");
			}
			
			String cookie = resultSet.getBytes("VALUES").toString();
			return cookie;
			
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
			closeConnection(resultSet, ps, connection);
		}
		
	}
	
	public void deleteCookieFromUsername(String username) throws SQLException, ApplicationException {
		
		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement ps = null;
		
		try {
			
			connection = getConnection(TABLE, DELETE);
			String statement = deleteCookieStatement;
			ps = connection.prepareStatement(statement);
			int i = 1;
			ps.setString(i++, username);
			ps.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
			closeConnection(resultSet, ps, connection);
		}
		
	}

}
