package com.soriani.securewebapp.web.profilo;


import com.soriani.securewebapp.utility.ApplicationException;
import com.soriani.securewebapp.utility.Controllore;
import com.soriani.securewebapp.utility.Servizi;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.ArrayList;

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

    public byte[] chkUpdateProfilo(HttpServletRequest request) throws ApplicationException {

        ApplicationException exception = new ApplicationException();

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
            exception.setMessaggio("Necessario caricare un file per la proposta progettuale");
            throw exception;
        }

    }

}
