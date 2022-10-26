package com.soriani.securewebapp.web.login;

import javax.servlet.http.HttpServletRequest;

import com.soriani.securewebapp.utility.ApplicationException;
import com.soriani.securewebapp.utility.Controllore;

public class ChkLoginServlet {
	
	private static ChkLoginServlet instance = new ChkLoginServlet();

	private static final String USERNAME_REGEX = "^[a-zA-Z0-9]+$";
	
	private ChkLoginServlet() {
		
	}
	
	static ChkLoginServlet getInstance() {
		return instance;
	}
	
	/**
	 * metodo che controlla il corretto inserimento dell'username 
	 * @param request
	 * @throws ApplicationException
	 */
	public void checkUsername(HttpServletRequest request) throws ApplicationException {
		
		String username = request.getParameter("username");
		
		if(!Controllore.checkString(username, USERNAME_REGEX)) {
			throw new ApplicationException("Inserire un username valido, non può contenere caratteri speciali!");
		}
		
	}

}
