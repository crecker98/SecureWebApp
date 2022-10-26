package com.soriani.securewebapp.web.profilo;


import com.soriani.securewebapp.business.Utente;
import com.soriani.securewebapp.dao.categorie.CategorieDao;
import com.soriani.securewebapp.dao.sale.SaleDao;
import com.soriani.securewebapp.dao.utenti.UtentiDao;
import com.soriani.securewebapp.utility.ApplicationException;
import com.soriani.securewebapp.utility.Password;
import com.soriani.securewebapp.web.condivisi.GestoreSessione;
import com.soriani.securewebapp.web.registration.GestoreSessioneRegistration;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashMap;

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
    public void updatePhoto(HttpServletRequest request) throws ApplicationException {

        byte[] newPhoto = ChkProfilo.getInstance().chkUpdateFotoProfilo(request);
        try {
            UtentiDao.getUtenteDao().updatePhoto(newPhoto, GestoreSessioneProfilo.getUtenteLoggato(request).getUsername());
            GestoreSessione.getUtenteLoggato(request).setImmagineProfilo(newPhoto);
        } catch(SQLException e) {
            e.printStackTrace();
            throw new ApplicationException("Errore nell'inserimento dei dati");
        }


    }

    /**
     * metodo che consente l'update delle informazioni dell'utente tranne password
     * @param request
     * @throws ApplicationException
     * @throws SQLException
     */
    public void updateInfo(HttpServletRequest request) throws ApplicationException {

        try {
            Utente utente = ChkProfilo.getInstance().checkUtente(request);
            UtentiDao.getUtenteDao().updateInfoUtente(utente, GestoreSessioneProfilo.getUtenteLoggato(request).getUsername());
            Utente utenteLoggato = GestoreSessioneProfilo.getUtenteLoggato(request);
            utenteLoggato.setNome(utente.getNome());
            utenteLoggato.setCognome(utente.getCognome());
            utenteLoggato.setUsername(utente.getUsername());
            utenteLoggato.setEmail(utente.getEmail());
        } catch(SQLException e) {
            e.printStackTrace();
            throw new ApplicationException("Errore nell'inserimento dei dati");
        }

    }

    /**
     * metodo che consente l'update della password (sale + utente)
     * @param request
     * @throws ApplicationException
     */
    public void updatePassword(HttpServletRequest request) throws ApplicationException {

        ApplicationException ex = new ApplicationException("Errore in fase di update della password!");
        String username = GestoreSessioneProfilo.getUtenteLoggato(request).getUsername();
        try{

            ChkProfilo.getInstance().checkPassword(request);
            Password.setPassword(request.getParameter("password").getBytes(), username);
            UtentiDao.getUtenteDao().updatePasswordUtente(username);
            SaleDao.getSaleDao().updateSale(username);

        } catch (NoSuchAlgorithmException | IOException | SQLException e) {
            e.printStackTrace();
            throw ex;
        }

    }

    public HashMap<String, Double> calculateCategories(HttpServletRequest request) {

        HashMap<String, Double> categorie;

        try {
            categorie = CategorieDao.getCategorieDao().getAvgCategoriesFromUsername(GestoreSessioneProfilo.getUtenteLoggato(request).getUsername());
        } catch (ApplicationException | SQLException e) {
            categorie = null;
        }

        return categorie;

    }


}
