package com.soriani.securewebapp.dao.utenti;

public interface UtentiDaoQuery {
	
	String insertUtenteStatement = "INSERT INTO UTENTI(NOME, COGNOME, USERNAME, IMMAGINE_PROFILO, PASSWORD) VALUES(?, ?, ?, ?, ?) ";
	
	String readUtenteFromUsernameStatement = "SELECT * FROM UTENTI WHERE USERNAME = ? ";
	
	String readPasswordFromUsernameStatemant = "SELECT PASSWORD FROM UTENTI WHERE USERNAME = ? ";

	String updatePhotoFromUsernameStatement = "UPDATE UTENTI SET IMMAGINE_PROFILO = ? WHERE USERNAME = ? ";

	String updateInfoFromUsernameStatement = "UPDATE UTENTE SET USERNAME = ?, NOME = ?, COGNOME = ? WHERE USERNAME = ? ";

}
