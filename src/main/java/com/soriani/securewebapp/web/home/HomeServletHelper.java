package com.soriani.securewebapp.web.home;


import com.soriani.securewebapp.business.PropostaProgettuale;
import com.soriani.securewebapp.dao.proposte.ProposteProgettualiDao;
import com.soriani.securewebapp.utility.ApplicationException;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;

public class HomeServletHelper {

    private static HomeServletHelper instance = new HomeServletHelper();

    private HomeServletHelper(){

    }

    protected static HomeServletHelper getInstance(){
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
        }catch(ApplicationException e){
            e.printStackTrace();
        }catch(SQLException e1){
            e1.printStackTrace();
        }

        GestoreSessioneHome.setProposte(request, proposte);

    }

}
