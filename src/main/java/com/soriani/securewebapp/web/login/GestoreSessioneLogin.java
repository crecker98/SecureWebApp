package com.soriani.securewebapp.web.login;

import javax.servlet.http.HttpServletRequest;
import com.soriani.securewebapp.web.sessione.GestoreSessione;

public class GestoreSessioneLogin extends GestoreSessione {
	
	static void setUsernameLogin(HttpServletRequest request, String username) {
		request.getSession().setAttribute("usernameLogin", username);
	}
	
	public static String getUsernameLogin(HttpServletRequest request){
		return (String) request.getSession().getAttribute("usernameLogin");
	}

}
