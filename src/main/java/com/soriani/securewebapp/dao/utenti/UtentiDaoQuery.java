package com.soriani.securewebapp.dao.utenti;

public interface UtentiDaoQuery {
	
	final String insertUtenteStatement = "INSERT INTO UTENTI(NOME, COGNOME, USERNAME, IMMAGINE_PROFILO, PASSWORD) VALUES(?, ?, ?, ?, ?) ";
	
	final String readUtenteFromUsernameStatement = "SELECT * FROM UTENTI WHERE USERNAME = ? ";
	
	final String readPasswordFromUsernameStatemant = "SELECT PASSWORD FROM UTENTI WHERE USERNAME = ? ";

}
