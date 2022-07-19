package com.soriani.securewebapp.web.registration;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.soriani.securewebapp.web.sessione.GestoreSessione;

public class GestoreSessioneRegistration extends GestoreSessione {
	
	static void setRegistrationForm(HttpServletRequest request, HashMap<String,String> form) {
		request.getSession().setAttribute("RegistrationForm", form);
	}
	
	@SuppressWarnings("unchecked")
	public static HashMap<String, String> getRegistrationForm(HttpServletRequest request){
		return (HashMap<String, String>) request.getSession().getAttribute("RegistrationForm");
	}

}
