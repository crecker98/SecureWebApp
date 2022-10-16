package com.soriani.securewebapp.web.profilo;


import com.soriani.securewebapp.business.Utente;
import com.soriani.securewebapp.dao.utenti.UtentiDao;
import com.soriani.securewebapp.utility.ApplicationException;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

/**
 * @author christiansoriani on 30/07/22
 * @project SecureWebApp
 */
public class ProfiloServletHelper {

    private static ProfiloServletHelper instance = new ProfiloServletHelper();

    private ProfiloServletHelper() {

    }

    protected static ProfiloServletHelper getInstance() {
        return instance;
    }

    /**
     * metodo che consente l'update dell'imagine del profilo
     * @param request
     * @throws ApplicationException
     * @throws SQLException
     */
    public void updatePhoto(HttpServletRequest request) throws ApplicationException, SQLException {

        byte[] newPhoto = ChkProfilo.getInstance().chkUpdateFotoProfilo(request);
        UtentiDao.getUtenteDao().updatePhoto(newPhoto, GestoreSessioneProfilo.getUtenteLoggato(request).getUsername());

    }

    /**
     * metodo che consente l'update delle informazioni dell'utente tranne password
     * @param request
     * @throws ApplicationException
     * @throws SQLException
     */
    public void updateInfo(HttpServletRequest request) throws ApplicationException, SQLException {


        Utente utente = ChkProfilo.getInstance().checkUtente(request);
        UtentiDao.getUtenteDao().updateInfoUtente(utente, GestoreSessioneProfilo.getUtenteLoggato(request).getUsername());

    }

    /**
     * metodo che permette di popolore un oggetto Utente
     * @param request
     * @return
     */
    private Utente popolaUtente(HttpServletRequest request) {

        Utente utente = new Utente();
        utente.setUsername(request.getParameter("newUsername"));
        utente.setNome(request.getParameter("newNome"));
        utente.setCognome(request.getParameter("newCognome"));

        return utente;

    }


}
