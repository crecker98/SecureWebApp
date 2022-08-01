package com.soriani.securewebapp.web.uploadProposta;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.soriani.securewebapp.business.Categoria;
import com.soriani.securewebapp.web.sessione.GestoreSessione;

public class GestoreSessioneUploadProposta extends GestoreSessione {
	
	public static void setCategorie(HttpServletRequest request, ArrayList<Categoria> categorie) {
		request.getSession().setAttribute("Categorie", categorie);
	}
	
	public static ArrayList<Categoria> getCategorie(HttpServletRequest request) {
		return (ArrayList<Categoria>) request.getSession().getAttribute("Categorie");
	}
	
	public static void setFormUpload(HttpServletRequest request, HashMap<String, String> form) {
		request.getSession().setAttribute("FormUpload", form);
	}
	
	@SuppressWarnings("unchecked")
	public static HashMap<String, String> getFormUpload(HttpServletRequest request){
		return (HashMap<String, String>) request.getSession().getAttribute("FormUpload");
	}

	public static void setPropostaCaricata(HttpServletRequest request, boolean caricata){
		request.getSession().setAttribute("propostaCaricata", caricata);
	}

	public static boolean isPropostaCaricata(HttpServletRequest request){
		return (boolean) request.getSession().getAttribute("propostaCaricata");
	}

}
