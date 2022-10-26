package com.soriani.securewebapp.dao.sale;

public interface SaleDaoQuery {
	
	String registraSaltStatement = "INSERT INTO SALE(USERNAME, SALT) VALUES (?, ?)";
	
	String readSaltFromUsernameStatement = "SELECT SALT FROM SALE WHERE USERNAME = ?";

	String updateSaltStatement = "UPDATE SALE SET SALT = ? WHERE USERNAME = ?";

}
