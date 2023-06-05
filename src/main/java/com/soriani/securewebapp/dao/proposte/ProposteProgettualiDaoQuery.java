package com.soriani.securewebapp.dao.proposte;

public interface ProposteProgettualiDaoQuery {
	
	 String inserPropostaStatement = "INSERT INTO PROPOSTE_PROGETTUALI(UTENTE, CATEGORIA, FILE, NOME, DESCRIZIONE) VALUES (?, ?, ?, ?, ?)";

	 String selectPropostaStatement = "SELECT * FROM PROPOSTE_PROGETTUALI P, UTENTI U, CATEGORIE C WHERE P.UTENTE = U.USERNAME AND P.CATEGORIA = C.CODICE";

	 String selectPropostaByCodice = "SELECT FILE FROM PROPOSTE_PROGETTUALI WHERE CODICE = ?";

}
