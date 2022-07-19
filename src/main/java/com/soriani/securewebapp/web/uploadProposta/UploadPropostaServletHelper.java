package com.soriani.securewebapp.web.uploadProposta;

import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.soriani.securewebapp.business.GRPCategoria;
import com.soriani.securewebapp.business.PropostaProgettuale;
import com.soriani.securewebapp.dao.categorie.CategorieDao;
import com.soriani.securewebapp.dao.proposte.ProposteProgettualiDao;
import com.soriani.securewebapp.utility.ApplicationException;

public class UploadPropostaServletHelper {
	
	private static UploadPropostaServletHelper instance = new UploadPropostaServletHelper();
	
	private UploadPropostaServletHelper() {
		
	}
	
	protected static UploadPropostaServletHelper getInstance() {
		return instance;
	}
	
	/**
	 * setta le categorie in sessione
	 * @param request
	 * @throws ApplicationException
	 */
	public void setCategorieSessione(HttpServletRequest request) throws ApplicationException {
		
		try {
			GRPCategoria categorie = CategorieDao.getCategorieDao().getGRPCategorie();
			GestoreSessioneUploadProposta.setGRPCategorie(request, categorie);
		} catch (ApplicationException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException("Errore durante il caricamento delle categorie");
		}
		
	}
	
	/**
	 * setta il form in sessione
	 * @param request
	 */
	public void setFormSessione(HttpServletRequest request) {
		
		HashMap<String, String> form = new HashMap<String, String>();
		form.put("NomeProposta", request.getParameter("nome") != null ? request.getParameter("nome") : "" );
		form.put("Categoria", request.getParameter("cod_categoria") != null ? request.getParameter("cod_categoria").toString() : "0" );
		form.put("Descrizione", request.getParameter("descrizione") != null ? request.getParameter("descrizione") : "");
		GestoreSessioneUploadProposta.setFormUpload(request, form);
		
	}
	
	/**
	 * 
	 * @param request
	 * @throws ApplicationException
	 */
	public void insertPropostaProgettuale(HttpServletRequest request) throws ApplicationException {
		
		PropostaProgettuale proposta = ChkUploadProposta.getInstance().checkPropostaProgettuale(request);
		try {
			ProposteProgettualiDao.getProposteDao().insertPropostaProgettuale(proposta, GestoreSessioneUploadProposta.getUtenteLoggato(request).getUsername());
		} catch (ApplicationException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException("Errore in fase di inserimento");
		}
		
	}

}
