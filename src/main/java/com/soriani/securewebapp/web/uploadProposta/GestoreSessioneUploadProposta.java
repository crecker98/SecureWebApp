package com.soriani.securewebapp.web.uploadProposta;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.soriani.securewebapp.business.GRPCategoria;
import com.soriani.securewebapp.web.sessione.GestoreSessione;

public class GestoreSessioneUploadProposta extends GestoreSessione {
	
	static void setGRPCategorie(HttpServletRequest request, GRPCategoria grpCategoria) {
		request.getSession().setAttribute("GRPCategorie", grpCategoria);
	}
	
	public static GRPCategoria getGRPCategorie(HttpServletRequest request) {
		return (GRPCategoria) request.getSession().getAttribute("GRPCategorie");
	}
	
	static void setFormUpload(HttpServletRequest request, HashMap<String, String> form) {
		request.getSession().setAttribute("FormUpload", form);
	}
	
	@SuppressWarnings("unchecked")
	public static HashMap<String, String> getFormUpload(HttpServletRequest request){
		return (HashMap<String, String>) request.getSession().getAttribute("FormUpload");
	}

}
