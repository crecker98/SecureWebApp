package com.soriani.securewebapp.web.uploadProposta;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.soriani.securewebapp.utility.ApplicationException;


/**
 * Servlet implementation class UploadPropostaServlet
 */
@WebServlet(name = "UploadProposta", urlPatterns = "/user/UploadProposta")
@MultipartConfig
public final class UploadPropostaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * costante col nome della servlet
	 */
    private static final String SERVLET = "UploadProposta";
    
    /**
     * costante per il percorso dell'upload proposta 
     */
    private static final String PAGE_UPLOAD_PROPOSTA = "/uploadProposta/jsp/uploadProposta.jsp";

	private static final String PAGE_UPLOAD_PROPOSTA_SUCCESS = "/uploadProposta/jsp/success.jsp";

    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadPropostaServlet() {
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
			if(operazione.equals("insertProposta")) {
				log("procedo con la registrazione della proposta");
				try {
					
					UploadPropostaServletHelper.getInstance().setFormSessione(request);
					UploadPropostaServletHelper.getInstance().insertPropostaProgettuale(request);
					GestoreSessioneUploadProposta.setPropostaCaricata(request, true);
					inviaPagina(request, response, PAGE_UPLOAD_PROPOSTA_SUCCESS);
				}catch(ApplicationException e) {
					
					log("generata eccezzione: " + e.getMessaggio());
					GestoreSessioneUploadProposta.setMessaggioErrore(request, e.getMessaggio());
					inviaPagina(request, response,PAGE_UPLOAD_PROPOSTA);
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
		String servlet = "/user/" + SERVLET;
		if (servlet.equals(task)){
			avviaSessione(request);
		    inviaPagina(request, response, PAGE_UPLOAD_PROPOSTA);
		}
		
	}
	
	/**
	 * funzione che permette di settare la sessione all'avvio del caso d'uso
	 * @param request
	 */
	private void avviaSessione(HttpServletRequest request) {
		GestoreSessioneUploadProposta.setCasoDUso(request, SERVLET);
		GestoreSessioneUploadProposta.setMessaggioErrore(request, null);
		GestoreSessioneUploadProposta.setPropostaCaricata(request, false);
		UploadPropostaServletHelper.getInstance().setFormSessione(request);
		try {
			UploadPropostaServletHelper.getInstance().setCategorieSessione(request);
		} catch(ApplicationException e) {
			GestoreSessioneUploadProposta.setMessaggioErrore(request, e.getMessaggio());
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
