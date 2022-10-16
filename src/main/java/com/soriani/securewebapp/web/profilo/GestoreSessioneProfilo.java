package com.soriani.securewebapp.web.profilo;

import com.soriani.securewebapp.web.condivisi.GestoreSessione;

import javax.servlet.http.HttpServletRequest;

public class GestoreSessioneProfilo extends GestoreSessione {

    public static void setMessaggioRiuscita(HttpServletRequest request, String msg) {
        request.getSession().setAttribute("messaggioRiuscita", msg);
    }

    public static String getMessaggioRiuscita(HttpServletRequest request) {
        return (String) request.getSession().getAttribute("messaggioRiuscita");
    }

}
