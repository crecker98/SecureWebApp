package com.soriani.securewebapp.dao.sale;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.soriani.securewebapp.dao.condivisi.Dao;
import com.soriani.securewebapp.utility.ApplicationException;
import com.soriani.securewebapp.utility.Servizi;

public class SaleDao extends Dao implements SaleDaoQuery {
	
	/**
	 * costante per connetersi al db 
	 */
	private static final String TABLE = "sale";
	
	private static SaleDao saleDao;

	public static SaleDao getSaleDao() {
		
		if(saleDao == null) {
			saleDao = new SaleDao();
		}
		return saleDao;
		
	}

	public void updateSale(String username) throws ApplicationException, SQLException, IOException {

		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement ps = null;

		try {

			connection = getConnection(TABLE, UPDATE);
			ps = connection.prepareStatement(updateSaltStatement);
			int i = 1;
			ps.setBytes(i++, Servizi.readBytes("salt" + username + ".bin"));
			ps.setString(i, username);
			ps.executeUpdate();

		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
			closeConnection(resultSet, ps, connection);
			Servizi.deleteFile("salt" + username + ".bin");
		}

	}
	
	/**
	 * metodo che consente di registrare il sale per l'utente(fase di registrazione)
	 * @param username
	 * @throws ApplicationException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void registraSale(String username) throws ApplicationException, SQLException, IOException {
		
		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement ps = null;
		
		try {
			
			connection = getConnection(TABLE, INSERT);
			ps = connection.prepareStatement(registraSaltStatement);
			int i = 1;
			ps.setString(i++, username);
			ps.setBytes(i, Servizi.readBytes("salt" + username + ".bin"));
			ps.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
			closeConnection(resultSet, ps, connection);
			Servizi.deleteFile("salt" + username + ".bin");
		}
		
	}

	/**
	 * metodo che restituisce il salt collegato ad un username
	 * @param username
	 * @return
	 * @throws ApplicationException
	 * @throws SQLException
	 */
	public byte[] readSaltFromUsername(String username) throws ApplicationException, SQLException {
		
		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement ps = null;
		
		try {
			
			connection = getConnection(TABLE, SELECT);
			ps = connection.prepareStatement(readSaltFromUsernameStatement);
			ps.setString(1, username);
			resultSet = ps.executeQuery();
			if(!resultSet.next()) {
				throw new ApplicationException("Username o password errati");
			}

			return resultSet.getBytes("SALT");
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
			closeConnection(resultSet, ps, connection);
		}
		
	}

}
