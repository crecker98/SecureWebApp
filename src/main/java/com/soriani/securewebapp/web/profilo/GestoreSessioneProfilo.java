package com.soriani.securewebapp.web.profilo;

import com.soriani.securewebapp.web.condivisi.GestoreSessione;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public class GestoreSessioneProfilo extends GestoreSessione {

    public static void setMessaggioRiuscita(HttpServletRequest request, String msg) {
        request.getSession().setAttribute("messaggioRiuscita", msg);
    }

    public static String getMessaggioRiuscita(HttpServletRequest request) {
        return (String) request.getSession().getAttribute("messaggioRiuscita");
    }

    public static void setUpdateForm(HttpServletRequest request, HashMap<String,String> form) {
        request.getSession().setAttribute("ProfiloForm", form);
    }

    @SuppressWarnings("unchecked")
    public static HashMap<String, String> getUpdateForm(HttpServletRequest request){
        return (HashMap<String, String>) request.getSession().getAttribute("ProfiloForm");
    }

    static void setCategories(HttpServletRequest request, HashMap<String,Double> form) {
        request.getSession().setAttribute("CategoriesProfilo", form);
    }

    @SuppressWarnings("unchecked")
    public static HashMap<String, Double> getCategories(HttpServletRequest request){
        return (HashMap<String, Double>) request.getSession().getAttribute("CategoriesProfilo");
    }

}
