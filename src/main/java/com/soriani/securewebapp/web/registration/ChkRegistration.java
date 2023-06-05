package com.soriani.securewebapp.web.registration;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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


public final class ChkRegistration {

	//COSTANTI PER GESTIRE I TIPI DI FILE DA CARICARE
	private static final String JPEG_EXTENSION = "image/jpeg";
	private static final String PNG_EXTENSION = "image/png";
	private static final String JPG_EXTENSION = "image/jpg";
	
	private static final ChkRegistration instance = new ChkRegistration();
	
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

		Utente utente = Servizi.checkInfoUtente(request, GestoreSessioneRegistration.getRegistrationForm(request), new Utente());

		Servizi.takePassword(request);

		Part filePart = null;
		try {
			filePart = request.getPart("immagine_profilo");
		} catch (IOException | ServletException e) {
			e.printStackTrace();
			throw new ApplicationException("Errore nel caricamento dell'immagine");
		}

		if(filePart.getSize() > 0) {

			ArrayList<String> contentTypes = new ArrayList<>();
			contentTypes.add(JPEG_EXTENSION);
			contentTypes.add(PNG_EXTENSION);
			contentTypes.add(JPG_EXTENSION);
			try {
				Controllore.checkFile(filePart, contentTypes);
				utente.setImmagineProfilo(Servizi.readAllBytes(filePart.getInputStream()));
			} catch (IOException e) {
				e.printStackTrace();
				throw new ApplicationException("Impossibile elaborare il file!");
			}

		}else {
			utente.setImmagineProfilo(null);
		}
		
		
		
		return utente;
		
	}



}
