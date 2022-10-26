package com.soriani.securewebapp.web.profilo;


import com.soriani.securewebapp.business.Utente;
import com.soriani.securewebapp.dao.utenti.UtentiDao;
import com.soriani.securewebapp.utility.ApplicationException;
import com.soriani.securewebapp.utility.Controllore;
import com.soriani.securewebapp.utility.Servizi;
import com.soriani.securewebapp.web.registration.ChkRegistration;
import com.soriani.securewebapp.web.registration.GestoreSessioneRegistration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * @author christiansoriani on 30/07/22
 * @project SecureWebApp
 */
public class ChkProfilo {

    //COSTANTI PER GESTIRE I TIPI DI FILE DA CARICARE
    private static final String JPEG_EXTENSION = "image/jpeg";
    private static final String PNG_EXTENSION = "image/png";
    private static final String JPG_EXTENSION = "image/jpg";

    private static ChkProfilo instance = new ChkProfilo();

    private ChkProfilo() {

    }

    static ChkProfilo getInstance() {
        return instance;
    }

    /**
     * metodo che controlla se le informazioni inserite dall'utente sono corrette
     * @param request
     * @return
     * @throws ApplicationException
     */
    public Utente checkUtente(HttpServletRequest request) throws ApplicationException {

        return Servizi.checkInfoUtente(request, GestoreSessioneProfilo.getUpdateForm(request), new Utente());

    }

    /**
     * effettua i controlli sulla foto caricata
     * @param request
     * @return
     * @throws ApplicationException
     */
    public byte[] chkUpdateFotoProfilo(HttpServletRequest request) throws ApplicationException {

        ApplicationException exception = new ApplicationException();

        Part filePart = null;
        try {
            filePart = request.getPart("fotoProfilo");
        } catch (IOException | ServletException e) {
            e.printStackTrace();
            exception.setMessaggio("Errore nel caricamento dell'immagine del profilo");
            throw exception;
        }

        if(filePart.getSize() > 0) {

            exception.setMessaggio("Impossibile elaborare l'immagine!");
            ArrayList<String> contentTypes = new ArrayList<>();
            contentTypes.add(JPEG_EXTENSION);
            contentTypes.add(PNG_EXTENSION);
            contentTypes.add(JPG_EXTENSION);
            try {
                Controllore.checkFile(filePart, contentTypes);
                return Servizi.readAllBytes(filePart.getInputStream());
            } catch (ApplicationException e) {
                e.printStackTrace();
                throw e;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }else {
            exception.setMessaggio("Necessario caricare un foto per aggiornare il profilo");
            throw exception;
        }

    }

    /**
     * metodo che controlla la correttezza della password inserita per l'update
     * @param request
     * @throws ApplicationException
     */
    public void checkPassword(HttpServletRequest request) throws ApplicationException {

        Servizi.takePassword(request);

    }

}
