package com.soriani.securewebapp.dao.cookie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.soriani.securewebapp.business.CustomCookie;
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
	 * metodo che registra il customCookie associato ad un username
	 * @param username
	 * @throws ApplicationException
	 * @throws SQLException
	 */
	public void registraCookie(String username, CustomCookie customCookie) throws ApplicationException, SQLException {
		
		Connection connection = null;
		PreparedStatement ps = null;
		
		try {
			
			connection = getConnection(TABLE,INSERT);
			ps = connection.prepareStatement(registraCookieStatement);
			int i = 1;
			ps.setString(i++, username);
			ps.setBytes(i++, customCookie.getKey());
			ps.setBytes(i, customCookie.getValue());
			ps.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
			closeConnection(null, ps, connection);
		}
		
	}
	
	/**
	 * metodo che restituisce il valore del cookie in base all'username
	 * @param username
	 * @return
	 * @throws ApplicationException
	 * @throws SQLException
	 */
	public ArrayList<CustomCookie> readCookies() throws ApplicationException, SQLException {
		
		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement ps = null;
		
		try {
			
			connection = getConnection(TABLE, SELECT);
			ps = connection.prepareStatement(readCookieStatement);
			resultSet = ps.executeQuery();
			if(!resultSet.next()) {
				throw new ApplicationException("Username o password errati");
			}

			ArrayList<CustomCookie> list = new ArrayList<>();
			do{
				CustomCookie customCookie = new CustomCookie();
				customCookie.setKey(resultSet.getBytes("KEY_COOKIE"));
				customCookie.setValue(resultSet.getBytes("VALUE"));
				list.add(customCookie);
			}while (resultSet.next());

			return list;
			
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
			ps = connection.prepareStatement(deleteCookieStatement);
			ps.setString(1, username);
			ps.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
			closeConnection(resultSet, ps, connection);
		}
		
	}

}
