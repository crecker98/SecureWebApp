package com.soriani.securewebapp.web.home;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet(name = "Home", urlPatterns = "/Home")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * costante col nome della servlet
	 */
    private static final String SERVLET = "Home";
    
    /**
     * costante per il percorso della home 
     */
    private static final String PAGE_HOME = "/home/jsp/home.jsp";
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeServlet() {
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

		HomeServletHelper.getInstance().readAllProposte(request);
		mainPage(request, response);
		
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
		    inviaPagina(request, response, PAGE_HOME);
		}
		
	}
	
	/**
	 * funzione che permette di settare la sessione all'avvio del caso d'uso
	 * @param request
	 */
	private void avviaSessione(HttpServletRequest request) {
		GestoreSessioneHome.setCasoDUso(request, SERVLET);
		GestoreSessioneHome.setMessaggioErrore(request, null);
		
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
