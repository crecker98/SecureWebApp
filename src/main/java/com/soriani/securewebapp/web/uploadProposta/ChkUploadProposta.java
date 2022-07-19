package com.soriani.securewebapp.web.uploadProposta;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

import com.soriani.securewebapp.business.PropostaProgettuale;
import com.soriani.securewebapp.utility.ApplicationException;
import com.soriani.securewebapp.utility.Controllore;
import com.soriani.securewebapp.utility.Servizi;

public class ChkUploadProposta {
	
private static ChkUploadProposta instance = new ChkUploadProposta();
	
	private ChkUploadProposta() {
		
	}
	
	static ChkUploadProposta getInstance() {
		return instance;
	}
	
	public PropostaProgettuale checkPropostaProgettuale(HttpServletRequest request) throws ApplicationException {
		
		HashMap<String, String> form = GestoreSessioneUploadProposta.getFormUpload(request);
		PropostaProgettuale proposta = new PropostaProgettuale();
		
		if(!Controllore.isString(form.get("NomeProposta"))) {
			throw new ApplicationException("Inserire un nome proposta valido");
		}
		proposta.setNome(form.get("NomeProposta"));
		
		if(!Controllore.isAlfanumericString(form.get("Descrizione"))) {
			throw new ApplicationException("Inserire una descrizione valida");
		}
		proposta.setDescrizione(form.get("Descrizione"));
		
		if(Integer.parseInt(form.get("Categoria").toString()) < 0 && Integer.parseInt(form.get("Categoria").toString()) > 10 ) {
			throw new ApplicationException("Inserire una categoria valida");
		}
		proposta.setCodCategoria((int)Integer.parseInt(form.get("Categoria").toString()));
		
		Part filePart = null;
		try {
			filePart = request.getPart("file_proposta");
		} catch (IOException e) {
			e.printStackTrace();
			throw new ApplicationException("Errore nel caricamento dell'immagine");
		} catch (ServletException e) {
			e.printStackTrace();
			throw new ApplicationException("Errore nel caricamento dell'immagine");
		}
		
		if(filePart.getSize() > 0) {
			
			String esitoFile;
			try {
				esitoFile = Controllore.checkFileProposta(filePart.getInputStream());
				proposta.setFile(Servizi.readAllBytes(filePart.getInputStream()));
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
			
			if(esitoFile != null) {
				throw new ApplicationException(esitoFile);
			}
			
		}else {
			throw new ApplicationException("Necessario caricare un file per la proposta progettuale");
		}
		
		return proposta;
		
	}

}
