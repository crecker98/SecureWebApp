package com.soriani.securewebapp.dao.categorie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.soriani.securewebapp.business.Categoria;
import com.soriani.securewebapp.dao.condivisi.Dao;
import com.soriani.securewebapp.utility.ApplicationException;

public class CategorieDao extends Dao implements CategorieDaoQuery {
	
	/**
	 * costante per connetersi al db 
	 */
	private static final String TABLE = "categorie";
	
	private static CategorieDao categorieDao;
	
	public static CategorieDao getCategorieDao() {
		if(categorieDao == null) {
			categorieDao = new CategorieDao();
		}
		return categorieDao;
	}
	
	public CategorieDao() {
		
	}
	
	/**
	 * metodo che restituisce tutte le categorie inserite a db
	 * @return
	 * @throws ApplicationException
	 * @throws SQLException
	 */
	public ArrayList<Categoria> getCategorie() throws ApplicationException, SQLException {
		
		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement ps = null;
		
		try {
			
			connection = getConnection(TABLE, SELECT);
			ps = connection.prepareStatement(readCategorieStatement);
			resultSet = ps.executeQuery();
			if(!resultSet.next()) {
				throw new ApplicationException("Categorie non disponibili");
			}

			ArrayList<Categoria> categorie = new ArrayList<Categoria>();
			do {
				Categoria categoria = new Categoria();
				categoria.setCodice(resultSet.getInt("CODICE"));
				categoria.setDescrizione(resultSet.getString("DESCRIZIONE"));
				categorie.add(categoria);
			}while(resultSet.next());
			
			return categorie;
			
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
			closeConnection(resultSet, ps, connection);
		}
		
	}

	public HashMap<String, Double> getAvgCategoriesFromUsername(String username) throws ApplicationException, SQLException {

		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement ps = null;

		try {

			connection = getConnection(TABLE, SELECT);
			ps = connection.prepareStatement(readCategoriesGroupBy);
			ps.setString(1, username);
			resultSet = ps.executeQuery();
			if(!resultSet.next()) {
				throw new ApplicationException("Categorie non disponibili");
			}

			HashMap<String, Double> categorie = new HashMap<>();
			do {

				categorie.put(resultSet.getString("CATEGORIA"), resultSet.getDouble("AVG"));

			}while(resultSet.next());

			return categorie;

		} catch(SQLException | ApplicationException e) {
			e.printStackTrace();
			throw e;
		}finally {
			closeConnection(resultSet, ps, connection);
		}

	}


}
