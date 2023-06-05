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

public final class RegistrationServletHelper {
	
	private static final RegistrationServletHelper instance = new RegistrationServletHelper();
	
	private RegistrationServletHelper() {
		
	}
	
	static RegistrationServletHelper getInstance() {
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
			
		} catch (ApplicationException | IOException | SQLException e) {
			e.printStackTrace();
			throw ex;
		}
		
	}

}
