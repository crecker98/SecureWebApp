package com.soriani.securewebapp.dao.proposte;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.soriani.securewebapp.business.PropostaProgettuale;
import com.soriani.securewebapp.dao.condivisi.Dao;
import com.soriani.securewebapp.utility.ApplicationException;

public class ProposteProgettualiDao extends Dao implements ProposteProgettualiDaoQuery {
	
	/**
	 * costante per connetersi al db 
	 */
	private static final String TABLE = "proposte_progettuali";
	
	
	private static ProposteProgettualiDao utenteDao;

	public static ProposteProgettualiDao getProposteDao() {
		
		if(utenteDao == null) {
			utenteDao = new ProposteProgettualiDao();
		}
		return utenteDao;
		
	}
	
	/**
	 * costrutture
	 */
	public ProposteProgettualiDao() {
		
	}
	
	/**
	 * metodo che inserisce la proposta progettuale
	 * @param proposta
	 * @param username
	 * @throws ApplicationException
	 * @throws SQLException
	 */
	public void insertPropostaProgettuale(PropostaProgettuale proposta, String username) throws ApplicationException, SQLException {
		
		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement ps = null;
		
		try {
			
			connection = getConnection(TABLE, INSERT);
			String statement = inserPropostaStatement;
			ps = connection.prepareStatement(statement);
			int i = 1;
			ps.setString(i++, username);
			ps.setInt(i++, proposta.getCodCategoria());
			ps.setBytes(i++, proposta.getFile());
			ps.setString(i++, proposta.getNome());
			ps.setString(i++, proposta.getDescrizione());
			ps.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
			closeConnection(resultSet, ps, connection);
		}
		
	}

}
