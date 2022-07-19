package com.soriani.securewebapp.web.registration;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.bouncycastle.util.Arrays;

import com.soriani.securewebapp.business.Utente;
import com.soriani.securewebapp.dao.sale.SaleDao;
import com.soriani.securewebapp.dao.utenti.UtentiDao;
import com.soriani.securewebapp.utility.ApplicationException;
import com.soriani.securewebapp.utility.Password;

public class RegistrationServletHelper {
	
	private static RegistrationServletHelper instance = new RegistrationServletHelper();
	
	private RegistrationServletHelper() {
		
	}
	
	protected static RegistrationServletHelper getInstance() {
		return instance;
	}

	/**
	 * metodo che effettua la registrazione
	 * @param utente
	 * @return
	 */
	public void registrationUtente(HttpServletRequest request, Utente utente) throws ApplicationException {
		
		ApplicationException ex = new ApplicationException("Errore in fase di registrazione!");
		try {
			
			Password.setPassword(request.getParameter("password").getBytes(), utente.getUsername());
			UtentiDao.getUtenteDao().registraUtente(utente);
			SaleDao.getSaleDao().registraSale(utente.getUsername());
			Arrays.fill(request.getParameter("password").getBytes(), (byte) 0);
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw ex;
		} catch (IOException e) {
			e.printStackTrace();
			throw ex;
		} catch(SQLException e) {
			throw ex; 
		}
		
	}
	
	/**
	 * setta il form per la registrazione
	 * @param request
	 */
	public void setDatiForm(HttpServletRequest request) {
		
		HashMap<String, String> form = new HashMap<String, String>();
		form.put("nome", request.getParameter("nome") != null ? request.getParameter("nome") : "" );
		form.put("cognome", request.getParameter("cognome") != null ? request.getParameter("cognome") : "");
		form.put("username", request.getParameter("username") != null ? request.getParameter("username") : "");
		GestoreSessioneRegistration.setRegistrationForm(request, form);
		
	}

}
