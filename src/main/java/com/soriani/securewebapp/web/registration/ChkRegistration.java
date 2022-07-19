package com.soriani.securewebapp.web.registration;


import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;
import com.soriani.securewebapp.business.Utente;
import com.soriani.securewebapp.dao.utenti.UtentiDao;
import com.soriani.securewebapp.utility.ApplicationException;
import com.soriani.securewebapp.utility.Controllore;
import com.soriani.securewebapp.utility.Servizi;


public class ChkRegistration {
	
	private static ChkRegistration instance = new ChkRegistration();
	
	private ChkRegistration() {
		
	}
	
	static ChkRegistration getInstance() {
		return instance;
	}
	
	/**
	 * metodo che controlla se le informazioni inserite dall'utente sono corrette
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	public Utente checkUtente(HttpServletRequest request) throws ApplicationException {
		
		HashMap<String, String> form = GestoreSessioneRegistration.getRegistrationForm(request);
		Utente utente = new Utente();
		if(!Controllore.isString(form.get("nome"))) {
			throw new ApplicationException("Inserire un nome valido");
		}
		
		if(!Controllore.isString(form.get("cognome"))) {
			throw new ApplicationException("Inserire un cognome valido");
		}
		
		if(!Controllore.isAlfanumericString(form.get("username"))) {
			throw new ApplicationException("Inserire un username valido, non può contenere caratteri speciali!");
		}
		
		try {
			
			if(UtentiDao.getUtenteDao().readUtenteFromUsername(form.get("username"))) {
				throw new ApplicationException("Username già in uso. Riprova!");
			}
			
		}catch(SQLException e) {
			throw new ApplicationException("Errore di connessione!");
		}catch(ApplicationException e1) {
			throw new ApplicationException(e1.getMessaggio());
		}
		
		utente.setNome(form.get("nome"));
		utente.setCognome(form.get("nome"));
		utente.setUsername(form.get("username"));
		
		if(request.getParameter("password").equals(request.getParameter("ripeti_password"))) {
			
			if(!Controllore.checkPassword(request.getParameter("password")) || !Controllore.checkPassword(request.getParameter("ripeti_password"))) {
				throw new ApplicationException("La password non rispetta i vincoli di inserimento!");
			}
			
		}else {
			throw new ApplicationException("Le password non coincidono");
		}
		
		Part filePart = null;
		try {
			filePart = request.getPart("immagine_profilo");
		} catch (IOException e) {
			e.printStackTrace();
			throw new ApplicationException("Errore nel caricamento dell'immagine");
		} catch (ServletException e) {
			e.printStackTrace();
			throw new ApplicationException("Errore nel caricamento dell'immagine");
		}
		
		if(filePart.getSize() > 0) {
			
			String esitoImmagine;
			try {
				esitoImmagine = Controllore.checkImmagineDelProfilo(filePart.getInputStream());
				utente.setImmagineProfilo(Servizi.readAllBytes(filePart.getInputStream()));
			} catch (IOException e) {
				e.printStackTrace();
				throw new ApplicationException("Impossibile elaborare il file!");
			} catch (SAXException e) {
				e.printStackTrace();
				throw new ApplicationException("Impossibile elaborare il file!");
			} catch (TikaException e) {
				e.printStackTrace();
				throw new ApplicationException("Impossibile elaborare il file!");
			}
			
			if(esitoImmagine != null) {
				throw new ApplicationException(esitoImmagine);
			}
			
		}else {
			utente.setImmagineProfilo(null);
		}
		
		
		
		return utente;
		
	}

}
