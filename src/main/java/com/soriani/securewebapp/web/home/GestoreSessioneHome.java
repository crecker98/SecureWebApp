package com.soriani.securewebapp.web.home;

import com.soriani.securewebapp.business.PropostaProgettuale;
import com.soriani.securewebapp.web.sessione.GestoreSessione;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public class GestoreSessioneHome extends GestoreSessione {

    static void setProposte(HttpServletRequest request, ArrayList<PropostaProgettuale> proposte) {
        request.getSession().setAttribute("proposte", proposte);
    }

    public static ArrayList<PropostaProgettuale> getProposte(HttpServletRequest request){
        return (ArrayList<PropostaProgettuale>) request.getSession().getAttribute("proposte");
    }

}
