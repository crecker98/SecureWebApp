package com.soriani.securewebapp.web.uploadProposta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import com.google.common.io.Files;
import com.soriani.securewebapp.business.Categoria;
import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

import com.soriani.securewebapp.business.PropostaProgettuale;
import com.soriani.securewebapp.utility.ApplicationException;
import com.soriani.securewebapp.utility.Controllore;
import com.soriani.securewebapp.utility.Servizi;

public class ChkUploadProposta {

	private static final String TXT_EXTENSION = "text/plain";
	private static ChkUploadProposta instance = new ChkUploadProposta();

	private ChkUploadProposta() {
		
	}
	
	static ChkUploadProposta getInstance() {
		return instance;
	}

	/**
	 * metodo che controlla i dati inseriti nel form di upload proposta
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	public PropostaProgettuale checkPropostaProgettuale(HttpServletRequest request) throws ApplicationException {
		
		HashMap<String, String> form = GestoreSessioneUploadProposta.getFormUpload(request);
		PropostaProgettuale proposta = new PropostaProgettuale();
		ApplicationException exception = new ApplicationException();
		
		if(!Controllore.isAlfanumericString(form.get("NomeProposta").replaceAll("\\s+",""))) {
			exception.setMessaggio("Inserire un nome proposta valido");
			throw exception;
		}
		proposta.setNome(form.get("NomeProposta"));
		
		if(!Controllore.isAlfanumericString(form.get("Descrizione").replaceAll("\\s+",""))) {
			exception.setMessaggio("Inserire una descrizione valida");
			throw exception;
		}
		proposta.setDescrizione(form.get("Descrizione"));
		
		if(Integer.parseInt(form.get("Categoria").toString()) < 0 && Integer.parseInt(form.get("Categoria").toString()) > 10 ) {
			exception.setMessaggio("Inserire una categoria valida");
			throw exception;
		}
		Categoria categoria = new Categoria();
		categoria.setCodice((int)Integer.parseInt(form.get("Categoria").toString()));
		proposta.setCategoria(categoria);

		Part filePart = null;
		try {
			filePart = request.getPart("file_proposta");
		} catch (IOException e) {
			e.printStackTrace();
			exception.setMessaggio("Errore nel caricamento dell file");
			throw exception;
		} catch (ServletException e) {
			e.printStackTrace();
			exception.setMessaggio("Errore nel caricamento dell file");
			throw exception;
		}
		
		if(filePart.getSize() > 0) {

			exception.setMessaggio("Impossibile elaborare il file!");
			ArrayList<String> contentTypes = new ArrayList<>();
			contentTypes.add(TXT_EXTENSION);
			try {
				Controllore.checkFile(filePart, contentTypes);
				proposta.setFile(Servizi.readAllBytes(filePart.getInputStream()));
			} catch (IOException e) {
				e.printStackTrace();
				throw exception;
			} catch (ApplicationException e) {
				e.printStackTrace();
				throw e;
			}
			
		}else {
			exception.setMessaggio("Necessario caricare un file per la proposta progettuale");
			throw exception;
		}
		
		return proposta;
		
	}

}
