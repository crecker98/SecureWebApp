package com.soriani.securewebapp.web.profilo;

import com.soriani.securewebapp.utility.ApplicationException;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ProfiloServlet
 */
@WebServlet(name = "Profilo", urlPatterns = "/Profilo")
@MultipartConfig
public class ProfiloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * costante col nome della servlet
	 */
    private static final String SERVLET = "Profilo";
    
    /**
     * costante per la main page della servlet
     */
    private static final String PAGE_PROFILO = "/profilo/jsp/profilo.jsp";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProfiloServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String operazione = request.getParameter("operazione");
		String msg = "";
		if(operazione != null) {

			if(operazione.equals("updatePhoto")){
				try {
					ProfiloServletHelper.getInstance().updatePhoto(request);
					msg = "Modifica della foto riuscita correttamente";
				} catch (ApplicationException e) {
					throw new RuntimeException(e);
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}else if(operazione.equals("updateInfo")) {


			}

			GestoreSessioneProfilo.setMessaggioRiuscita(request, msg);
			inviaPagina(request, response, PAGE_PROFILO);
		}else {
			avviaSessione(request);
			mainPage(request, response);
		}
		
	}

	/**
	 * funzione che permette di settare la sessione all'avvio del caso d'uso
	 * @param request
	 */
	private void avviaSessione(HttpServletRequest request) {

		GestoreSessioneProfilo.setCasoDUso(request, SERVLET);
		GestoreSessioneProfilo.setMessaggioErrore(request, null);

	}
	
	/**
	 * funzione che reindirizza alla pagina principale del caso d'uso
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private static void mainPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String task = request.getRequestURI().substring(request.getContextPath().length());
		String servlet = "/" + SERVLET;
		if (servlet.equals(task)){
		    RequestDispatcher dispatcher = request.getRequestDispatcher(PAGE_PROFILO);
		    dispatcher.forward(request, response);
		}
		
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
