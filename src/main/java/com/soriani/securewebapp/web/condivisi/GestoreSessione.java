package com.soriani.securewebapp.web.condivisi;

import javax.servlet.http.HttpServletRequest;

import com.soriani.securewebapp.business.Utente;

public abstract class GestoreSessione {
	
	public static String getMessaggioErrore(HttpServletRequest request) {
		return (String) request.getSession().getAttribute("MessaggioErrore");
	}
	
	public static void setMessaggioErrore(HttpServletRequest request, String messaggioErrore) {
		request.getSession().setAttribute("MessaggioErrore", messaggioErrore);
	}
	
	public static String getCasoDUso(HttpServletRequest request) {
		return (String) request.getSession().getAttribute("CasoDUso");
	}
	
	public static  void setCasoDUso(HttpServletRequest request, String casoDUso) {
		request.getSession().setAttribute("CasoDUso", casoDUso);
	}
	
	public static Utente getUtenteLoggato(HttpServletRequest request) {
		return (Utente) request.getSession().getAttribute("UtenteLoggato");
	}
	
	public static void setUtenteLoggato(HttpServletRequest request, Utente utente) {
		request.getSession().setAttribute("UtenteLoggato", utente);
	}

}
