package com.soriani.securewebapp.web.login;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Base64.Encoder;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.soriani.securewebapp.business.Utente;
import com.soriani.securewebapp.dao.cookie.CookieDao;
import com.soriani.securewebapp.dao.utenti.UtentiDao;
import com.soriani.securewebapp.utility.ApplicationException;
import com.soriani.securewebapp.utility.Password;
import com.soriani.securewebapp.utility.Servizi;

public class LoginServletHelper {
	
	private static LoginServletHelper instance = new LoginServletHelper();

	private static final int MAX_TIMEOUT = 60 * 15; //15 minuti
	private static final int COOKIE_TIME = 60 * 60 * 24 * 30 ; //un mese
	private static final String COOKIE_UUID = "UUID";
	
	private LoginServletHelper() {
		
	}
	
	protected static LoginServletHelper getInstance() {
		return instance;
	}
	
	/**
	 * contiene dei sotto metodi che si occupano di inzializzare cookie e sessione
	 * @param request
	 * @param response
	 * @throws ApplicationException
	 */
	public void loginUtente(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		
		ApplicationException ex = new ApplicationException("Username o password errati");
		
		try {
			
			if(Password.checkPassword(request.getParameter("password").getBytes(), request.getParameter("username"))) {
				
				if(Boolean.valueOf(request.getParameter("remember"))) {
					saveCookie(request, response);
				}
				setUtenteSession(request);
			}else {
				throw ex;
			}
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw ex;
		} catch (ApplicationException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw ex;
		} catch (IOException e) {
			e.printStackTrace();
			throw ex;
		}
		
	}
	
	/**
	 * metodo che si occupa del salvataggio del cookie
	 * @param request
	 * @param response
	 * @throws ApplicationException
	 */
	void saveCookie(HttpServletRequest request, HttpServletResponse response) throws ApplicationException{
		
		String random = Servizi.generateUUID();
		ApplicationException ex = new ApplicationException("Errore in fase di login. Riprovare");
		String username = request.getParameter("username");
		Cookie[] cookies = request.getCookies();
        Map<String, Cookie> cookieMap = new HashMap<>();
        if(cookies != null) {
        	for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
             }
        }
		try {
			
			if(cookieMap.get(COOKIE_UUID) != null) {
				CookieDao.getCookieDao().deleteCookieFromUsername(username);
			}
			
			CookieDao.getCookieDao().registraCookie(username, random);
		} catch (ApplicationException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw ex;
		}
		
		Encoder encoder = Base64.getEncoder();
		String value = username + ";" + random;
		String cookieValue = encoder.encodeToString(value.getBytes());
		Cookie cookie = new Cookie("UUID", cookieValue);
		cookie.setMaxAge(COOKIE_TIME);
		response.addCookie(cookie);
		
	}
	
	/**
	 * metodo che setta l'utente in sessione
	 * @param request
	 * @throws ApplicationException
	 */
	void setUtenteSession(HttpServletRequest request) throws ApplicationException {
		
		ApplicationException ex = new ApplicationException("Errore in fase di login. Riprovare");
		String username = request.getParameter("username");
		Utente utente = new Utente();
		try {
			utente = UtentiDao.getUtenteDao().getUtenteFromUsername(username);
		} catch (ApplicationException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw ex;
		}
		
		HttpSession session = request.getSession();
		session.invalidate();
		session = request.getSession(true);
		session.setMaxInactiveInterval(MAX_TIMEOUT);
		GestoreSessioneLogin.setUtenteLoggato(request, utente);
		
	}
	
	public void logoutUtente(HttpServletRequest request) throws ApplicationException {
		
		HttpSession session = request.getSession(true);
		session.invalidate();
		Cookie[] cookies = request.getCookies();
        Map<String, Cookie> cookieMap = new HashMap<>();
        if(cookies != null) {
        	for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
             }
        }
        if(cookieMap.get(COOKIE_UUID) != null) {
        	try {
    			CookieDao.getCookieDao().deleteCookieFromUsername(GestoreSessioneLogin.getUtenteLoggato(request).getUsername());
    		} catch (SQLException e) {
    			e.printStackTrace();
    			throw new ApplicationException("Errore di connesione");
    		} catch (ApplicationException e) {
    			e.printStackTrace();
    			throw e;
    		}
        }
		
	}

}
