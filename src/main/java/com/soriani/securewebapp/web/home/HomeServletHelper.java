package com.soriani.securewebapp.web.home;


import com.soriani.securewebapp.business.PropostaProgettuale;
import com.soriani.securewebapp.dao.proposte.ProposteProgettualiDao;
import com.soriani.securewebapp.utility.ApplicationException;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;

public final class HomeServletHelper {

    private static final HomeServletHelper instance = new HomeServletHelper();

    private HomeServletHelper(){

    }

    static HomeServletHelper getInstance(){
        return instance;
    }

    /**
     * metodo che popola le prposte nella tabella presente nella home page
     * @param request
     * @throws ApplicationException
     * @throws SQLException
     */
    public void readAllProposte(HttpServletRequest request) {

        ArrayList<PropostaProgettuale> proposte = new ArrayList<>();
        try{
            proposte = ProposteProgettualiDao.getProposteDao().readAllProposte();
        }catch(ApplicationException | SQLException e){
            e.printStackTrace();
        }

        GestoreSessioneHome.setProposte(request, proposte);

    }

    public void getProposta(HttpServletRequest request) {

        byte[] proposta = null;
        try {
            proposta = ProposteProgettualiDao.getProposteDao().getPropostaByCodice(GestoreSessioneHome.getCodiceProposta(request));
        } catch (SQLException | ApplicationException e) {
            e.printStackTrace();
        }
        GestoreSessioneHome.setVisualizza(request, proposta);

    }

}
