package com.soriani.securewebapp.web.registration;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.soriani.securewebapp.business.Utente;
import com.soriani.securewebapp.utility.ApplicationException;
import com.soriani.securewebapp.utility.Servizi;

/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet(name = "Registration", urlPatterns = "/Registration")
@MultipartConfig
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * jsp associata al caso d'uso
	 */
	private static final String PAGE_REGISTRATION = "/registration/jsp/registration.jsp";
	
	/**
	 * jsp per il successo della registrazione
	 */
	private static final String PAGE_SUCCESS_REGISTRATION = "/registration/jsp/success.jsp";
	
	/**
	 * costante per il nome della servlet
	 */
	private static final String SERVLET = "Registration";
	
	/**
	 * costante per il caso d'uso login
	 */
	private static final String SERVLET_LOGIN = "Login";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrationServlet() {
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
		if(operazione != null) {
			if(operazione.equals("login")) {
				response.sendRedirect(SERVLET_LOGIN);	
			} else if(operazione.equals("registration")) {
				
				log("procedo con la registrazione");
				try {

					GestoreSessioneRegistration.setRegistrationForm(request, Servizi.setDatiFormUtente(request));
					Utente utente = ChkRegistration.getInstance().checkUtente(request);
					RegistrationServletHelper.getInstance().registrationUtente(request, utente);
					inviaPagina(request, response, PAGE_SUCCESS_REGISTRATION);
					
				} catch(ApplicationException e) {
					log("generata eccezzione: " + e.getMessaggio());
					GestoreSessioneRegistration.setMessaggioErrore(request, e.getMessaggio());
					inviaPagina(request, response, PAGE_REGISTRATION);
				}
				
			}
		}else {
			mainPage(request, response);
		}
		
	}
	
	/**
	 * funzione che reindirizza alla pagina principale del caso d'uso
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void mainPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String task = request.getRequestURI().substring(request.getContextPath().length());
		String servlet = "/" + SERVLET;
		if (servlet.equals(task)){
			avviaSessione(request);
		    inviaPagina(request, response, PAGE_REGISTRATION);
		}
		
	}
	
	/**
	 * funzione che permette di settare la sessione all'avvio del caso d'uso
	 * @param request
	 */
	private void avviaSessione(HttpServletRequest request) {
		GestoreSessioneRegistration.setCasoDUso(request, SERVLET);
		GestoreSessioneRegistration.setMessaggioErrore(request, null);
		GestoreSessioneRegistration.setRegistrationForm(request, Servizi.setDatiFormUtente(request));
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
