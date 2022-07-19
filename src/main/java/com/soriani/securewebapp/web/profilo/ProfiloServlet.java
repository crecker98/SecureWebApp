package com.soriani.securewebapp.web.profilo;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ProfiloServlet
 */
@WebServlet(name = "Profilo", urlPatterns = "/Profilo")
public class ProfiloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * costante col nome della servlet
	 */
    private static final String SERVLET = "/Profilo";
    
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
		if(operazione != null) {
			
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
	private static void mainPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String task = request.getRequestURI().substring(request.getContextPath().length());
		if (SERVLET.equals(task)){
		    RequestDispatcher dispatcher = request.getRequestDispatcher(PAGE_PROFILO);
		    dispatcher.forward(request, response);
		}
		
	}

}
