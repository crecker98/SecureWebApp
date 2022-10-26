package com.soriani.securewebapp.dao.cookie;

public interface CookieDaoQuery {
	
	final String registraCookieStatement = "INSERT INTO COOKIE(USERNAME, KEY_COOKIE, VALUE) VALUES (?, ?, ?) ";
	
	final String readCookieStatement = "SELECT VALUE, KEY_COOKIE FROM COOKIE";
	
	final String deleteCookieStatement = "DELETE FROM COOKIE WHERE USERNAME = ? ";

}
