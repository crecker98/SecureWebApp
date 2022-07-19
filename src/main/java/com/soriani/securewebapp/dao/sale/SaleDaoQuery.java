package com.soriani.securewebapp.dao.sale;

public interface SaleDaoQuery {
	
	final String registraSaltStatement = "INSERT INTO SALE(USERNAME, SALT) VALUES (?, ?)";
	
	final String readSaltFromUsernameStatement = "SELECT SALT FROM SALE WHERE USERNAME = ?";

}
