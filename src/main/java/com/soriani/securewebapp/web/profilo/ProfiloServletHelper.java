package com.soriani.securewebapp.web.profilo;


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

    public void updatePhoto(HttpServletRequest request) throws ApplicationException, SQLException {

        byte[] newPhoto = ChkProfilo.getInstance().chkUpdateProfilo(request);
        UtentiDao.getUtenteDao().updatePhoto(newPhoto, GestoreSessioneProfilo.getUtenteLoggato(request).getUsername());


    }

}
