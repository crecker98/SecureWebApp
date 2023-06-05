package com.soriani.securewebapp.web.home;

import com.soriani.securewebapp.business.PropostaProgettuale;
import com.soriani.securewebapp.web.condivisi.GestoreSessione;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public final class GestoreSessioneHome extends GestoreSessione {

    static void setProposte(HttpServletRequest request, ArrayList<PropostaProgettuale> proposte) {
        request.getSession().setAttribute("proposte", proposte);
    }

    public static ArrayList<PropostaProgettuale> getProposte(HttpServletRequest request){
        return (ArrayList<PropostaProgettuale>) request.getSession().getAttribute("proposte");
    }

    public static void setVisualizza(HttpServletRequest request, byte[] proposta) {
        request.getSession().setAttribute("visualizzaProposta", proposta);
    }

    public static byte[] getVisualizza(HttpServletRequest request) {
        return (byte[])request.getSession().getAttribute("visualizzaProposta");
    }

    public static int getCodiceProposta(HttpServletRequest request) {
        return (int)request.getSession().getAttribute("codiceProposta");
    }

    public static void setCodiceProposta(HttpServletRequest request, int codice) {
        request.getSession().setAttribute("codiceProposta", codice);
    }

}
