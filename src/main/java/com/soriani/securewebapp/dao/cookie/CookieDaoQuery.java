package com.soriani.securewebapp.dao.cookie;

public interface CookieDaoQuery {
	
	final String registraCookieStatement = "INSERT INTO COOKIE(USERNAME, VALUE) VALUES (?, ?) ";
	
	final String readCookieStatement = "SELECT VALUE FROM COOKIE WHERE USERNAME = ? ";
	
	final String deleteCookieStatement = "DELETE FROM COOKIE WHERE USERNAME = ? ";

}
