package com.soriani.securewebapp.dao.proposte;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.soriani.securewebapp.business.Categoria;
import com.soriani.securewebapp.business.PropostaProgettuale;
import com.soriani.securewebapp.business.Utente;
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
			ps = connection.prepareStatement(inserPropostaStatement);
			int i = 1;
			ps.setString(i++, username);
			ps.setInt(i++, proposta.getCategoria().getCodice());
			ps.setBytes(i++, proposta.getFile());
			ps.setString(i++, proposta.getNome());
			ps.setString(i, proposta.getDescrizione());
			ps.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
			closeConnection(resultSet, ps, connection);
		}
		
	}


	/**
	 * Metodo che consente di prelevare tutte le proposte caricate
	 * @return
	 * @throws ApplicationException
	 * @throws SQLException
	 */
	public ArrayList<PropostaProgettuale> readAllProposte() throws ApplicationException, SQLException {

		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement ps = null;

		try{

			connection = getConnection(TABLE, SELECT);
			ps = connection.prepareStatement(selectPropostaStatement);
			resultSet = ps.executeQuery();
			if(!resultSet.next()) {
				throw new ApplicationException("Proposte non disponibili");
			}

			ArrayList<PropostaProgettuale> proposte = new ArrayList<>();
			do{
				PropostaProgettuale proposta = new PropostaProgettuale();
				proposta.setCodice(resultSet.getInt("P.CODICE"));
				proposta.setNome(resultSet.getString("P.NOME"));
				proposta.setDescrizione(resultSet.getString("P.DESCRIZIONE"));
				proposta.setDataAggiungta(resultSet.getString("P.DATA_AGGIUNTA"));
				proposta.setFile(resultSet.getBytes("P.FILE"));

				Utente utente = new Utente();
				utente.setUsername(resultSet.getString("U.USERNAME"));
				utente.setNome(resultSet.getString("U.NOME"));
				utente.setCognome(resultSet.getString("U.COGNOME"));
				proposta.setUtente(utente);

				Categoria categoria = new Categoria();
				categoria.setCodice(resultSet.getInt("C.CODICE"));
				categoria.setDescrizione(resultSet.getString("C.DESCRIZIONE"));
				proposta.setCategoria(categoria);

				proposte.add(proposta);
			} while (resultSet.next());

			return proposte;

		}catch(SQLException e){
			e.printStackTrace();
			throw e;
		}finally {
			closeConnection(resultSet, ps, connection);
		}

	}

	public HashMap<String, String> getTotCategoriePropostaUtente(String username) throws ApplicationException, SQLException {

		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement ps = null;

		try{

			connection = getConnection(TABLE, SELECT);
			ps = connection.prepareStatement(selectCategoriaPropostaUtente);
			resultSet = ps.executeQuery();
			if(!resultSet.next()) {
				throw new ApplicationException("Proposte non disponibili");
			}

			HashMap<String, String> categorie = new HashMap<String, String>();

			return categorie;

		}catch(SQLException e){
			e.printStackTrace();
			throw e;
		}finally {
			closeConnection(resultSet, ps, connection);
		}

	}

}
