package com.soriani.securewebapp.dao.proposte;

public interface ProposteProgettualiDaoQuery {
	
	final String inserPropostaStatement = "INSERT INTO PROPOSTE_PROGETTUALI(UTENTE, CATEGORIA, FILE, NOME, DESCRIZIONE) VALUES (?, ?, ?, ?, ?)";

	final String selectPropostaStatement = "SELECT * FROM PROPOSTE_PROGETTUALI P, UTENTI U, CATEGORIE C WHERE P.UTENTE = U.USERNAME AND P.CATEGORIA = C.CODICE";

	final String selectCategoriaPropostaUtente = "SELECT C.CODICE, C.DESCRIZIONE, COUNT(C.CODICE) TOT FROM CATEGORIE C, PROPOSTE_PROGETTUALI P WHERE C.CODICE = P.CATEGORIA AND UTENTE = ? GROUP BY C.CODICE";

}
