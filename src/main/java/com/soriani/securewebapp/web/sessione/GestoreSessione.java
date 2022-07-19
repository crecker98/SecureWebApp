package com.soriani.securewebapp.web.sessione;

import javax.servlet.http.HttpServletRequest;

import com.soriani.securewebapp.business.Utente;

public class GestoreSessione {
	
	public static String getMessaggioErrore(HttpServletRequest request) {
		return (String) request.getSession().getAttribute("MessaggioErrore");
	}
	
	public static final void setMessaggioErrore(HttpServletRequest request, String messaggioErrore) {
		request.getSession().setAttribute("MessaggioErrore", messaggioErrore);
	}
	
	public static String getCasoDUso(HttpServletRequest request) {
		return (String) request.getSession().getAttribute("CasoDUso");
	}
	
	public static final void setCasoDUso(HttpServletRequest request, String casoDUso) {
		request.getSession().setAttribute("CasoDUso", casoDUso);
	}
	
	public static Utente getUtenteLoggato(HttpServletRequest request) {
		return (Utente) request.getSession().getAttribute("UtenteLoggato");
	}
	
	public static final void setUtenteLoggato(HttpServletRequest request, Utente utente) {
		request.getSession().setAttribute("UtenteLoggato", utente);
	}

}
