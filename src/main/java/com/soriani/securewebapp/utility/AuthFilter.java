package com.soriani.securewebapp.utility;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.soriani.securewebapp.business.Utente;
import com.soriani.securewebapp.dao.cookie.CookieDao;
import com.soriani.securewebapp.dao.utenti.UtentiDao;
import com.soriani.securewebapp.web.sessione.GestoreSessione;

@WebFilter(filterName="AuthFilter", urlPatterns = "/*")
public class AuthFilter implements Filter {
	
	//costanti per reindirizzamento
	private static final String APP = "/SecureWebApp";
	private static final String LOGIN_SERVLET = "/Login";
	private static final String HOME_SERVLET = "/Home";
	private static final String COOKIE_UUID = "UUID";
	private static final String ASSETS = APP + "/condivisi/assets/";
	private static final String REGISTRATION_SERVLET = "/Registration";
	private static final String JS_RESOURCE = "js";
	
	private ServletContext context;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        
        boolean loggedIn = (session != null && !session.isNew()) && (GestoreSessione.getUtenteLoggato(req) != null);
        boolean loginRequest = req.getRequestURI().equals(APP + LOGIN_SERVLET);
        boolean registrationRequest = req.getRequestURI().equals(APP + REGISTRATION_SERVLET);
        boolean isStaticResource = req.getRequestURI().startsWith(ASSETS);
        boolean isStaticResourceJS = req.getRequestURI().endsWith(JS_RESOURCE);
        boolean homeRequest = req.getRequestURI().equals(APP + "/");
        
        Cookie[] cookies = req.getCookies();
        Map<String, Cookie> cookieMap = new HashMap<>();
        if(cookies != null) {
        	for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
             }
        }

        if(loggedIn || loginRequest || isStaticResource || registrationRequest || isStaticResourceJS) { //se sono loggato, se sto andando sul login, se sto andando su registrazione o se richiamo le risorse statiche(ccs,js,html)
        	
        	if(loggedIn && cookieMap.get(COOKIE_UUID) != null) { // se sono loggato e ho i cookie memorizzati 
        		
        		if(loginRequest || registrationRequest) { //reindirizzo le chiamate al login o registration alla home in quanto utente è già loggato
        			res.sendRedirect(req.getContextPath() + HOME_SERVLET);
        			return;
        		}
 
        	}
        	
        	if(cookieMap.get(COOKIE_UUID) != null && !loggedIn) { //se ho il cookie ma non ho sessione attiva, la creo partendo dal cookie (ho spinto su ricordami in precedenza)
        		
        		try {
    				
    				byte[] cookieBro = Base64.getDecoder().decode(cookieMap.get(COOKIE_UUID).getValue());
    				String cookieBrowser = new String(cookieBro, StandardCharsets.UTF_8);
    				cookieBrowser = cookieBrowser.split(";")[1];
    	        	String cookieDB = CookieDao.getCookieDao().readCookieFromUsername(req.getParameter("username"));
    				if(cookieBrowser.equals(cookieDB)) {
    	        		Utente utente = UtentiDao.getUtenteDao().getUtenteFromUsername(req.getParameter("username"));
    	        		GestoreSessione.setUtenteLoggato(req, utente);
    	        		res.sendRedirect(req.getContextPath() + HOME_SERVLET);
    	        		return;
    	        	}else {
    	        		res.sendRedirect(req.getContextPath() + LOGIN_SERVLET);
    	        		return;
    	        	}
    				
    			} catch (ApplicationException e) {
    				e.printStackTrace();
    				res.sendRedirect(req.getContextPath() + LOGIN_SERVLET);
    				if (cookies != null)
    			        for (Cookie cookie : cookies) {
    			            cookie.setValue("");
    			            cookie.setPath("/");
    			            cookie.setMaxAge(-1);
    			            res.addCookie(cookie);
    			    }
    				return;
    			} catch (SQLException e) {
    				e.printStackTrace();
    				res.sendRedirect(req.getContextPath() + LOGIN_SERVLET);
    				if (cookies != null)
    			        for (Cookie cookie : cookies) {
    			            cookie.setValue("");
    			            cookie.setPath("/");
    			            cookie.setMaxAge(-1);
    			            res.addCookie(cookie);
    			        }
    				return;
    			}
        		
        	}
        	
        	if(cookieMap.get(COOKIE_UUID) == null && loggedIn) { // se non ho chiesto di essere ricordato, ma sono loggato con sessione attiva devo controllare solo se faccio richieste a login o registration
        		
        		if(loginRequest || registrationRequest) { //reindirizzo le chiamate al login o registration alla home in quanto utente è già loggato
        			res.sendRedirect(req.getContextPath() + HOME_SERVLET);
        			return;
        		}
        		
        	}
        	
        	if(loggedIn && (loginRequest || registrationRequest || homeRequest) ) {
        		res.sendRedirect(req.getContextPath() + HOME_SERVLET);
        		return;
        	}
        	
        	chain.doFilter(request, response);
        	
        }else {
        	
        	res.sendRedirect(req.getContextPath() + LOGIN_SERVLET);
        	return;	
        	
        } 
        	
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
		this.context = filterConfig.getServletContext();
        this.context.log("AuthenticationFilter initialized");
		
	}

	

}
