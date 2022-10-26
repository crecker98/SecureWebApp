package com.soriani.securewebapp.web.login;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.soriani.securewebapp.utility.ApplicationException;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(name = "Login", urlPatterns = "/Login")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	/**
	 * costante per indentificare la servlet
	 */
	private static final String SERVLET = "Login";
	
	/**
	 * costante per indicare la pagina da visualizzare per il caso d'uso
	 */
	private static final String PAGE_LOGIN = "/login/jsp/login.jsp";
	
	/**
	 * costante che si riferisce alla servlet home
	 */
	private static final String SERVLET_HOME = "Home";
	
	/**
	 * costante per caso d'uso registration
	 */
	private static final String SERVLET_REGISTRATION = "Registration";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
			if(operazione.equals("registration")) {
				response.sendRedirect(SERVLET_REGISTRATION);
			}else if(operazione.equals("home")) {
				response.sendRedirect(SERVLET_HOME);
			}  else if(operazione.equals("login")) {
				
				log("procedo con il login");
				try {
					
					GestoreSessioneLogin.setUsernameLogin(request, request.getParameter("username"));
					ChkLoginServlet.getInstance().checkUsername(request);
					LoginServletHelper.getInstance().loginUtente(request, response);
					response.sendRedirect(SERVLET_HOME);
					
				}catch(ApplicationException e) {
					log("generata eccezzione: " + e.getMessaggio());
					GestoreSessioneLogin.setMessaggioErrore(request, e.getMessaggio());
					inviaPagina(request, response, PAGE_LOGIN);
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
		    inviaPagina(request, response, PAGE_LOGIN);
		}
		
	}
	
	/**
	 * funzione che permette di settare la sessione all'avvio del caso d'uso
	 * @param request
	 */
	private void avviaSessione(HttpServletRequest request) {
		GestoreSessioneLogin.setCasoDUso(request, SERVLET);
		GestoreSessioneLogin.setMessaggioErrore(request, null);
		GestoreSessioneLogin.setUsernameLogin(request, null);
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
