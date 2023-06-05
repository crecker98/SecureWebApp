package com.soriani.securewebapp.web.logout;

import com.soriani.securewebapp.utility.ApplicationException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author christiansoriani on 22/10/22
 * @project SecureWebApp
 */

@WebServlet(name = "Logout", urlPatterns = "/user/Logout")
public final class LogoutServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * costante per indentificare la servlet
     */
    private static final String HOME_SERVLET = "/Home";

    /**
     * costante per indicare la pagina da visualizzare per il caso d'uso
     */
    private static final String PAGE_LOGIN = "/login/jsp/login.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String operazione = request.getParameter("operazione");
        if(operazione != null && operazione.equals("Logout")) {
            try {
                LogoutServletHelper.getInstance().logoutUtente(request, response);
            } catch (ApplicationException e) {
                e.printStackTrace();
                log("generata eccezzione: " + e.getMessaggio());
                GestoreSessioneLogout.setMessaggioErrore(request, e.getMessaggio());
            }

        }
        response.sendRedirect(request.getContextPath() + HOME_SERVLET);

    }


    /**
     * metodo per reindirizzare le pagine
     * @param request
     * @throws IOException
     * @throws ServletException
     */
    private void inviaPagina(HttpServletRequest request, HttpServletResponse response, String pagina) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(pagina);
        dispatcher.forward(request, response);
    }

}
